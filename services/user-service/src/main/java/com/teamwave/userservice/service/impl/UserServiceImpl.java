package com.teamwave.userservice.service.impl;

import com.teamwave.common.dto.kafka.*;
import com.teamwave.userservice.config.JWTProperties;
import com.teamwave.userservice.config.KafkaConfig;
import com.teamwave.userservice.dto.*;
import com.teamwave.userservice.exception.AccountActivationException;
import com.teamwave.userservice.exception.DataNotFoundException;
import com.teamwave.userservice.exception.PasswordNotMatchException;
import com.teamwave.userservice.model.Role;
import com.teamwave.userservice.model.User;
import com.teamwave.userservice.repository.UserRepository;
import com.teamwave.userservice.service.HtmlTemplateService;
import com.teamwave.userservice.service.JwtService;
import com.teamwave.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UUID, UserRepository> implements UserService {
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final HttpServletRequest request;
    private final MessageSource messageSource;
    private final JWTProperties jwtProperties;
    private final HtmlTemplateService htmlTemplateService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserServiceImpl(UserRepository repository, JwtService jwtService, HttpServletRequest request, MessageSource messageSource,
                           PasswordEncoder encoder, JWTProperties jwtProperties, HtmlTemplateService htmlTemplateService, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.request = request;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.messageSource = messageSource;
        this.jwtProperties = jwtProperties;
        this.kafkaTemplate = kafkaTemplate;
        this.htmlTemplateService = htmlTemplateService;
    }

    @Override
    public User findByEmail(String email) {
        return super.getRepository().findByEmail(email)
        .orElseThrow(() -> new DataNotFoundException(messageSource.getMessage("messages.user.not-found", new Object[]{email}, request.getLocale())));
    }

    @Override
    public List<User> findAll(Example<User> example, String orderBy, Sort.Direction direction) {
        return super.getRepository().findAll(example, Sort.by(direction, orderBy));
    }

    @Override
    public void hasLoggedIn(User user) {
        if (!user.getHasLoggedIn()) {
            super.save(user.toBuilder()
            .hasLoggedIn(Boolean.TRUE)
            .build());
        }
    }

    @Override
    public User create(UserDTO userDTO) {
        return super.save(User.builder()
        .name(userDTO.name())
        .gender(userDTO.gender())
        .email(userDTO.email())
        .roles(Set.of(Role.USER))
        .countryId(userDTO.countryId())
        .dateOfBirth(userDTO.dateOfBirth())
        .password(encoder.encode(userDTO.password()))
        .build());
    }

    @Override
    @Transactional
    public User createArtist(UserArtistDTO userArtistDTO) {
        User user = create(userArtistDTO.userDTO());
        kafkaTemplate.send(KafkaConfig.TOPIC_CREATE_ARTIST, new ArtistDTO(
            userArtistDTO.artistDTO().name(),
            userArtistDTO.artistDTO().debutYear(),
            userArtistDTO.artistDTO().biography(),
            user.getId()
        ));
        return user;
    }

    @Override
    public User update(UUID userId, UpdateUserDTO updateUserDTO) {
        return super.getRepository().save(super.findById(userId).toBuilder()
        .name(updateUserDTO.name())
        .gender(updateUserDTO.gender())
        .dateOfBirth(updateUserDTO.dateOfBirth())
        .countryId(updateUserDTO.countryId())
        .build());
    }

    @Override
    public User updatePassword(UUID userId, UpdateUserPasswordDTO updateUserPasswordDTO) {
        User user = super.findById(userId);
        if (!encoder.matches(updateUserPasswordDTO.currentPassword(), user.getPassword())) {
            throw new PasswordNotMatchException(messageSource.getMessage("messages.user.password.invalid-password", null, request.getLocale()));
        }

        return super.save(user.toBuilder()
        .password(encoder.encode(updateUserPasswordDTO.newPassword()))
        .build());
    }

    @Override
    public User updateEmail(UUID userId, UpdateUserEmailDTO updateUserEmailDTO) {
        User user = super.findById(userId);
        if (!encoder.matches(updateUserEmailDTO.password(), user.getPassword())) {
            throw new PasswordNotMatchException("Password does not match");
        }

        return super.getRepository().save(user.toBuilder()
                .email(updateUserEmailDTO.email())
                .build());
    }

    @Override
    public User activate(UserEmailDTO userEmailDTO) {
        User user = this.findByEmail(userEmailDTO.email());
        if (user.getEnabled()) {
            throw new AccountActivationException(messageSource.getMessage("messages.account.activation.enabled", null, request.getLocale()));
        }
        return super.save(user.toBuilder()
                .enabled(Boolean.TRUE)
                .build());
    }

    @Override
    public User recovery(RecoveryDTO recoveryDTO) {
        return super.save(this.findByEmail(recoveryDTO.email()).toBuilder()
                .password(encoder.encode(recoveryDTO.password()))
                .build());
    }

    @Override
    public EmailToken requireActivation(UserEmailDTO userEmailDTO) {
        User user = this.findByEmail(userEmailDTO.email());
        if (user.getEnabled()) {
            throw new AccountActivationException(messageSource.getMessage("messages.account.activation.enabled", null, request.getLocale()));
        }
        kafkaTemplate.send(
                KafkaConfig.TOPIC_SEND_EMAIL,
                new EmailDTO(
                        user.getEmail(),
                        messageSource.getMessage("messages.recovery.subject", null, request.getLocale()),
                        htmlTemplateService.accountActivation(user, jwtService.generateEmailToken(user))
                )
        );
        return new EmailToken(jwtProperties.getExpirationEmailToken());
    }

    @Override
    public EmailToken requireUpdateEmail(UserEmailDTO userEmailDTO) {
        User user = this.findByEmail(userEmailDTO.email());
        kafkaTemplate.send(
                KafkaConfig.TOPIC_SEND_EMAIL,
                new EmailDTO(
                        user.getEmail(),
                        messageSource.getMessage("messages.user.email.update.subject", null, request.getLocale()),
                        htmlTemplateService.updateEmail(user, jwtService.generateEmailToken(user))
                )
        );
        return new EmailToken(jwtProperties.getExpirationEmailToken());
    }

    @Override
    public EmailToken requireRecovery(UserEmailDTO userEmailDTO) {
        User user = this.findByEmail(userEmailDTO.email());
        kafkaTemplate.send(
                KafkaConfig.TOPIC_SEND_EMAIL,
                new EmailDTO(
                    user.getEmail(),
                    messageSource.getMessage("messages.recovery.subject", null, Locale.getDefault()),
                    htmlTemplateService.recovery(user, jwtService.generateEmailToken(user))
                )
        );
        return new EmailToken(jwtProperties.getExpirationEmailToken());
    }

    @Override
    public void delete(UUID userId) {
        User user = super.findById(userId);
        super.getRepository().delete(user);
        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE,
            new DeleteFileDTO(user.getCoverPicture(), FileType.USER_COVER_PICTURE)
        );
        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE,
            new DeleteFileDTO(user.getProfilePicture(), FileType.USER_PROFILE_PICTURE)
        );
        kafkaTemplate.send(KafkaConfig.TOPIC_USER_DELETED, new UserDeletedDTO(userId));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_ARTIST_CREATED)
    public void handleUpdateUserToArtist(ArtistCreatedDTO artistCreatedDTO) {
        log.info("Artist created: {}", artistCreatedDTO);
        super.getRepository().findById(artistCreatedDTO.userId())
        .ifPresent(user -> {
            user.getRoles().add(Role.ARTIST);
            user.setArtistId(artistCreatedDTO.artistId());
            super.getRepository().save(user);
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_ARTIST_DELETED)
    public void handleArtistDeleted(ArtistDeletedDTO artistDeletedDTO) {
        log.info("Artist deleted: {}", artistDeletedDTO);
        super.getRepository().findOne(Example.of(User.builder().artistId(artistDeletedDTO.artistId()).build()))
        .ifPresent(user -> {
            user.getRoles().remove(Role.ARTIST);
            super.getRepository().save(user);
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_USER_PROFILE_PICTURE)
    public void handleUserProfilePicture(FileUploadedDTO fileUploadedDTO) {
        log.info("User profile picture: {}", fileUploadedDTO);
        super.getRepository().findById(fileUploadedDTO.ownerId()).ifPresent(user -> {
            String profilePicture = user.getProfilePicture();
            super.save(user.toBuilder().profilePicture(fileUploadedDTO.filename()).build());
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE,
                new DeleteFileDTO(profilePicture, FileType.USER_PROFILE_PICTURE)
            );
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_USER_COVER_PICTURE)
    public void handleUserCoverPicture(FileUploadedDTO fileUploadedDTO) {
        log.info("User cover picture: {}", fileUploadedDTO);
        super.getRepository().findById(fileUploadedDTO.ownerId()).ifPresent(user -> {
            String coverPicture = user.getCoverPicture();
            super.save(user.toBuilder().coverPicture(fileUploadedDTO.filename()).build());
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE,
                    new DeleteFileDTO(coverPicture, FileType.USER_COVER_PICTURE)
            );
        });
    }

}

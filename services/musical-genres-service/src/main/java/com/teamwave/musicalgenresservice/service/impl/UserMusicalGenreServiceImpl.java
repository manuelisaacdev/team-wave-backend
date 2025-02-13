package com.teamwave.musicalgenresservice.service.impl;

import com.teamwave.common.dto.kafka.UserDeletedDTO;
import com.teamwave.musicalgenresservice.config.KafkaConfig;
import com.teamwave.musicalgenresservice.model.UserMusicalGenre;
import com.teamwave.musicalgenresservice.repository.UserMusicalGenreRepository;
import com.teamwave.musicalgenresservice.service.MusicalGenreService;
import com.teamwave.musicalgenresservice.service.UserMusicalGenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserMusicalGenreServiceImpl extends AbstractServiceImpl<UserMusicalGenre, UUID, UserMusicalGenreRepository> implements UserMusicalGenreService {
    private final MusicalGenreService musicalGenreService;

    public UserMusicalGenreServiceImpl(UserMusicalGenreRepository repository, MusicalGenreService musicalGenreService) {
        super(repository);
        this.musicalGenreService = musicalGenreService;
    }

    @Override
    public UserMusicalGenre create(UUID userId, UUID musicalGenreId) {
        return super.save(UserMusicalGenre.builder()
        .userId(userId)
        .musicalGenre(musicalGenreService.findById(musicalGenreId))
        .build());
    }

    @Override
    public List<UserMusicalGenre> create(UUID userId, List<UUID> musicalGenreIds) {
        return super.save(musicalGenreService.findAllById(musicalGenreIds)
        .stream().map(musicalGenre -> UserMusicalGenre.builder()
        .userId(userId)
        .musicalGenre(musicalGenre)
        .build()).toList());
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("User deleted: {}", userDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(UserMusicalGenre.builder()
        .userId(userDeletedDTO.userId())
        .build())));
    }
}

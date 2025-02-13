package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.config.KafkaConfig;
import com.teamwave.common.dto.kafka.ArtistDTO;
import com.teamwave.artistservice.dto.UpdateArtistDTO;
import com.teamwave.common.dto.kafka.ArtistCreatedDTO;
import com.teamwave.common.dto.kafka.ArtistDeletedDTO;
import com.teamwave.common.dto.kafka.UserDeletedDTO;
import com.teamwave.artistservice.exception.DataNotFoundException;
import com.teamwave.artistservice.model.Artist;
import com.teamwave.artistservice.repository.ArtistRepository;
import com.teamwave.artistservice.service.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ArtistServiceImpl extends AbstractServiceImpl<Artist, UUID, ArtistRepository> implements ArtistService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ArtistServiceImpl(ArtistRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Artist findByUserId(UUID userId) {
        return super.getRepository().findOne(Example.of(Artist.builder().userId(userId).build()))
        .orElseThrow(() -> new DataNotFoundException("Artist not found: " + userId));
    }

    @Override
    public Artist create(ArtistDTO artistDTO) {
        Artist artist = super.save(Artist.builder()
        .name(artistDTO.name())
        .debutYear(artistDTO.debutYear())
        .biography(artistDTO.biography())
        .userId(artistDTO.userId())
        .build());

        kafkaTemplate.send(KafkaConfig.TOPIC_ARTIST_CREATED, new ArtistCreatedDTO(
            artistDTO.userId(),
            artist.getId()
        ));

        return artist;
    }

    @Override
    public Artist update(UUID artistId, UpdateArtistDTO updateArtistDTO) {
        return super.save(super.findById(artistId).toBuilder()
        .name(updateArtistDTO.name())
        .debutYear(updateArtistDTO.debutYear())
        .biography(updateArtistDTO.biography())
        .build());
    }

    @Override
    public void delete(UUID artistId) {
        Artist artist = super.findById(artistId);
        super.getRepository().delete(artist);
        kafkaTemplate.send(KafkaConfig.TOPIC_ARTIST_DELETED, new ArtistDeletedDTO(artistId));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("Deleting artist as user: {}", userDeletedDTO);
        super.getRepository().findOne(Example.of(Artist.builder().userId(userDeletedDTO.userId()).build()))
        .ifPresent(artist -> {
            super.getRepository().delete(artist);
            kafkaTemplate.send(KafkaConfig.TOPIC_ARTIST_DELETED, new ArtistDeletedDTO(artist.getId()));
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_CREATE_ARTIST)
    public void handleCreateArtist(ArtistDTO artistDTO) {
        log.info("Creating new Artist: {}", artistDTO);
        create(artistDTO);
    }

}

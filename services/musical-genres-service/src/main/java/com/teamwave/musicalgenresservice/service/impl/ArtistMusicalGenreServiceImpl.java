package com.teamwave.musicalgenresservice.service.impl;

import com.teamwave.common.dto.kafka.ArtistDeletedDTO;
import com.teamwave.musicalgenresservice.config.KafkaConfig;
import com.teamwave.musicalgenresservice.model.ArtistMusicalGenre;
import com.teamwave.musicalgenresservice.repository.ArtistMusicalGenreRepository;
import com.teamwave.musicalgenresservice.service.ArtistMusicalGenreService;
import com.teamwave.musicalgenresservice.service.MusicalGenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ArtistMusicalGenreServiceImpl extends AbstractServiceImpl<ArtistMusicalGenre, UUID, ArtistMusicalGenreRepository> implements ArtistMusicalGenreService {
    private final MusicalGenreService musicalGenreService;

    public ArtistMusicalGenreServiceImpl(ArtistMusicalGenreRepository repository, MusicalGenreService musicalGenreService) {
        super(repository);
        this.musicalGenreService = musicalGenreService;
    }

    @Override
    public ArtistMusicalGenre create(UUID artistId, UUID musicalGenreId) {
        return super.save(ArtistMusicalGenre.builder()
        .artistId(artistId)
        .musicalGenre(musicalGenreService.findById(musicalGenreId))
        .build());
    }

    @Override
    public List<ArtistMusicalGenre> create(UUID artistId, List<UUID> musicalGenreIds) {
        return super.save(musicalGenreService.findAllById(musicalGenreIds)
        .stream().map(musicalGenre -> ArtistMusicalGenre.builder()
        .artistId(artistId)
        .musicalGenre(musicalGenre)
        .build()).toList());
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_ARTIST_DELETED)
    public void handleUserDeleted(ArtistDeletedDTO artistDeletedDTO) {
        log.info("Artist deleted: {}", artistDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(ArtistMusicalGenre.builder()
        .artistId(artistDeletedDTO.artistId())
        .build())));
    }
}

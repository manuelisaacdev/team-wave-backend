package com.teamwave.albumservice.service.impl;

import com.teamwave.albumservice.config.KafkaConfig;
import com.teamwave.albumservice.dto.AlbumMusicalGenreDTO;
import com.teamwave.albumservice.model.AlbumMusicalGenre;
import com.teamwave.albumservice.repository.AlbumMusicalGenreRepository;
import com.teamwave.albumservice.service.AlbumMusicalGenreService;
import com.teamwave.albumservice.service.AlbumService;
import com.teamwave.albumservice.service.MusicalGenreService;
import com.teamwave.common.dto.kafka.MusicalGenreUpdated;
import com.teamwave.common.model.MusicalGenre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class AlbumMusicalGenreImpl extends AbstractServiceImpl<AlbumMusicalGenre, UUID, AlbumMusicalGenreRepository> implements AlbumMusicalGenreService {
    private final AlbumService albumService;
    private final MusicalGenreService musicalGenreService;

    public AlbumMusicalGenreImpl(AlbumMusicalGenreRepository repository, AlbumService albumService, MusicalGenreService musicalGenreService) {
        super(repository);
        this.albumService = albumService;
        this.musicalGenreService = musicalGenreService;
    }

    @Override
    public AlbumMusicalGenre create(AlbumMusicalGenreDTO albumMusicalGenreDTO, String authorization) {
        MusicalGenre musicalGenre = musicalGenreService.findById(albumMusicalGenreDTO.musicalGenreId(), authorization);
        return super.save(AlbumMusicalGenre.builder()
        .name(musicalGenre.name())
        .musicalGenreId(musicalGenre.id())
        .album(albumService.findById(albumMusicalGenreDTO.albumId()))
        .build());
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_MUSICAL_GENRE_UPDATED)
    public void handleMusicalGenreUpdated(MusicalGenreUpdated musicalGenreUpdated) {
        log.info("Music Genre Updated: {}", musicalGenreUpdated);
        super.getRepository().saveAll(super.getRepository().findAll(Example.of(AlbumMusicalGenre.builder()
        .musicalGenreId(musicalGenreUpdated.id()).build()))
                .stream().map(albumMusicalGenre -> albumMusicalGenre.toBuilder()
                        .name(musicalGenreUpdated.name())
                        .build()).toList());
    }

}

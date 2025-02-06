package com.teamwave.albumservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamwave.albumservice.config.KafkaConfig;
import com.teamwave.albumservice.dto.AlbumDTO;
import com.teamwave.common.dto.kafka.AlbumUpdatedDTO;
import com.teamwave.albumservice.model.Album;
import com.teamwave.albumservice.model.AlbumMusicalGenre;
import com.teamwave.albumservice.model.Label;
import com.teamwave.albumservice.repository.AlbumMusicalGenreRepository;
import com.teamwave.albumservice.repository.AlbumRepository;
import com.teamwave.albumservice.repository.LabelRepository;
import com.teamwave.albumservice.service.AlbumService;
import com.teamwave.albumservice.service.MusicalGenreService;
import com.teamwave.common.dto.kafka.*;
import com.teamwave.common.model.MusicalGenre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class AlbumServiceImpl extends AbstractServiceImpl<Album, UUID, AlbumRepository> implements AlbumService {
    private final LabelRepository labelRepository;
    private final MusicalGenreService musicalGenreService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AlbumMusicalGenreRepository albumMusicalGenreRepository;

    public AlbumServiceImpl(AlbumRepository repository, LabelRepository labelRepository, MusicalGenreService musicalGenreService, KafkaTemplate<String, Object> kafkaTemplate, AlbumMusicalGenreRepository albumMusicalGenreRepository) {
        super(repository);
        this.labelRepository = labelRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.musicalGenreService = musicalGenreService;
        this.albumMusicalGenreRepository = albumMusicalGenreRepository;
    }

    @Override
    public Album create(UUID artistId, AlbumDTO albumDTO) {
        return super.save(Album.builder()
        .artistId(artistId)
        .name(albumDTO.name())
        .description(albumDTO.description())
        .albumType(albumDTO.albumType())
        .privacy(albumDTO.privacy())
        .releaseDate(albumDTO.releaseDate())
        .build());
    }

    @Override
    public List<Album> create(UUID artistId, List<AlbumDTO> albumsDTO, String authorization) {
        try {
            List<Label> labels = new ArrayList<>();
            List<AlbumMusicalGenre> albumMusicalGenres = new ArrayList<>();
            List<MusicalGenre> musicalGenres = musicalGenreService.findAll(authorization);
            List<String> labelNames = new ObjectMapper().readValue(getClass().getResource("/json/Labels.json") , new TypeReference<>() {});

            List<Album> albums = super.getRepository().saveAllAndFlush(albumsDTO.stream().map(albumDTO -> Album.builder()
                    .artistId(artistId)
                    .name(albumDTO.name())
                    .description(albumDTO.description())
                    .albumType(albumDTO.albumType())
                    .privacy(albumDTO.privacy())
                    .releaseDate(albumDTO.releaseDate())
                    .build()).toList());
            Random random = new Random();

            for (Album album : albums) {
                Set<Label> set2 = new HashSet<>();
                Set<AlbumMusicalGenre> set1 = new HashSet<>();
                for (int i = random.nextInt(1, 5); i > 0; i--) {
                    MusicalGenre musicalGenre = musicalGenres.get(random.nextInt(musicalGenres.size()));
                    set1.add(AlbumMusicalGenre.builder()
                            .album(album)
                            .name(musicalGenre.name())
                            .musicalGenreId(musicalGenre.id())
                            .build());
                }
                for (int i = random.nextInt(1, 5); i > 0; i--) {
                    set2.add(Label.builder()
                            .album(album)
                            .name(labelNames.get(random.nextInt(labelNames.size())))
                            .build());
                }
                labels.addAll(set2);
                albumMusicalGenres.addAll(set1);
            }

            labelRepository.saveAll(labels);
            albumMusicalGenreRepository.saveAll(albumMusicalGenres);
            return albums;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Album update(UUID albumId, AlbumDTO albumDTO) {
        Album album = super.save(super.findById(albumId).toBuilder()
        .name(albumDTO.name())
        .description(albumDTO.description())
        .albumType(albumDTO.albumType())
        .privacy(albumDTO.privacy())
        .releaseDate(albumDTO.releaseDate())
        .build());
        kafkaTemplate.send(KafkaConfig.TOPIC_ALBUM_UPDATED, new AlbumUpdatedDTO(
                album.getId(),
                album.getName()
        ));
        return album;
    }

    @Override
    public void delete(UUID albumId) {
        Album album = super.findById(albumId);
        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                album.getCover(),
                FileType.ALBUM_COVER
        ));

        kafkaTemplate.send(KafkaConfig.TOPIC_ALBUM_DELETED, new AlbumDeletedDTO(albumId));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_ARTIST_DELETED)
    public void handleArtistDeleted(ArtistDeletedDTO artistDeletedDTO) {
        log.info("Artist deleted: {}", artistDeletedDTO);
        List<Album> albums = super.getRepository().findAll(Example.of(Album.builder()
        .artistId(artistDeletedDTO.artistId())
        .build()));

        super.getRepository().deleteAll(albums);

        albums.forEach(album -> {
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                    album.getCover(),
                    FileType.ALBUM_COVER
            ));
            kafkaTemplate.send(KafkaConfig.TOPIC_ALBUM_DELETED, new AlbumDeletedDTO(album.getId()));
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_ALBUM_REACTIONS)
    public void updateAlbumReactions(AlbumReactionsDTO albumReactionsDTO) {
        log.info("Album reactions: {}", albumReactionsDTO);
        super.getRepository().findById(albumReactionsDTO.albumId())
        .ifPresent(music -> super.save(music.toBuilder()
            .likes(albumReactionsDTO.likes())
            .dislikes(albumReactionsDTO.dislikes())
            .loves(albumReactionsDTO.loves())
            .build()
        ));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_ALBUM_COVER)
    public void updateAlbumCover(FileUploadedDTO fileUploadedDTO) {
        log.info("Album cover: {}", fileUploadedDTO);
        super.getRepository().findById(fileUploadedDTO.ownerId()).ifPresent(album -> {
            String oldCover = album.getCover();
            super.save(album.toBuilder()
                    .cover(fileUploadedDTO.filename())
                    .build()
            );

            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                    oldCover,
                    FileType.ALBUM_COVER
            ));
        });
    }

}

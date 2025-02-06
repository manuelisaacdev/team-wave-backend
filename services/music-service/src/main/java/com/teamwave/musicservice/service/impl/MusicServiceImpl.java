package com.teamwave.musicservice.service.impl;

import com.teamwave.common.dto.kafka.*;
import com.teamwave.common.model.MusicalGenre;
import com.teamwave.musicservice.config.KafkaConfig;
import com.teamwave.musicservice.dto.MusicDTO;
import com.teamwave.musicservice.model.Music;
import com.teamwave.musicservice.repository.MusicRepository;
import com.teamwave.musicservice.service.MusicService;
import com.teamwave.musicservice.service.MusicalGenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MusicServiceImpl extends AbstractServiceImpl<Music, UUID, MusicRepository> implements MusicService {
    private final MusicalGenreService musicalGenreService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MusicServiceImpl(MusicRepository repository, MusicalGenreService musicalGenreService, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
        this.musicalGenreService = musicalGenreService;
    }

    @Override
    public Music create(UUID artistId, MusicDTO musicDTO, String authorization) {
        MusicalGenre musicalGenre = musicalGenreService.findById(musicDTO.musicalGenreId(), authorization);
        return super.save(Music.builder()
        .artistId(artistId)
        .title(musicDTO.title())
        .lyrics(musicDTO.lyrics())
        .description(musicDTO.description())
        .releaseType(musicDTO.releaseType())
        .releaseDate(musicDTO.releaseDate())
        .musicalGenreId(musicalGenre.id())
        .musicalGenreName(musicalGenre.name())
        .build());
    }

    @Override
    public List<Music> create(UUID artistId, List<MusicDTO> musicsDTO, String authorization) {
        List<MusicalGenre> musicalGenres = musicalGenreService.findAll(authorization);
        List<Music> musics = new ArrayList<>();
        for (MusicDTO musicDTO : musicsDTO) {
            for(MusicalGenre musicalGenre : musicalGenres) {
                if (musicalGenre.id().equals(musicDTO.musicalGenreId())) {
                    musics.add(Music.builder()
                    .artistId(artistId)
                    .title(musicDTO.title())
                    .lyrics(musicDTO.lyrics())
                    .description(musicDTO.description())
                    .releaseType(musicDTO.releaseType())
                    .releaseDate(musicDTO.releaseDate())
                    .musicalGenreId(musicalGenre.id())
                    .musicalGenreName(musicalGenre.name())
                    .build());
                }
            }
        }
        return super.getRepository().saveAll(musics);
    }

    @Override
    public Music update(UUID musicId, MusicDTO musicDTO, String authorization) {
        MusicalGenre musicalGenre = musicalGenreService.findById(musicDTO.musicalGenreId(), authorization);
        return super.save(super.findById(musicId).toBuilder()
        .title(musicDTO.title())
        .lyrics(musicDTO.lyrics())
        .description(musicDTO.description())
        .releaseType(musicDTO.releaseType())
        .releaseDate(musicDTO.releaseDate())
        .musicalGenreId(musicalGenre.id())
        .musicalGenreName(musicalGenre.name())
        .build());
    }

    @Override
    public void delete(UUID musicId) {
        delete(super.findById(musicId));
    }

    private void delete(Music music) {
        super.getRepository().delete(music);
        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                music.getCover(),
                FileType.MUSIC_COVER
        ));
        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                music.getAudio(),
                FileType.MUSIC_AUDIO
        ));
        kafkaTemplate.send(KafkaConfig.TOPIC_MUSIC_DELETED, new MusicDeletedDTO(music.getId()));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_ARTIST_DELETED)
    public void handleArtistDeleted(ArtistDeletedDTO artistDeletedDTO) {
        log.info("Artist deleted: {}", artistDeletedDTO);
        super.getRepository().findAll(Example.of(Music.builder().artistId(artistDeletedDTO.artistId()).build()))
        .forEach(this::delete);
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_REACTIONS)
    public void handleMusicReactions(MusicReactionsDTO musicReactionsDTO) {
        log.info("Updating music reactions: {}", musicReactionsDTO);
        super.getRepository().findById(musicReactionsDTO.musicId())
        .ifPresent(music -> super.save(music.toBuilder()
            .likes(musicReactionsDTO.likes())
            .dislikes(musicReactionsDTO.dislikes())
            .loves(musicReactionsDTO.loves())
            .build()
        ));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_COVER)
    public void handleMusicCover(FileUploadedDTO fileUploadedDTO) {
        log.info("Music cover: {}", fileUploadedDTO);
        super.getRepository().findById(fileUploadedDTO.ownerId()).ifPresent(music -> {
            String oldCover = music.getCover();
            super.save(music.toBuilder()
                    .cover(fileUploadedDTO.filename())
                    .build());
            kafkaTemplate.send(KafkaConfig.TOPIC_MUSIC_UPDATED, new MusicUpdatedDTO(
                    music.getId(),
                    music.getCover(),
                    music.getDuration()
            ));
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                oldCover,
                FileType.MUSIC_COVER
            ));
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_AUDIO)
    public void handleMusicCover(MediaFileUploadedDTO mediaFileUploadedDTO) {
        log.info("Music audio: {}", mediaFileUploadedDTO);
        super.getRepository().findById(mediaFileUploadedDTO.ownerId()).ifPresent(music -> {
            String oldAudio = music.getAudio();
            super.save(music.toBuilder()
                    .available(Boolean.TRUE)
                    .audio(mediaFileUploadedDTO.filename())
                    .duration(mediaFileUploadedDTO.duration())
                    .build());
            kafkaTemplate.send(KafkaConfig.TOPIC_MUSIC_UPDATED, new MusicUpdatedDTO(
                    music.getId(),
                    music.getCover(),
                    music.getDuration()
            ));
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                    oldAudio,
                    FileType.MUSIC_AUDIO
            ));
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSICAL_GENRE_UPDATED)
    public void handleMusicalGenreUpdated(MusicalGenreUpdated musicalGenreUpdated) {
        log.info("Music Genre Updated: {}", musicalGenreUpdated);
        super.getRepository().saveAll(super.getRepository().findAll(Example.of(Music.builder().musicalGenreId(musicalGenreUpdated.id()).build()))
                .stream().map(albumMusicalGenre -> albumMusicalGenre.toBuilder()
                        .musicalGenreName(musicalGenreUpdated.name())
                        .build()).toList());
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_ALBUM_UPDATED)
    public void handleAlbumUpdated(AlbumUpdatedDTO albumUpdatedDTO) {
        log.info("Album Updated: {}", albumUpdatedDTO);
        super.getRepository().saveAll(super.getRepository().findAll(Example.of(Music.builder().albumId(albumUpdatedDTO.albumId()).build()))
                .stream().map(albumMusicalGenre -> albumMusicalGenre.toBuilder()
                        .albumName(albumUpdatedDTO.name())
                        .build()).toList());
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_ALBUM_DELETED)
    public void handleAlbumDeleted(AlbumDeletedDTO albumDeletedDTO) {
        log.info("Album deleted: {}", albumDeletedDTO);
        super.getRepository().saveAll(super.getRepository().findAll(Example.of(Music.builder().albumId(albumDeletedDTO.albumId()).build()))
                .stream().map(albumMusicalGenre -> albumMusicalGenre.toBuilder()
                        .albumId(null)
                        .albumName(null)
                        .build()).toList());
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_ADDED_IN_ALBUM)
    public void handleMusicAddedInAlbum(MusicAddedInAlbum musicAddedInAlbum) {
        log.info("Music added in album: {}", musicAddedInAlbum);
        super.getRepository().findById(musicAddedInAlbum.musicId())
        .ifPresent(music -> super.save(music.toBuilder()
        .albumId(musicAddedInAlbum.albumId())
        .albumName(musicAddedInAlbum.albumName())
        .build()));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_ADDED_IN_ALBUM)
    public void handleMusicRemovedInAlbum(MusicRemovedInAlbum musicRemovedInAlbum) {
        log.info("Music removed in album: {}", musicRemovedInAlbum);
        super.getRepository().findOne(Example.of(Music.builder()
        .id(musicRemovedInAlbum.musicId())
        .albumId(musicRemovedInAlbum.albumId())
        .build())).ifPresent(music -> super.save(music.toBuilder()
            .albumId(null)
            .albumName(null)
            .build()));
    }

}

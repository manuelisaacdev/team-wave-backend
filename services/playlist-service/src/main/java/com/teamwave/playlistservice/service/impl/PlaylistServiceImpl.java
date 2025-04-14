package com.teamwave.playlistservice.service.impl;

import com.teamwave.common.dto.kafka.*;
import com.teamwave.playlistservice.config.KafkaConfig;
import com.teamwave.playlistservice.dto.PlaylistDTO;
import com.teamwave.playlistservice.exception.DataNotFoundException;
import com.teamwave.playlistservice.model.Playlist;
import com.teamwave.playlistservice.repository.PlaylistRepository;
import com.teamwave.playlistservice.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PlaylistServiceImpl extends AbstractServiceImpl<Playlist, UUID, PlaylistRepository> implements PlaylistService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PlaylistServiceImpl(PlaylistRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Playlist findById(UUID playlistId) throws DataNotFoundException {
        return super.findById(playlistId);
    }

    @Override
    public Playlist create(UUID userId, PlaylistDTO playlistDTO) {
        return super.save(Playlist.builder()
        .userId(userId)
        .name(playlistDTO.name())
        .privacy(playlistDTO.privacy())
        .description(playlistDTO.description())
        .build());
    }

    @Override
    public List<Playlist> create(UUID userId, List<PlaylistDTO> playlistsDTO) {
        return super.getRepository().saveAll(playlistsDTO.stream().map(playlistDTO -> Playlist.builder()
        .userId(userId)
        .name(playlistDTO.name())
        .privacy(playlistDTO.privacy())
        .description(playlistDTO.description())
        .build()).toList());
    }

    @Override
    public Playlist update(UUID playlistId, PlaylistDTO playlistDTO) {
        return super.save(super.findById(playlistId).toBuilder()
        .name(playlistDTO.name())
        .privacy(playlistDTO.privacy())
        .description(playlistDTO.description())
        .build());
    }

    @Override
    public void delete(UUID id) {
        Playlist playlist = super.findById(id);

        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                playlist.getCover(),
                FileType.PLAYLIST_COVER
        ));

        kafkaTemplate.send(KafkaConfig.TOPIC_PLAYLIST_DELETED, new PlaylistDeletedDTO(playlist.getId()));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("User deleted: {}", userDeletedDTO);
        List<Playlist> playlists = super.getRepository().findAll(Example.of(Playlist.builder()
        .userId(userDeletedDTO.userId()).build()));

        super.getRepository().deleteAll(playlists);

        playlists.forEach(playlist -> {
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                    playlist.getCover(),
                    FileType.PLAYLIST_COVER
            ));
            kafkaTemplate.send(KafkaConfig.TOPIC_PLAYLIST_DELETED, new PlaylistDeletedDTO(playlist.getId()));
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_PLAYLIST_COVER)
    public void handlePlaylistCover(FileUploadedDTO fileUploadedDTO) {
        log.info("Playlist cover: {}", fileUploadedDTO);
        super.getRepository().findById(fileUploadedDTO.ownerId())
        .ifPresent(playlist -> {
            String oldCover = playlist.getCover();
            super.save(playlist.toBuilder()
                    .cover(fileUploadedDTO.filename())
                    .build()
            );

            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                    oldCover,
                    FileType.PLAYLIST_COVER
            ));
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_PLAYLIST_REACTIONS)
    public void handlePlaylistReactions(PlaylistReactionsDTO playlistReactionsDTO) {
        log.info("Playlist reactions: {}", playlistReactionsDTO);
        super.getRepository().findById(playlistReactionsDTO.playlistId())
        .ifPresent(playlist -> super.save(playlist.toBuilder()
            .likes(playlistReactionsDTO.likes())
            .loves(playlistReactionsDTO.loves())
            .dislikes(playlistReactionsDTO.dislikes())
            .build()
        ));
    }

}

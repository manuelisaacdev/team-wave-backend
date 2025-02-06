package com.teamwave.reactionservice.service.impl;

import com.teamwave.common.dto.kafka.PlaylistDeletedDTO;
import com.teamwave.common.dto.kafka.UpdatePlaylistReaction;
import com.teamwave.common.dto.kafka.UserDeletedDTO;
import com.teamwave.reactionservice.config.KafkaConfig;
import com.teamwave.reactionservice.dto.PlaylistReactionDTO;
import com.teamwave.reactionservice.model.PlaylistReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.repository.PlaylistReactionRepository;
import com.teamwave.reactionservice.service.PlaylistReactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PlaylistReactionServiceImpl extends AbstractReactionServiceImpl<PlaylistReaction, PlaylistReactionRepository> implements PlaylistReactionService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PlaylistReactionServiceImpl(PlaylistReactionRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public PlaylistReaction create(PlaylistReactionDTO playlistReactionDTO) {
        PlaylistReaction playlistReaction = super.save(PlaylistReaction.builder()
        .userId(playlistReactionDTO.userId())
        .playlistId(playlistReactionDTO.playlistId())
        .reactionType(playlistReactionDTO.reactionType())
        .build());
        notify(playlistReaction.getPlaylistId());
        return playlistReaction;
    }

    @Override
    public void delete(UUID reactId) {
        PlaylistReaction playlistReaction = super.findById(reactId);
        super.getRepository().delete(playlistReaction);
        notify(playlistReaction.getPlaylistId());
    }

    private void notify(UUID playlistId) {
        kafkaTemplate.send(KafkaConfig.TOPIC_PLAYLIST_REACTIONS, new UpdatePlaylistReaction(
            playlistId,
            super.getRepository().count(Example.of(PlaylistReaction.builder().playlistId(playlistId).reactionType(ReactionType.LIKE).build())),
            super.getRepository().count(Example.of(PlaylistReaction.builder().playlistId(playlistId).reactionType(ReactionType.DISLIKE).build())),
            super.getRepository().count(Example.of(PlaylistReaction.builder().playlistId(playlistId).reactionType(ReactionType.LOVE).build()))
        ));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("User deleted: {}", userDeletedDTO);
        List<PlaylistReaction> playlistReactions = super.getRepository().findAll(Example.of(PlaylistReaction.builder()
                .userId(userDeletedDTO.userId())
                .build()));
        super.getRepository().deleteAll(playlistReactions);
        playlistReactions.forEach(albumReaction -> notify(albumReaction.getPlaylistId()));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_PLAYLIST_DELETED)
    public void handlePlaylistDeleted(PlaylistDeletedDTO playlistDeletedDTO) {
        log.info("Playlist deleted: {}", playlistDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(PlaylistReaction.builder()
                .playlistId(playlistDeletedDTO.playlistId())
                .build())));
    }
}

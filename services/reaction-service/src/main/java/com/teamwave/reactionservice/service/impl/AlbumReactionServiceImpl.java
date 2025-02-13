package com.teamwave.reactionservice.service.impl;

import com.teamwave.common.dto.kafka.AlbumDeletedDTO;
import com.teamwave.common.dto.kafka.UpdateAlbumReaction;
import com.teamwave.common.dto.kafka.UserDeletedDTO;
import com.teamwave.reactionservice.config.KafkaConfig;
import com.teamwave.reactionservice.dto.AlbumReactionDTO;
import com.teamwave.reactionservice.model.AlbumReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.repository.AlbumReactionRepository;
import com.teamwave.reactionservice.service.AlbumReactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AlbumReactionServiceImpl extends AbstractReactionServiceImpl<AlbumReaction, AlbumReactionRepository> implements AlbumReactionService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AlbumReactionServiceImpl(AlbumReactionRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public AlbumReaction create(AlbumReactionDTO albumReactionDTO) {
        AlbumReaction albumReaction = super.save(AlbumReaction.builder()
        .userId(albumReactionDTO.userId())
        .albumId(albumReactionDTO.albumId())
        .reactionType(albumReactionDTO.reactionType())
        .build());
        notify(albumReactionDTO.albumId());
        return albumReaction;
    }

    @Override
    public void delete(UUID reactId) {
        AlbumReaction albumReaction = super.findById(reactId);
        super.getRepository().delete(albumReaction);
        notify(albumReaction.getAlbumId());
    }

    private void notify(UUID albumId) {
        kafkaTemplate.send(KafkaConfig.TOPIC_ALBUM_REACTIONS, new UpdateAlbumReaction(
            albumId,
            super.getRepository().count(Example.of(AlbumReaction.builder().albumId(albumId).reactionType(ReactionType.LIKE).build())),
            super.getRepository().count(Example.of(AlbumReaction.builder().albumId(albumId).reactionType(ReactionType.DISLIKE).build())),
            super.getRepository().count(Example.of(AlbumReaction.builder().albumId(albumId).reactionType(ReactionType.LOVE).build()))
        ));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("User deleted: {}", userDeletedDTO);
        List<AlbumReaction> albumReactions = super.getRepository().findAll(Example.of(AlbumReaction.builder()
                .userId(userDeletedDTO.userId())
                .build()));
        super.getRepository().deleteAll(albumReactions);
        albumReactions.forEach(albumReaction -> notify(albumReaction.getAlbumId()));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_ALBUM_DELETED)
    public void handleAlbumDeleted(AlbumDeletedDTO albumDeletedDTO) {
        log.info("Album deleted: {}", albumDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(AlbumReaction.builder()
                .albumId(albumDeletedDTO.albumId())
                .build())));
    }
}

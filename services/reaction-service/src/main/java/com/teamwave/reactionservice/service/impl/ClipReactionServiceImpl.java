package com.teamwave.reactionservice.service.impl;

import com.teamwave.common.dto.kafka.ClipDeletedDTO;
import com.teamwave.common.dto.kafka.UpdateAlbumReaction;
import com.teamwave.common.dto.kafka.UserDeletedDTO;
import com.teamwave.reactionservice.config.KafkaConfig;
import com.teamwave.reactionservice.dto.ClipReactionDTO;
import com.teamwave.reactionservice.model.ClipReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.repository.ClipReactionRepository;
import com.teamwave.reactionservice.service.ClipReactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ClipReactionServiceImpl extends AbstractReactionServiceImpl<ClipReaction, ClipReactionRepository> implements ClipReactionService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ClipReactionServiceImpl(ClipReactionRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public ClipReaction create(ClipReactionDTO clipReactionDTO) {
        ClipReaction albumReaction = super.save(ClipReaction.builder()
                .userId(clipReactionDTO.userId())
                .clipId(clipReactionDTO.clipId())
                .reactionType(clipReactionDTO.reactionType())
                .build());
        notify(clipReactionDTO.clipId());
        return albumReaction;
    }

    @Override
    public void delete(UUID reactId) {
        ClipReaction clipReaction = super.findById(reactId);
        super.getRepository().delete(clipReaction);
        notify(clipReaction.getClipId());
    }

    private void notify(UUID clipId) {
        kafkaTemplate.send(KafkaConfig.TOPIC_CLIP_REACTIONS, new UpdateAlbumReaction(
            clipId,
            super.getRepository().count(Example.of(ClipReaction.builder().clipId(clipId).reactionType(ReactionType.LIKE).build())),
            super.getRepository().count(Example.of(ClipReaction.builder().clipId(clipId).reactionType(ReactionType.DISLIKE).build())),
            super.getRepository().count(Example.of(ClipReaction.builder().clipId(clipId).reactionType(ReactionType.LOVE).build()))
        ));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("User deleted: {}", userDeletedDTO);
        List<ClipReaction> clipReactions = super.getRepository().findAll(Example.of(ClipReaction.builder()
                .userId(userDeletedDTO.userId())
                .build()));
        super.getRepository().deleteAll(clipReactions);
        clipReactions.forEach(albumReaction -> notify(albumReaction.getClipId()));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_CLIP_DELETED)
    public void handleClipDeleted(ClipDeletedDTO clipDeletedDTO) {
        log.info("Clip deleted: {}", clipDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(ClipReaction.builder()
                .clipId(clipDeletedDTO.clipId())
                .build())));
    }
}

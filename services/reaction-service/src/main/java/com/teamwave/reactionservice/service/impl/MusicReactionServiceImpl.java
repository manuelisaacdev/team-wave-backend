package com.teamwave.reactionservice.service.impl;

import com.teamwave.common.dto.kafka.MusicDeletedDTO;
import com.teamwave.common.dto.kafka.UpdateMusicReaction;
import com.teamwave.common.dto.kafka.UserDeletedDTO;
import com.teamwave.reactionservice.config.KafkaConfig;
import com.teamwave.reactionservice.dto.MusicReactionDTO;
import com.teamwave.reactionservice.model.MusicReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.repository.MusicReactionRepository;
import com.teamwave.reactionservice.service.MusicReactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MusicReactionServiceImpl extends AbstractReactionServiceImpl<MusicReaction, MusicReactionRepository> implements MusicReactionService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MusicReactionServiceImpl(MusicReactionRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public MusicReaction create(MusicReactionDTO musicReactionDTO) {
        MusicReaction musicReaction = save(MusicReaction.builder()
        .userId(musicReactionDTO.userId())
        .musicId(musicReactionDTO.musicId())
        .reactionType(musicReactionDTO.reactionType())
        .build());
        notify(musicReaction.getMusicId());
        return musicReaction;
    }

    @Override
    public void delete(UUID reactId) {
        MusicReaction musicReaction = super.findById(reactId);
        super.getRepository().delete(musicReaction);
        notify(musicReaction.getUserId());
    }

    private void notify(UUID musicId) {
        kafkaTemplate.send(KafkaConfig.TOPIC_MUSIC_REACTIONS, new UpdateMusicReaction(
            musicId,
            super.getRepository().count(Example.of(MusicReaction.builder().musicId(musicId).reactionType(ReactionType.LIKE).build())),
            super.getRepository().count(Example.of(MusicReaction.builder().musicId(musicId).reactionType(ReactionType.DISLIKE).build())),
            super.getRepository().count(Example.of(MusicReaction.builder().musicId(musicId).reactionType(ReactionType.LOVE).build()))
        ));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("User deleted: {}", userDeletedDTO);
        List<MusicReaction> musicReactions = super.getRepository().findAll(Example.of(MusicReaction.builder()
                .userId(userDeletedDTO.userId())
                .build()));
        super.getRepository().deleteAll(musicReactions);
        musicReactions.forEach(albumReaction -> notify(albumReaction.getMusicId()));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_DELETED)
    public void handleMusicDeleted(MusicDeletedDTO musicDeletedDTO) {
        log.info("Music deleted: {}", musicDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(MusicReaction.builder()
                .musicId(musicDeletedDTO.musicId())
                .build())));
    }
}

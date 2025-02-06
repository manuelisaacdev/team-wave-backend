package com.teamwave.reactionservice.service;

import com.teamwave.reactionservice.dto.MusicReactionDTO;
import com.teamwave.reactionservice.model.MusicReaction;

public interface MusicReactionService extends AbstractReactionService<MusicReaction> {
    MusicReaction create(MusicReactionDTO musicReactionDTO);
}

package com.teamwave.reactionservice.service;

import com.teamwave.reactionservice.dto.ClipReactionDTO;
import com.teamwave.reactionservice.model.ClipReaction;

public interface ClipReactionService extends AbstractReactionService<ClipReaction> {
    ClipReaction create(ClipReactionDTO clipReactionDTO);
}

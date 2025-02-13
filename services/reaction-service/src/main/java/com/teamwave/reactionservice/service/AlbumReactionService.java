package com.teamwave.reactionservice.service;

import com.teamwave.reactionservice.dto.AlbumReactionDTO;
import com.teamwave.reactionservice.model.AlbumReaction;

public interface AlbumReactionService extends AbstractReactionService<AlbumReaction> {
    AlbumReaction create(AlbumReactionDTO albumReactionDTO);
}

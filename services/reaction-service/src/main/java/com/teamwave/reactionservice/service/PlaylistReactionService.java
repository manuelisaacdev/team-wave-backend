package com.teamwave.reactionservice.service;

import com.teamwave.reactionservice.dto.PlaylistReactionDTO;
import com.teamwave.reactionservice.model.PlaylistReaction;

public interface PlaylistReactionService extends AbstractReactionService<PlaylistReaction> {
    PlaylistReaction create(PlaylistReactionDTO playlistReactionDTO);
}

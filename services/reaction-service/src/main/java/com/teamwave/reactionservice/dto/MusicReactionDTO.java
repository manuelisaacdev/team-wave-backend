package com.teamwave.reactionservice.dto;

import com.teamwave.reactionservice.model.ReactionType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MusicReactionDTO(
        @NotNull UUID userId,
        @NotNull UUID musicId,
        @NotNull ReactionType reactionType
) {
}

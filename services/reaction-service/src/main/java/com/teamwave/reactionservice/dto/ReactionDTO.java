package com.teamwave.reactionservice.dto;

import com.teamwave.reactionservice.model.ReactionType;
import jakarta.validation.constraints.NotNull;

public record ReactionDTO(
        @NotNull ReactionType reactionType
) {
}

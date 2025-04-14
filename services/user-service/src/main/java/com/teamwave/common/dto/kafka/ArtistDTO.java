package com.teamwave.common.dto.kafka;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ArtistDTO(
        @NotBlank String name,
        @NotNull Integer debutYear,
        @NotBlank String biography,
        UUID userId
) {
}

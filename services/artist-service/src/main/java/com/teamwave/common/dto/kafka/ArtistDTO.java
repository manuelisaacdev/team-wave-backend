package com.teamwave.common.dto.kafka;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Validated
public record ArtistDTO(
        @NotBlank String name,
        @NotNull Integer debutYear,
        @NotBlank String biography,
        @NotNull UUID userId
) {
}

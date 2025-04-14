package com.teamwave.artistservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateArtistDTO(
        @NotBlank String name,
        @NotNull Integer debutYear,
        @NotBlank String biography
) {
}

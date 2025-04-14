package com.teamwave.musicalgenresservice.dto;

import jakarta.validation.constraints.NotBlank;

public record MusicalGenreDTO(
        @NotBlank String name
) {
}

package com.teamwave.clipservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ClipDTO(
        @NotBlank String title,
        String description,
        @NotNull LocalDate releaseDate,
        @NotNull UUID musicId
) {
}

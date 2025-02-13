package com.teamwave.musicservice.dto;

import com.teamwave.musicservice.model.ReleaseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record MusicDTO(
        @NotBlank String title,
        String lyrics,
        String description,
        @NotNull LocalDate releaseDate,
        @NotNull ReleaseType releaseType,
        @NotNull UUID musicalGenreId
        ) {
}

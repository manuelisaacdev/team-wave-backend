package com.teamwave.playlistservice.dto;

import com.teamwave.playlistservice.model.Privacy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlaylistDTO(
        @NotBlank String name,
        String description,
        @NotNull Privacy privacy
) {
}

package com.teamwave.albumservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GalleryDTO(
        @NotNull UUID userId,
        @NotNull UUID playlistId
) {
}

package com.teamwave.playlistservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GalleryDTO(
        @NotNull UUID userId,
        @NotNull UUID playlistId) {
}

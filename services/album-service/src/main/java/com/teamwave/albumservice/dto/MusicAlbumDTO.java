package com.teamwave.albumservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MusicAlbumDTO(
        @NotNull UUID musicId,
        @NotNull UUID albumId
) {
}

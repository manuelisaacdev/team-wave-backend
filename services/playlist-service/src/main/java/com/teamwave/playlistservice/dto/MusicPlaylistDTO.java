package com.teamwave.playlistservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MusicPlaylistDTO(
        @NotNull UUID musicId,
        @NotNull UUID playlistId
) {
}

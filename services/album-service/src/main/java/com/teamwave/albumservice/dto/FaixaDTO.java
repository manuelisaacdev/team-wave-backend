package com.teamwave.albumservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FaixaDTO(
        @NotNull UUID albumId,
        @NotNull UUID musicId
) {
}

package com.teamwave.clipservice.dto;

import java.util.UUID;

public record FavoriteDTO(
        UUID userId,
        UUID clipId
) {
}

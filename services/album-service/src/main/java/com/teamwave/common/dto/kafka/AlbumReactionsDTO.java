package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record AlbumReactionsDTO(
        UUID albumId,
        Long likes,
        Long dislikes,
        Long loves
) {
}

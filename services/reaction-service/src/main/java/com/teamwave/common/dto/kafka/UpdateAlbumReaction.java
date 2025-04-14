package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record UpdateAlbumReaction(
        UUID albumId,
        Long likes,
        Long dislikes,
        Long loves
) {
}

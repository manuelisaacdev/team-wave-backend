package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record UpdatePlaylistReaction(
        UUID playlistId,
        Long likes,
        Long dislikes,
        Long loves
) {
}

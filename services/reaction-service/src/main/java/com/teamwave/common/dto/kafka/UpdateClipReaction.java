package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record UpdateClipReaction(
        UUID clipId,
        Long likes,
        Long dislikes,
        Long loves
) {
}

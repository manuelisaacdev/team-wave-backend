package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record UpdateMusicReaction(
    UUID musicId,
    Long likes,
    Long dislikes,
    Long loves
) {
}

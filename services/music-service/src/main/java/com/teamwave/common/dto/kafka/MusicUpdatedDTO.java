package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record MusicUpdatedDTO(
        UUID musicId,
        String cover,
        Long duration
) {
}

package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record AudioUpdatedDTO(UUID musicId, Long duration, String filename) {
}

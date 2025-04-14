package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record MediaFileUploadedDTO(
        UUID ownerId,
        Long duration,
        String filename
) {
}

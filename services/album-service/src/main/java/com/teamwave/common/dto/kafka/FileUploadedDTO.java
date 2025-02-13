package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record FileUploadedDTO(
        UUID ownerId,
        String filename
) {
}

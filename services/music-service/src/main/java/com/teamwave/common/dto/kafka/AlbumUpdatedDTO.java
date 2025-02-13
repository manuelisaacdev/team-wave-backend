package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record AlbumUpdatedDTO(UUID albumId, String name) {
}

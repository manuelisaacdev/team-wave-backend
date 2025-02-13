package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record PlaylistDeletedDTO(
        UUID playlistId
) {
}

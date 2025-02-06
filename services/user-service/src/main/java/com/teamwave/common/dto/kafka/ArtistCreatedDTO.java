package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record ArtistCreatedDTO(
        UUID userId,
        UUID artistId
) {
}

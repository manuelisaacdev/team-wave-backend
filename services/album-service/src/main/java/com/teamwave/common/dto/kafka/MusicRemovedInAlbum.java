package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record MusicRemovedInAlbum(UUID albumId, UUID musicId) {
}

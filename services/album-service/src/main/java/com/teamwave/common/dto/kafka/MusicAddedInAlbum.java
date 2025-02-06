package com.teamwave.common.dto.kafka;

import java.util.UUID;

public record MusicAddedInAlbum(UUID albumId, UUID musicId, String albumName) {
}

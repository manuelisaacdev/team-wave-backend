package com.teamwave.artistservice.service;

import com.teamwave.common.dto.kafka.ArtistDTO;
import com.teamwave.artistservice.dto.UpdateArtistDTO;
import com.teamwave.artistservice.model.Artist;

import java.util.UUID;

public interface ArtistService extends AbstractService<Artist, UUID> {
    Artist findByUserId(UUID userId);
    Artist create(ArtistDTO artistDTO);
    Artist update(UUID artistId, UpdateArtistDTO updateArtistDTO);
}

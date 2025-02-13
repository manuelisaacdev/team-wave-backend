package com.teamwave.musicalgenresservice.service;

import com.teamwave.musicalgenresservice.model.ArtistMusicalGenre;
import com.teamwave.musicalgenresservice.model.UserMusicalGenre;

import java.util.List;
import java.util.UUID;

public interface ArtistMusicalGenreService extends AbstractService<ArtistMusicalGenre, UUID> {
    ArtistMusicalGenre create(UUID artistId, UUID musicalGenreId);
    List<ArtistMusicalGenre> create(UUID artistId, List<UUID> musicalGenreIds);
}

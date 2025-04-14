package com.teamwave.artistservice.service;

import com.teamwave.artistservice.model.Favorite;

import java.util.UUID;

public interface FavoriteService extends AbstractService<Favorite, UUID> {
    Favorite create(UUID userId, UUID artistId);
}

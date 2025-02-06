package com.teamwave.clipservice.service;

import com.teamwave.clipservice.dto.FavoriteDTO;
import com.teamwave.clipservice.model.Favorite;

import java.util.UUID;

public interface FavoriteService extends AbstractService<Favorite, UUID> {
    Favorite create(FavoriteDTO favoriteDTO);
}

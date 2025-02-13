package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.model.Favorite;
import com.teamwave.artistservice.repository.FavoriteRepository;
import com.teamwave.artistservice.service.ArtistService;
import com.teamwave.artistservice.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FavoriteServiceImpl extends AbstractServiceImpl<Favorite, UUID, FavoriteRepository> implements FavoriteService {
    private final ArtistService artistService;

    public FavoriteServiceImpl(FavoriteRepository repository, ArtistService artistService) {
        super(repository);
        this.artistService = artistService;
    }

    @Override
    public Favorite create(UUID userId, UUID artistId) {
        return super.save(Favorite.builder()
        .userId(userId)
        .artist(artistService.findById(artistId))
        .build());
    }
}

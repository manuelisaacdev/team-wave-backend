package com.teamwave.clipservice.service.impl;

import com.teamwave.clipservice.dto.FavoriteDTO;
import com.teamwave.clipservice.model.Favorite;
import com.teamwave.clipservice.repository.FavoriteRepository;
import com.teamwave.clipservice.service.ClipService;
import com.teamwave.clipservice.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FavoriteServiceImpl extends AbstractServiceImpl<Favorite, UUID, FavoriteRepository> implements FavoriteService {
    private final ClipService clipService;

    public FavoriteServiceImpl(FavoriteRepository repository, ClipService clipService) {
        super(repository);
        this.clipService = clipService;
    }

    @Override
    public Favorite create(FavoriteDTO favoriteDTO) {
        return super.save(Favorite.builder()
        .userId(favoriteDTO.userId())
        .clip(clipService.findById(favoriteDTO.clipId()))
        .build());
    }
}

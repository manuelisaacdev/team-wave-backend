package com.teamwave.playlistservice.service.impl;

import com.teamwave.playlistservice.dto.GalleryDTO;
import com.teamwave.playlistservice.model.Gallery;
import com.teamwave.playlistservice.repository.GalleryRepository;
import com.teamwave.playlistservice.service.GalleryService;
import com.teamwave.playlistservice.service.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GalleryServiceImpl extends AbstractServiceImpl<Gallery, UUID, GalleryRepository> implements GalleryService {
    private final PlaylistService playlistService;

    public GalleryServiceImpl(GalleryRepository repository, PlaylistService playlistService) {
        super(repository);
        this.playlistService = playlistService;
    }

    @Override
    public Gallery create(GalleryDTO galleryDTO) {
        return super.save(Gallery.builder()
        .userId(galleryDTO.userId())
        .playlist(playlistService.findById(galleryDTO.playlistId()))
        .build());
    }
}

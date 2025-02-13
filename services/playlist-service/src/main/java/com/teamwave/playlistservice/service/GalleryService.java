package com.teamwave.playlistservice.service;

import com.teamwave.playlistservice.dto.GalleryDTO;
import com.teamwave.playlistservice.model.Gallery;

import java.util.UUID;

public interface GalleryService extends AbstractService<Gallery, UUID> {
    Gallery create(GalleryDTO galleryDTO);
}

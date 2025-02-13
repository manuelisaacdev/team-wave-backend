package com.teamwave.albumservice.service;

import com.teamwave.albumservice.dto.GalleryDTO;
import com.teamwave.albumservice.model.Gallery;

import java.util.UUID;

public interface GalleryService extends AbstractService<Gallery, UUID> {
    Gallery create(GalleryDTO galleryDTO);
}

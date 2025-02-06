package com.teamwave.playlistservice.repository;

import com.teamwave.playlistservice.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GalleryRepository extends JpaRepository<Gallery, UUID> {
}

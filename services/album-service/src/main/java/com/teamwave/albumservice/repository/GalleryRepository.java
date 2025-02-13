package com.teamwave.albumservice.repository;

import com.teamwave.albumservice.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GalleryRepository extends JpaRepository<Gallery, UUID> {
}

package com.teamwave.albumservice.repository;

import com.teamwave.albumservice.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {

}

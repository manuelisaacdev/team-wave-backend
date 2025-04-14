package com.teamwave.albumservice.repository;

import com.teamwave.albumservice.model.AlbumMusicalGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlbumMusicalGenreRepository extends JpaRepository<AlbumMusicalGenre, UUID> {
}

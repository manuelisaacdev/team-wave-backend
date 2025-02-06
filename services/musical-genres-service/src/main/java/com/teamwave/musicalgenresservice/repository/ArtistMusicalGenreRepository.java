package com.teamwave.musicalgenresservice.repository;

import com.teamwave.musicalgenresservice.model.ArtistMusicalGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ArtistMusicalGenreRepository extends JpaRepository<ArtistMusicalGenre, UUID> {

}

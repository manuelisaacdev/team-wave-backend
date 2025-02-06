package com.teamwave.musicalgenresservice.repository;

import com.teamwave.musicalgenresservice.model.MusicalGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicalGenreRepository extends JpaRepository<MusicalGenre, UUID> {
}

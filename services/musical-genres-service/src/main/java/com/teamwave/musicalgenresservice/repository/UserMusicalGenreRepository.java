package com.teamwave.musicalgenresservice.repository;

import com.teamwave.musicalgenresservice.model.UserMusicalGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserMusicalGenreRepository extends JpaRepository<UserMusicalGenre, UUID> {
}

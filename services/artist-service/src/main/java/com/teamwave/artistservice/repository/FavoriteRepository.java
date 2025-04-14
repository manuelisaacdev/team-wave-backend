package com.teamwave.artistservice.repository;

import com.teamwave.artistservice.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
}

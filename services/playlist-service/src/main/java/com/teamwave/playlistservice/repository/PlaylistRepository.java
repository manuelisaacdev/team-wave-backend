package com.teamwave.playlistservice.repository;

import com.teamwave.playlistservice.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaylistRepository extends JpaRepository<Playlist, UUID> {

}

package com.teamwave.playlistservice.repository;

import com.teamwave.playlistservice.model.MusicPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicPlaylistRepository extends JpaRepository<MusicPlaylist, UUID> {
}

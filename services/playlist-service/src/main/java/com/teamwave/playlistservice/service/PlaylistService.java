package com.teamwave.playlistservice.service;

import com.teamwave.playlistservice.dto.PlaylistDTO;
import com.teamwave.playlistservice.model.Playlist;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface PlaylistService extends AbstractService<Playlist, UUID> {
    Playlist create(UUID userId, PlaylistDTO PlaylistDTO);
    Playlist update(UUID playlistId, PlaylistDTO playlistDTO);
    List<Playlist> create(UUID userId, List<PlaylistDTO> playlistsDTO);
}

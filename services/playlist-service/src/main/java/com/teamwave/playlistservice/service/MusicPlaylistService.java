package com.teamwave.playlistservice.service;

import com.teamwave.playlistservice.dto.MusicPlaylistDTO;
import com.teamwave.playlistservice.model.MusicPlaylist;

import java.util.UUID;

public interface MusicPlaylistService extends AbstractService<MusicPlaylist, UUID> {
    MusicPlaylist create(MusicPlaylistDTO musicPlaylistDTO, String authorization);
}

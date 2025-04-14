package com.teamwave.musicservice.service;

import com.teamwave.musicservice.dto.MusicDTO;
import com.teamwave.musicservice.model.Music;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface MusicService extends AbstractService<Music, UUID> {
    Music create(UUID artistId, MusicDTO musicDTO, String authorization);
    Music update(UUID musicId, MusicDTO musicDTO, String authorization);

    List<Music> create(UUID artistId, List<MusicDTO> musicsDTO, String authorization);
}

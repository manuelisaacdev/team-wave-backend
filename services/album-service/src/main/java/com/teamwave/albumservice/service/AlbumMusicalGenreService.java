package com.teamwave.albumservice.service;

import com.teamwave.albumservice.dto.AlbumMusicalGenreDTO;
import com.teamwave.albumservice.model.AlbumMusicalGenre;

import java.util.UUID;

public interface AlbumMusicalGenreService extends AbstractService<AlbumMusicalGenre, UUID> {
    AlbumMusicalGenre create(AlbumMusicalGenreDTO albumMusicalGenreDTO, String authorization);
}

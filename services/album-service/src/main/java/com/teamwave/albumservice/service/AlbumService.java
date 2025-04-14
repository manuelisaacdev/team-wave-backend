package com.teamwave.albumservice.service;

import com.teamwave.albumservice.dto.AlbumDTO;
import com.teamwave.albumservice.model.Album;

import java.util.List;
import java.util.UUID;

public interface AlbumService extends AbstractService<Album, UUID> {
    Album create(UUID artistId, AlbumDTO AlbumDTO);
    List<Album> create(UUID artistId, List<AlbumDTO> albumsDTO, String authorization);
    Album update(UUID albumId, AlbumDTO AlbumDTO);

}

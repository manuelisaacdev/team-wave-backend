package com.teamwave.musicalgenresservice.service;

import com.teamwave.musicalgenresservice.dto.MusicalGenreDTO;
import com.teamwave.musicalgenresservice.model.MusicalGenre;

import java.util.List;
import java.util.UUID;

public interface MusicalGenreService extends AbstractService<MusicalGenre, UUID> {
    MusicalGenre create(MusicalGenreDTO musicalGenreDTO);
    List<MusicalGenre> create(List<MusicalGenreDTO> musicalGenresDTO);
    MusicalGenre update(UUID  musicalGenreId, MusicalGenreDTO musicalGenreDTO);
}

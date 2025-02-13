package com.teamwave.musicalgenresservice.service;

import com.teamwave.musicalgenresservice.model.UserMusicalGenre;

import java.util.List;
import java.util.UUID;

public interface UserMusicalGenreService extends AbstractService<UserMusicalGenre, UUID> {
    UserMusicalGenre create(UUID userId, UUID musicalGenreId);
    List<UserMusicalGenre> create(UUID userId, List<UUID> musicalGenreIds);
}

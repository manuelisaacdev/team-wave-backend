package com.teamwave.artistservice.service;

import com.teamwave.artistservice.dto.OccupationDTO;
import com.teamwave.artistservice.model.Occupation;

import java.util.UUID;

public interface OccupationService extends AbstractService<Occupation, UUID> {
    Occupation create(UUID artistId, OccupationDTO occupationDTO);
    Occupation update(UUID artistId, OccupationDTO occupationDTO);
}

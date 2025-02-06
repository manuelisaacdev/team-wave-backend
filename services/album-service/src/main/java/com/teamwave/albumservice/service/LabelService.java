package com.teamwave.albumservice.service;

import com.teamwave.albumservice.dto.LabelDTO;
import com.teamwave.albumservice.model.Label;

import java.util.UUID;

public interface LabelService extends AbstractService<Label, UUID> {
    Label create(UUID albumId, LabelDTO labelDTO);
    Label update(UUID labelId, LabelDTO labelDTO);
}

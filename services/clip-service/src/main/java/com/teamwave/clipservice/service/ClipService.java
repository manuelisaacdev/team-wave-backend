package com.teamwave.clipservice.service;

import com.teamwave.clipservice.dto.ClipDTO;
import com.teamwave.clipservice.model.Clip;

import java.util.UUID;

public interface ClipService extends AbstractService<Clip, UUID>{
    Clip create(ClipDTO clipDTO);
    Clip update(UUID clipId, ClipDTO clipDTO);
}
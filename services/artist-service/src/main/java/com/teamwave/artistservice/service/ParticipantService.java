package com.teamwave.artistservice.service;

import com.teamwave.artistservice.model.Participant;

import java.util.List;
import java.util.UUID;

public interface ParticipantService extends AbstractService<Participant, UUID> {
    List<Participant> findAllByMusic(UUID musicId);
    Participant create(UUID musicId, UUID artistId);
}

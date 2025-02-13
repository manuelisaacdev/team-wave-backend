package com.teamwave.artistservice.repository;

import com.teamwave.artistservice.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    List<Participant> findAllByMusicId(UUID musicId);
}

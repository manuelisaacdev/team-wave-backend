package com.teamwave.reactionservice.repository;

import com.teamwave.reactionservice.model.MusicReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicReactionRepository extends JpaRepository<MusicReaction, UUID> {

}

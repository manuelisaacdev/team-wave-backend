package com.teamwave.reactionservice.repository;

import com.teamwave.reactionservice.model.ClipReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClipReactionRepository extends JpaRepository<ClipReaction, UUID> {

}

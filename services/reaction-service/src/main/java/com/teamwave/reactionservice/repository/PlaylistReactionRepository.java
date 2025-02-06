package com.teamwave.reactionservice.repository;

import com.teamwave.reactionservice.model.PlaylistReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaylistReactionRepository extends JpaRepository<PlaylistReaction, UUID> {

}

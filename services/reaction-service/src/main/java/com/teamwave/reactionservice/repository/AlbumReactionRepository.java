package com.teamwave.reactionservice.repository;

import com.teamwave.reactionservice.model.AlbumReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlbumReactionRepository extends JpaRepository<AlbumReaction, UUID> {

}

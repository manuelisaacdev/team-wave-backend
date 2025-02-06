package com.teamwave.musicservice.repository;

import com.teamwave.musicservice.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicRepository extends JpaRepository<Music, UUID> {

}

package com.teamwave.artistservice.repository;

import com.teamwave.artistservice.model.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, UUID> {
}

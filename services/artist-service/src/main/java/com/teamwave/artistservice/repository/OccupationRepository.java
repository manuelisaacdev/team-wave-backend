package com.teamwave.artistservice.repository;

import com.teamwave.artistservice.model.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OccupationRepository extends JpaRepository<Occupation, UUID> {
}

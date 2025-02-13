package com.teamwave.clipservice.repository;

import com.teamwave.clipservice.model.Clip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClipRepository extends JpaRepository<Clip, UUID> {

}
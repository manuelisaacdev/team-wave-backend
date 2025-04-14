package com.teamwave.albumservice.repository;

import com.teamwave.albumservice.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabelRepository extends JpaRepository<Label, UUID> {
}

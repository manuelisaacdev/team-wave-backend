package com.teamwave.artistservice.repository;

import com.teamwave.artistservice.model.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FollowerRepository extends JpaRepository<Follower, UUID> {
}

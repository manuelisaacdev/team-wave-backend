package com.teamwave.artistservice.repository;

import com.teamwave.artistservice.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {
}

package com.teamwave.artistservice.service;

import com.teamwave.artistservice.model.Subscriber;

import java.util.UUID;

public interface SubscriberService extends AbstractService<Subscriber, UUID> {
    Subscriber create(UUID userId, UUID artistId);
}

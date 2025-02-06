package com.teamwave.artistservice.service;

import com.teamwave.artistservice.model.Follower;

import java.util.UUID;

public interface FollowerService extends AbstractService<Follower, UUID> {
    Follower create(UUID userId, UUID artistId);
}

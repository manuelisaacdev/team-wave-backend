package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.model.Follower;
import com.teamwave.artistservice.repository.FollowerRepository;
import com.teamwave.artistservice.service.ArtistService;
import com.teamwave.artistservice.service.FollowerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FollowerServiceImpl extends AbstractServiceImpl<Follower, UUID, FollowerRepository> implements FollowerService {
    private final ArtistService artistService;

    public FollowerServiceImpl(FollowerRepository repository, ArtistService artistService) {
        super(repository);
        this.artistService = artistService;
    }

    @Override
    public Follower create(UUID userId, UUID artistId) {
        return super.save(Follower.builder()
        .userId(userId)
        .artist(artistService.findById(artistId))
        .build());
    }
}

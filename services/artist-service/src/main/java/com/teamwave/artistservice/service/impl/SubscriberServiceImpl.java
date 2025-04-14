package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.model.Subscriber;
import com.teamwave.artistservice.repository.SubscriberRepository;
import com.teamwave.artistservice.service.ArtistService;
import com.teamwave.artistservice.service.SubscriberService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubscriberServiceImpl extends AbstractServiceImpl<Subscriber, UUID, SubscriberRepository> implements SubscriberService {
    private final ArtistService artistService;

    public SubscriberServiceImpl(SubscriberRepository repository, ArtistService artistService) {
        super(repository);
        this.artistService = artistService;
    }

    @Override
    public Subscriber create(UUID userId, UUID artistId) {
        return super.save(Subscriber.builder()
        .userId(userId)
        .artist(artistService.findById(artistId))
        .build());
    }
}

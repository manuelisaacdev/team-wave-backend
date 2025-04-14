package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.model.Participant;
import com.teamwave.artistservice.repository.ParticipantRepository;
import com.teamwave.artistservice.service.ArtistService;
import com.teamwave.artistservice.service.ParticipantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantServiceImpl extends AbstractServiceImpl<Participant, UUID, ParticipantRepository> implements ParticipantService {
    private final ArtistService artistService;

    public ParticipantServiceImpl(ParticipantRepository repository, ArtistService artistService) {
        super(repository);
        this.artistService = artistService;
    }

    @Override
    public List<Participant> findAllByMusic(UUID musicId) {
        return super.getRepository().findAllByMusicId(musicId);
    }

    @Override
    public Participant create(UUID musicId, UUID artistId) {
        return super.save(Participant.builder()
        .musicId(musicId)
        .artist(artistService.findById(artistId))
        .build());
    }
}

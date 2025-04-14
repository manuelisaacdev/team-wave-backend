package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.dto.OccupationDTO;
import com.teamwave.artistservice.model.Occupation;
import com.teamwave.artistservice.repository.OccupationRepository;
import com.teamwave.artistservice.service.ArtistService;
import com.teamwave.artistservice.service.OccupationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OccupationServiceImpl extends AbstractServiceImpl<Occupation, UUID, OccupationRepository> implements OccupationService {
    private final ArtistService artistService;

    public OccupationServiceImpl(OccupationRepository repository, ArtistService artistService) {
        super(repository);
        this.artistService = artistService;
    }

    @Override
    public Occupation create(UUID artistId, OccupationDTO occupationDTO) {
        return super.save(Occupation.builder()
        .description(occupationDTO.description())
        .artist(artistService.findById(artistId))
        .build());
    }

    @Override
    public Occupation update(UUID artistId, OccupationDTO occupationDTO) {
        return super.save(super.findById(artistId).toBuilder()
        .description(occupationDTO.description())
        .build());
    }
}

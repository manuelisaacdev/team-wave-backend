package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.dto.SocialMediaDTO;
import com.teamwave.artistservice.model.SocialMedia;
import com.teamwave.artistservice.repository.SocialMediaRepository;
import com.teamwave.artistservice.service.ArtistService;
import com.teamwave.artistservice.service.SocialMediaService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SocialMediaServiceImpl extends AbstractServiceImpl<SocialMedia, UUID, SocialMediaRepository> implements SocialMediaService {
    private final ArtistService artistService;

    public SocialMediaServiceImpl(SocialMediaRepository repository, ArtistService artistService) {
        super(repository);
        this.artistService = artistService;
    }

    @Override
    public SocialMedia create(UUID artistId, SocialMediaDTO socialMediaDTO) {
        return super.save(SocialMedia.builder()
        .url(socialMediaDTO.url())
        .artist(artistService.findById(artistId))
        .socialMediaType(socialMediaDTO.socialMediaType())
        .build());
    }

    @Override
    public SocialMedia update(UUID socialMediaId, SocialMediaDTO socialMediaDTO) {
        return super.save(super.findById(socialMediaId).toBuilder()
        .url(socialMediaDTO.url())
        .socialMediaType(socialMediaDTO.socialMediaType())
        .build());
    }
}

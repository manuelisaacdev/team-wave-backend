package com.teamwave.artistservice.service;

import com.teamwave.artistservice.dto.SocialMediaDTO;
import com.teamwave.artistservice.model.SocialMedia;

import java.util.UUID;

public interface SocialMediaService extends AbstractService<SocialMedia, UUID> {
    SocialMedia create(UUID artistId, SocialMediaDTO socialMediaDTO);
    SocialMedia update(UUID socialMediaId, SocialMediaDTO socialMediaDTO);
}

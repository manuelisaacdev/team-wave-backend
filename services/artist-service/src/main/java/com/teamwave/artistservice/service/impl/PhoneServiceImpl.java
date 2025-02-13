package com.teamwave.artistservice.service.impl;

import com.teamwave.artistservice.dto.PhoneDTO;
import com.teamwave.artistservice.dto.UpdatePhoneDTO;
import com.teamwave.artistservice.model.Phone;
import com.teamwave.artistservice.repository.PhoneRepository;
import com.teamwave.artistservice.service.ArtistService;
import com.teamwave.artistservice.service.PhoneService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PhoneServiceImpl extends AbstractServiceImpl<Phone, UUID, PhoneRepository> implements PhoneService {
    private final ArtistService artistService;

    public PhoneServiceImpl(PhoneRepository repository, ArtistService artistService) {
        super(repository);
        this.artistService = artistService;
    }

    @Override
    public Phone create(PhoneDTO phoneDTO) {
        return super.save(Phone.builder()
        .number(phoneDTO.number())
        .phoneType(phoneDTO.phoneType())
        .artist(artistService.findById(phoneDTO.artistId()))
        .countryId(phoneDTO.countryId())
        .build());
    }

    @Override
    public Phone update(UpdatePhoneDTO updatePhoneDTO) {
        return super.save(super.findById(updatePhoneDTO.phoneId()).toBuilder()
        .number(updatePhoneDTO.number())
        .phoneType(updatePhoneDTO.phoneType())
        .countryId(updatePhoneDTO.countryId())
        .build());
    }

}

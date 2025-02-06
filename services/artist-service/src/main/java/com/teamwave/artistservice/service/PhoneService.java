package com.teamwave.artistservice.service;

import com.teamwave.artistservice.dto.PhoneDTO;
import com.teamwave.artistservice.dto.UpdatePhoneDTO;
import com.teamwave.artistservice.model.Phone;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface PhoneService extends AbstractService<Phone, UUID> {
    Phone create(PhoneDTO phoneDTO);
    Phone update(UpdatePhoneDTO updatePhoneDTO);
}

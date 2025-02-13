package com.teamwave.artistservice.repository;

import com.teamwave.artistservice.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhoneRepository extends JpaRepository<Phone, UUID> {

}

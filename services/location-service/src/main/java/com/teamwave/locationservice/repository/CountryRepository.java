package com.teamwave.locationservice.repository;

import com.teamwave.locationservice.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {

    Optional<Country> findByCode(String code);

    Optional<Country> findByPhoneCode(String phoneCode);
}

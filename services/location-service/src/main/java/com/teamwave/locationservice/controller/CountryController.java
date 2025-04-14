package com.teamwave.locationservice.controller;

import com.teamwave.locationservice.dto.CountryDTO;
import com.teamwave.locationservice.exception.DataNotFountException;
import com.teamwave.locationservice.model.Country;
import com.teamwave.locationservice.repository.CountryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryController {
    private final CountryRepository countryRepository;

    @GetMapping
    public ResponseEntity<List<Country>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String phoneCode,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(countryRepository.findAll(Example.of(Country.builder()
        .name(name)
        .code(code)
        .phoneCode(phoneCode)
        .build(), ExampleMatcher.matching().withMatcher("name", matcher -> matcher.contains().ignoreCase())),
        Sort.by(direction, orderBy)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(countryRepository.findById(id)
        .orElseThrow(() -> new DataNotFountException("Country not found: " + id)));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Country> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(countryRepository.findByCode(code)
        .orElseThrow(() -> new DataNotFountException("Country not found by code: " + code)));
    }

    @GetMapping("/phoneCode/{phoneCode}")
    public ResponseEntity<Country> findByPhoneCode(@PathVariable String phoneCode) {
        return ResponseEntity.ok(countryRepository.findByPhoneCode(phoneCode)
                .orElseThrow(() -> new DataNotFountException("Country not found by phone code: " + phoneCode)));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Country>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String phoneCode,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(countryRepository.findAll(
            Example.of(Country.builder()
                .name(name)
                .code(code)
                .phoneCode(phoneCode)
                .build(), ExampleMatcher.matching().withMatcher("name", matcher -> matcher.contains().ignoreCase())),
            PageRequest.of(page, size, Sort.by(direction, orderBy))));
    }

    @PostMapping
    public ResponseEntity<Country> create(@RequestBody @Valid CountryDTO countryDTO) {
        return ResponseEntity.ok(countryRepository.save(Country.builder()
            .name(countryDTO.name())
            .code(countryDTO.code())
            .phoneCode(countryDTO.phoneCode())
            .build()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> update(@PathVariable UUID id, @RequestBody @Valid CountryDTO countryDTO) {
        return ResponseEntity.ok(countryRepository.save(
            countryRepository.findById(id)
            .orElseThrow(() -> new DataNotFountException("Country not found: " + id))
            .toBuilder()
            .name(countryDTO.name())
            .code(countryDTO.code())
            .phoneCode(countryDTO.phoneCode())
            .build()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        countryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

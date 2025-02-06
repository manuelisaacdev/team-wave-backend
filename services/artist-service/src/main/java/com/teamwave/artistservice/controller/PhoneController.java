package com.teamwave.artistservice.controller;

import com.teamwave.artistservice.dto.PhoneDTO;
import com.teamwave.artistservice.dto.UpdatePhoneDTO;
import com.teamwave.artistservice.model.Artist;
import com.teamwave.artistservice.model.Phone;
import com.teamwave.artistservice.service.PhoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phones")
public class PhoneController {
    private final PhoneService phoneService;

    @GetMapping
    public ResponseEntity<List<Phone>> findAll(
            Optional<UUID> artistId,
            @RequestParam(defaultValue = "number") String orderBy,
            @RequestParam(defaultValue = "ASC") Direction direction
    ) {
        return ResponseEntity.ok(phoneService.findAll(
            Example.of(Phone.builder()
            .artist(artistId.map(id -> Artist.builder().id(id).build()).orElse(null))
            .build()),
            orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<Phone> findAll(@RequestBody @Valid PhoneDTO phoneDTO) {
        return ResponseEntity.ok(phoneService.create(phoneDTO));
    }

    @PutMapping
    public ResponseEntity<Phone> findAll(@RequestBody @Valid UpdatePhoneDTO updatePhoneDTO) {
        return ResponseEntity.ok(phoneService.update(updatePhoneDTO));
    }

    @DeleteMapping("/{phoneId}")
    public ResponseEntity<Void> delete(@PathVariable UUID phoneId) {
        phoneService.delete(phoneId);
        return ResponseEntity.noContent().build();
    }
}

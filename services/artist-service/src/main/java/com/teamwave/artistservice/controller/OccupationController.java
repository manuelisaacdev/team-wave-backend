package com.teamwave.artistservice.controller;

import com.teamwave.artistservice.dto.OccupationDTO;
import com.teamwave.artistservice.model.Artist;
import com.teamwave.artistservice.model.Occupation;
import com.teamwave.artistservice.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists/occupation")
public class OccupationController {
    private final OccupationService occupationService;

    @GetMapping
    public ResponseEntity<List<Occupation>> findAll(
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(occupationService.findAll(
        Example.of(Occupation.builder().artist(Objects.isNull(artistId) ? null : Artist.builder().id(artistId).build()).build()),
        orderBy, direction));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Occupation>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(occupationService.findAll(
        page, size,
        Example.of(Occupation.builder().artist(Objects.isNull(artistId) ? null : Artist.builder().id(artistId).build()).build()),
        orderBy, direction));
    }

    @PostMapping("/{artistId}")
    public ResponseEntity<Occupation> create(@PathVariable UUID artistId, @RequestBody OccupationDTO occupationDTO) {
        return ResponseEntity.ok(occupationService.create(artistId, occupationDTO));
    }

    @PostMapping("/{occupationId}")
    public ResponseEntity<Occupation> update(@PathVariable UUID occupationId, @RequestBody OccupationDTO occupationDTO) {
        return ResponseEntity.ok(occupationService.update(occupationId, occupationDTO));
    }

    @DeleteMapping("/{occupationId}")
    public ResponseEntity<Void> delete(@PathVariable UUID occupationId) {
        occupationService.delete(occupationId);
        return ResponseEntity.noContent().build();
    }

}

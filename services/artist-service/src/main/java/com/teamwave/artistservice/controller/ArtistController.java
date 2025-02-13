package com.teamwave.artistservice.controller;

import com.teamwave.common.dto.kafka.ArtistDTO;
import com.teamwave.artistservice.dto.UpdateArtistDTO;
import com.teamwave.artistservice.model.Artist;
import com.teamwave.artistservice.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists")
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<Artist>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(artistService.findAll(Example.of(Artist.builder().name(name).build()), orderBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Artist> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(artistService.findByUserId(userId));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Artist>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(artistService.findAll(page, size, Example.of(Artist.builder().name(name).build()), orderBy, direction));
    }

    @PostMapping
    public ResponseEntity<Artist> create(@RequestBody @Valid ArtistDTO artistDTO) {
        return ResponseEntity.ok(artistService.create(artistDTO));
    }

    @PutMapping("/{artistId}")
    public ResponseEntity<Artist> update(@PathVariable UUID artistId, @RequestBody @Valid UpdateArtistDTO updateArtistDTO) {
        return ResponseEntity.ok(artistService.update(artistId, updateArtistDTO));
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<Void> delete(@PathVariable UUID artistId) {
        artistService.delete(artistId);
        return ResponseEntity.noContent().build();
    }

}

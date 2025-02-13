package com.teamwave.musicalgenresservice.controller;

import com.teamwave.musicalgenresservice.model.ArtistMusicalGenre;
import com.teamwave.musicalgenresservice.service.ArtistMusicalGenreService;
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
@RequestMapping("/musical-genres/artists")
public class ArtistMusicalGenreController {
    private final ArtistMusicalGenreService artistMusicalGenreService;

    @GetMapping
    public ResponseEntity<List<ArtistMusicalGenre>> findAll(
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "musicalGenre.name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(artistMusicalGenreService.findAll(
                Example.of(ArtistMusicalGenre.builder().artistId(artistId).build()),
                orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<ArtistMusicalGenre>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "musicalGenre.name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(artistMusicalGenreService.findAll(
                page, size,
                Example.of(ArtistMusicalGenre.builder().artistId(artistId).build()),
                orderBy, direction
        ));
    }

    @PostMapping("/{artistId}/{musicalGenreId}")
    public ResponseEntity<ArtistMusicalGenre> create(@PathVariable UUID artistId, @PathVariable UUID musicalGenreId) {
        return ResponseEntity.ok(artistMusicalGenreService.create(artistId, musicalGenreId));
    }

    @PostMapping("/{artistId}")
    public ResponseEntity<List<ArtistMusicalGenre>> create(@PathVariable UUID artistId, @RequestBody List<UUID> musicalGenreIds) {
        return ResponseEntity.ok(artistMusicalGenreService.create(artistId, musicalGenreIds));
    }

    @DeleteMapping("/{artistMusicalGenreId}")
    public ResponseEntity<Void> delete(@PathVariable UUID artistMusicalGenreId) {
        artistMusicalGenreService.deleteById(artistMusicalGenreId);
        return ResponseEntity.noContent().build();
    }
}

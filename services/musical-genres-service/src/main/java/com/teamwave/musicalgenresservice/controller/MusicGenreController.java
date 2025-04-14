package com.teamwave.musicalgenresservice.controller;

import com.teamwave.musicalgenresservice.dto.MusicalGenreDTO;
import com.teamwave.musicalgenresservice.model.MusicalGenre;
import com.teamwave.musicalgenresservice.service.MusicalGenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musical-genres")
public class MusicGenreController {
    private final MusicalGenreService musicalGenreService;

    @GetMapping
    public ResponseEntity<List<MusicalGenre>> findAll(
        @RequestParam(required = false) String name,
        @RequestParam(defaultValue = "name") String orderBy,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(musicalGenreService.findAll(Example.of(
                MusicalGenre.builder().name(name).build(),
                ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())),
                orderBy, direction
        ));
    }

    @GetMapping("/{musicalGenreId}")
    public ResponseEntity<MusicalGenre> findById(@PathVariable UUID musicalGenreId) {
        return ResponseEntity.ok(musicalGenreService.findById(musicalGenreId));
    }

    @GetMapping("/ids")
    public ResponseEntity<List<MusicalGenre>> findAllById(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(musicalGenreService.findAllById(ids));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<MusicalGenre>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return ResponseEntity.ok(musicalGenreService.findAll(
                page, size,
                Example.of(
                MusicalGenre.builder().name(name).build(),
                ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())),
                orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<MusicalGenre> save(@RequestBody @Valid MusicalGenreDTO musicalGenreDTO) {
        return ResponseEntity.ok(musicalGenreService.create(musicalGenreDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<List<MusicalGenre>> save(@RequestBody @Valid List<MusicalGenreDTO> musicGenresDTO) {
        return ResponseEntity.ok(musicalGenreService.create(musicGenresDTO));
    }

    @PutMapping("/{musicalGenreId}")
    public ResponseEntity<MusicalGenre> update(@PathVariable UUID musicalGenreId, @RequestBody @Valid MusicalGenreDTO musicalGenreDTO) {
        return ResponseEntity.ok(musicalGenreService.update(musicalGenreId, musicalGenreDTO));
    }

    @DeleteMapping("/{musicalGenreId}")
    public ResponseEntity<Void> delete(@PathVariable UUID musicalGenreId) {
        musicalGenreService.deleteById(musicalGenreId);
        return ResponseEntity.noContent().build();
    }

}

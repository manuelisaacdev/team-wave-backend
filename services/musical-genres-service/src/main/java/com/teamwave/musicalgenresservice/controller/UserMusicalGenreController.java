package com.teamwave.musicalgenresservice.controller;

import com.teamwave.musicalgenresservice.model.UserMusicalGenre;
import com.teamwave.musicalgenresservice.service.UserMusicalGenreService;
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
@RequestMapping("/musical-genres/users")
public class UserMusicalGenreController {
    private final UserMusicalGenreService userMusicalGenreService;

    @GetMapping
    public ResponseEntity<List<UserMusicalGenre>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "musicalGenre.name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(userMusicalGenreService.findAll(
                Example.of(UserMusicalGenre.builder().userId(userId).build()),
                orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<UserMusicalGenre>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "musicalGenre.name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(userMusicalGenreService.findAll(
                page, size,
                Example.of(UserMusicalGenre.builder().userId(userId).build()),
                orderBy, direction
        ));
    }

    @PostMapping("/{userId}/{musicalGenreId}")
    public ResponseEntity<UserMusicalGenre> create(@PathVariable UUID userId, @PathVariable UUID musicalGenreId) {
        return ResponseEntity.ok(userMusicalGenreService.create(userId, musicalGenreId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<List<UserMusicalGenre>> create(@PathVariable UUID userId, @RequestBody List<UUID> musicalGenreIds) {
        return ResponseEntity.ok(userMusicalGenreService.create(userId, musicalGenreIds));
    }

    @DeleteMapping("/{userMusicalGenreId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userMusicalGenreId) {
        userMusicalGenreService.deleteById(userMusicalGenreId);
        return ResponseEntity.noContent().build();
    }
}

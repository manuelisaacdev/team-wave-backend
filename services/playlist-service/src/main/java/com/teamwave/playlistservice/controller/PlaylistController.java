package com.teamwave.playlistservice.controller;

import com.teamwave.playlistservice.dto.PlaylistDTO;
import com.teamwave.playlistservice.model.Playlist;
import com.teamwave.playlistservice.service.PlaylistService;
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
@RequestMapping("/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    @GetMapping
    public ResponseEntity<List<Playlist>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(playlistService.findAll(
            Example.of(
                Playlist.builder().name(name).userId(userId).build(),
                ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<Playlist> findById(@PathVariable UUID playlistId) {
        return ResponseEntity.ok(playlistService.findById(playlistId));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Playlist>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(playlistService.findAll(
            page, size,
            Example.of(
                Playlist.builder().name(name).userId(userId).build(),
                ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(required = false) UUID userId) {
        return ResponseEntity.ok(playlistService.count(
                Example.of(Playlist.builder().userId(userId).build())
        ));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Playlist> create(@PathVariable UUID userId, @RequestBody @Valid PlaylistDTO playlistDTO) {
        return ResponseEntity.ok(playlistService.create(userId, playlistDTO));
    }

    @PostMapping("/all/{userId}")
    public ResponseEntity<List<Playlist>> create(@PathVariable UUID userId, @RequestBody @Valid List<PlaylistDTO> playlistsDTO) {
        return ResponseEntity.ok(playlistService.create(userId, playlistsDTO));
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<Playlist> update(@PathVariable UUID playlistId, @RequestBody PlaylistDTO playlistDTO) {
        return ResponseEntity.ok(playlistService.update(playlistId, playlistDTO));
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> delete(@PathVariable UUID playlistId) {
        playlistService.delete(playlistId);
        return ResponseEntity.noContent().build();
    }

}

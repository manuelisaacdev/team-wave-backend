package com.teamwave.playlistservice.controller;

import com.teamwave.playlistservice.dto.GalleryDTO;
import com.teamwave.playlistservice.model.Gallery;
import com.teamwave.playlistservice.model.Playlist;
import com.teamwave.playlistservice.service.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlists/galleries")
public class GalleryController {
    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<Gallery>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(galleryService.findAll(
            Example.of(
                Gallery.builder().userId(userId).playlist(Objects.isNull(name) ? null : Playlist.builder().name(name).build()).build(),
                ExampleMatcher.matching().withMatcher("playlist.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Gallery>> pagination(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(galleryService.findAll(
                page, size,
                Example.of(
                        Gallery.builder().userId(userId).playlist(Objects.isNull(name) ? null : Playlist.builder().name(name).build()).build(),
                        ExampleMatcher.matching().withMatcher("playlist.name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                ),
                orderBy, direction
        ));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(required = false) UUID userId) {
        return ResponseEntity.ok(galleryService.count(
            Example.of(Gallery.builder().userId(userId).build())
        ));
    }

    @PostMapping("/{userId}/{playlistId}")
    public ResponseEntity<Gallery> create(@RequestBody @Valid GalleryDTO galleryDTO) {
        return ResponseEntity.ok(galleryService.create(galleryDTO));
    }

    @DeleteMapping("/{galleryId}")
    public ResponseEntity<Void> delete(@PathVariable UUID galleryId) {
        galleryService.delete(galleryId);
        return ResponseEntity.noContent().build();
    }
}

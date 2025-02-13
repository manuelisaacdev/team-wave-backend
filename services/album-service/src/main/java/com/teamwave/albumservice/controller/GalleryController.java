package com.teamwave.albumservice.controller;

import com.teamwave.albumservice.dto.GalleryDTO;
import com.teamwave.albumservice.model.Album;
import com.teamwave.albumservice.model.Gallery;
import com.teamwave.albumservice.service.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/galleries")
public class GalleryController {
    private final GalleryService galleryService;

    @GetMapping
    public ResponseEntity<List<Gallery>> findAll(
            @RequestParam Optional<UUID> albumId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(galleryService.findAll(
                Example.of(Gallery.builder()
                        .userId(userId)
                        .album(albumId.map(id -> Album.builder().id(id).build()).orElse(null))
                        .build()
                ),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Gallery>> pagination(
            @RequestParam Optional<UUID> albumId,
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(galleryService.findAll(
            page, size,
            Example.of(Gallery.builder()
                .userId(userId)
                .album(albumId.map(id -> Album.builder().id(id).build()).orElse(null))
                .build()
            ),
            orderBy, direction
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

package com.teamwave.albumservice.controller;

import com.teamwave.albumservice.dto.AlbumDTO;
import com.teamwave.albumservice.model.Album;
import com.teamwave.albumservice.service.AlbumService;
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
@RequestMapping("/albums")
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(albumService.findAll(
            Example.of(
                Album.builder().name(name).artistId(artistId).build(),
                ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<Album> findById(@PathVariable UUID albumId) {
        return ResponseEntity.ok(albumService.findById(albumId));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Album>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(albumService.findAll(
            page, size,
            Example.of(
                Album.builder().name(name).artistId(artistId).build(),
                ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(required = false) UUID artistId) {
        return ResponseEntity.ok(albumService.count(
                Example.of(Album.builder().artistId(artistId).build())
        ));
    }

    @PostMapping("/{artistId}")
    public ResponseEntity<Album> create(@PathVariable UUID artistId, @RequestBody @Valid AlbumDTO albumDTO) {
        return ResponseEntity.ok(albumService.create(artistId, albumDTO));
    }

    @PostMapping("/all/{artistId}")
    public ResponseEntity<List<Album>> create(@PathVariable UUID artistId, @RequestBody @Valid List<AlbumDTO> albumsDTO, @RequestHeader String authorization) {
        return ResponseEntity.ok(albumService.create(artistId, albumsDTO, authorization));
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<Album> update(@PathVariable UUID albumId, @RequestBody @Valid AlbumDTO albumDTO) {
        return ResponseEntity.ok(albumService.update(albumId, albumDTO));
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> delete(@PathVariable UUID albumId) {
        albumService.delete(albumId);
        return ResponseEntity.noContent().build();
    }

}

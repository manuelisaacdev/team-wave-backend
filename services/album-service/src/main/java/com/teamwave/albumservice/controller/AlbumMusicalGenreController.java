package com.teamwave.albumservice.controller;

import com.teamwave.albumservice.dto.AlbumMusicalGenreDTO;
import com.teamwave.albumservice.model.AlbumMusicalGenre;
import com.teamwave.albumservice.service.AlbumMusicalGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/albums/musical-genres")
public class AlbumMusicalGenreController {
    private final AlbumMusicalGenreService albumMusicalGenreService;

    @PostMapping
    public ResponseEntity<AlbumMusicalGenre> create(@RequestBody AlbumMusicalGenreDTO albumMusicalGenreDTO, @RequestHeader String authorization) {
        return ResponseEntity.ok(albumMusicalGenreService.create(albumMusicalGenreDTO, authorization));
    }

    @DeleteMapping("/{albumMusicalGenreId}")
    public ResponseEntity<Void> delete(@PathVariable UUID albumMusicalGenreId) {
        albumMusicalGenreService.delete(albumMusicalGenreId);
        return ResponseEntity.ok().build();
    }
}

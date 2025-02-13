package com.teamwave.playlistservice.controller;

import com.teamwave.playlistservice.dto.MusicPlaylistDTO;
import com.teamwave.playlistservice.model.MusicPlaylist;
import com.teamwave.playlistservice.model.Playlist;
import com.teamwave.playlistservice.service.MusicPlaylistService;
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
@RequestMapping("/playlists/musics")
public class MusicPlaylistController {
    private final MusicPlaylistService musicPlaylistService;

    @GetMapping
    public ResponseEntity<List<MusicPlaylist>> findAll(
        @RequestParam Optional<UUID> playlistId,
        @RequestParam(defaultValue = "createdAt") String orderBy,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(musicPlaylistService.findAll(
            Example.of(MusicPlaylist.builder()
            .playlist(playlistId.map(id -> Playlist.builder().id(id).build()).orElse(null))
            .build()),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<MusicPlaylist>> pagination(
            @RequestParam Optional<UUID> playlistId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(musicPlaylistService.findAll(
            page,
            size,
            Example.of(MusicPlaylist.builder()
            .playlist(playlistId.map(id -> Playlist.builder().id(id).build()).orElse(null))
            .build()),
            orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<MusicPlaylist> create(@RequestBody @Valid MusicPlaylistDTO musicPlaylistDTO, @RequestHeader String authorization) {
        return ResponseEntity.ok(musicPlaylistService.create(musicPlaylistDTO, authorization));
    }

    @DeleteMapping("/{musicPlaylistId}")
    public ResponseEntity<Void> delete(@PathVariable UUID musicPlaylistId) {
        musicPlaylistService.delete(musicPlaylistId);
        return ResponseEntity.noContent().build();
    }
}

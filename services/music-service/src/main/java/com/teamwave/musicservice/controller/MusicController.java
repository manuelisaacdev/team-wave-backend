package com.teamwave.musicservice.controller;

import com.teamwave.musicservice.dto.MusicDTO;
import com.teamwave.musicservice.dto.MusicInfoDTO;
import com.teamwave.musicservice.model.Music;
import com.teamwave.musicservice.model.ReleaseType;
import com.teamwave.musicservice.service.MusicService;
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
@RequestMapping("/musics")
public class MusicController {
    private final MusicService musicService;

    @GetMapping
    public ResponseEntity<List<Music>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) UUID musicalGenreId,
            @RequestParam(required = false) ReleaseType releaseType,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "title") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(musicService.findAll(
            Example.of(
                Music.builder()
                    .title(title)
                    .musicalGenreId(musicalGenreId)
                    .releaseType(releaseType)
                    .available(available)
                    .artistId(artistId)
                    .build(),
                ExampleMatcher.matching()
                    .withMatcher("title", matcher -> matcher.contains().ignoreCase())
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<Music> findById(@PathVariable UUID musicId) {
        return ResponseEntity.ok(musicService.findById(musicId));
    }

    @GetMapping("/info/{musicId}")
    public ResponseEntity<MusicInfoDTO> getDuration(@PathVariable UUID musicId) {
        Music music = musicService.findById(musicId);
        return ResponseEntity.ok(new MusicInfoDTO(
            music.getCover(),
            music.getDuration()
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Music>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) UUID musicalGenreId,
            @RequestParam(required = false) ReleaseType releaseType,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "title") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(musicService.findAll(
            page, size,
            Example.of(
                Music.builder()
                    .title(title)
                    .musicalGenreId(musicalGenreId)
                    .releaseType(releaseType)
                    .available(available)
                    .artistId(artistId)
                    .build(),
                ExampleMatcher.matching()
                    .withMatcher("title", matcher -> matcher.contains().ignoreCase())
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam(required = false) UUID artistId) {
        return ResponseEntity.ok(musicService.count(
                Example.of(Music.builder().artistId(artistId).build())
        ));
    }

    @PostMapping("/{artistId}")
    public ResponseEntity<Music> create(@PathVariable UUID artistId, @RequestBody @Valid MusicDTO musicDTO, @RequestHeader String authorization) {
        return ResponseEntity.ok(musicService.create(artistId, musicDTO, authorization));
    }

    @PostMapping("/all/{artistId}")
    public ResponseEntity<List<Music>> create(@PathVariable UUID artistId, @RequestBody @Valid List<MusicDTO> musicsDTO, @RequestHeader String authorization) {
        return ResponseEntity.ok(musicService.create(artistId, musicsDTO, authorization));
    }

    @PutMapping("/{musicId}")
    public ResponseEntity<Music> update(@PathVariable UUID musicId, @RequestBody @Valid MusicDTO musicDTO, @RequestHeader String authorization) {
        return ResponseEntity.ok(musicService.update(musicId, musicDTO, authorization));
    }

    @DeleteMapping("/{musicId}")
    public ResponseEntity<Music> delete(@PathVariable UUID musicId) {
        musicService.delete(musicId);
        return ResponseEntity.noContent().build();
    }
}

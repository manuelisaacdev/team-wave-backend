package com.teamwave.clipservice.controller;

import com.teamwave.clipservice.dto.ClipDTO;
import com.teamwave.clipservice.model.Clip;
import com.teamwave.clipservice.service.ClipService;
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
@RequestMapping("/clips")
public class ClipController {
    private final ClipService clipService;

    @GetMapping
    public ResponseEntity<List<Clip>> findAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) UUID musicId,
            @RequestParam(defaultValue = "title") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
        ) {
        return ResponseEntity.ok(clipService.findAll(
                Example.of(Clip.builder()
                    .title(title)
                    .musicId(musicId)
                    .build(),
                ExampleMatcher.matching()
                    .withMatcher("title", matcher -> matcher.contains().ignoreCase())
                ),
                orderBy, direction
        ));
    }

    @GetMapping("/{clipId}")
    public ResponseEntity<Clip> findById(@PathVariable UUID clipId) {
        return ResponseEntity.ok(clipService.findById(clipId));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Clip>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) UUID musicId,
        @RequestParam(defaultValue = "title") String orderBy,
        @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(clipService.findAll(
                page, size,
                Example.of(Clip.builder()
                                .title(title)
                                .musicId(musicId)
                                .build(),
                        ExampleMatcher.matching()
                                .withMatcher("title", matcher -> matcher.contains().ignoreCase())
                ),
                orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<Clip> create(@Valid ClipDTO clipDTO) {
        return ResponseEntity.ok(clipService.create(clipDTO));
    }

    @PutMapping("/{clipId}")
    public ResponseEntity<Clip> update(@PathVariable UUID clipId, @RequestBody @Valid ClipDTO clipDTO) {
        return ResponseEntity.ok(clipService.update(clipId, clipDTO));
    }

    @DeleteMapping("/{clipId}")
    public ResponseEntity<Void> delete(@PathVariable UUID clipId) {
        clipService.delete(clipId);
        return ResponseEntity.noContent().build();
    }

}

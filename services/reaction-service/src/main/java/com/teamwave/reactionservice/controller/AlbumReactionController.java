package com.teamwave.reactionservice.controller;

import com.teamwave.reactionservice.dto.AlbumReactionDTO;
import com.teamwave.reactionservice.dto.ReactionDTO;
import com.teamwave.reactionservice.model.AlbumReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.service.AlbumReactionService;
import jakarta.validation.Valid;
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
@RequestMapping("/reactions/albums")
public class AlbumReactionController {
    private final AlbumReactionService albumReactionService;

    @GetMapping
    public ResponseEntity<List<AlbumReaction>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID albumId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(albumReactionService.findAll(
            Example.of(
                AlbumReaction.builder()
                .userId(userId)
                .albumId(albumId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<AlbumReaction>> pagination(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID albumId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(albumReactionService.findAll(
            page, size,
            Example.of(
                AlbumReaction.builder()
                .userId(userId)
                .albumId(albumId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<AlbumReaction> create(@RequestBody @Valid AlbumReactionDTO albumReactionDTO) {
        return ResponseEntity.ok(albumReactionService.create(albumReactionDTO));
    }

    @PutMapping("/{reactionId}")
    public ResponseEntity<AlbumReaction> update(@PathVariable UUID reactionId, @RequestBody @Valid ReactionDTO reactionDTO) {
        return ResponseEntity.ok(albumReactionService.update(reactionId, reactionDTO));
    }

    @DeleteMapping("/{reactionId}")
    public ResponseEntity<Void> delete(@PathVariable UUID reactionId) {
        albumReactionService.delete(reactionId);
        return ResponseEntity.noContent().build();
    }
}

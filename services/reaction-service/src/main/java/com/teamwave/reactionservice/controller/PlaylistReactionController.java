package com.teamwave.reactionservice.controller;

import com.teamwave.reactionservice.dto.PlaylistReactionDTO;
import com.teamwave.reactionservice.dto.ReactionDTO;
import com.teamwave.reactionservice.model.PlaylistReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.service.PlaylistReactionService;
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
@RequestMapping("/reactions/playlists")
public class PlaylistReactionController {
    private final PlaylistReactionService playlistReactionService;

    @GetMapping
    public ResponseEntity<List<PlaylistReaction>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID playlistId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(playlistReactionService.findAll(
            Example.of(
                PlaylistReaction.builder()
                .userId(userId)
                .playlistId(playlistId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<PlaylistReaction>> pagination(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID playlistId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(playlistReactionService.findAll(
            page, size,
            Example.of(
                PlaylistReaction.builder()
                .userId(userId)
                .playlistId(playlistId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<PlaylistReaction> create(@RequestBody @Valid PlaylistReactionDTO playlistReactionDTO) {
        return ResponseEntity.ok(playlistReactionService.create(playlistReactionDTO));
    }

    @PutMapping("/{reactionId}")
    public ResponseEntity<PlaylistReaction> update(@PathVariable UUID reactionId, @RequestBody @Valid ReactionDTO reactionDTO) {
        return ResponseEntity.ok(playlistReactionService.update(reactionId, reactionDTO));
    }

    @DeleteMapping("/{reactionId}")
    public ResponseEntity<Void> delete(@PathVariable UUID reactionId) {
        playlistReactionService.delete(reactionId);
        return ResponseEntity.noContent().build();
    }
}

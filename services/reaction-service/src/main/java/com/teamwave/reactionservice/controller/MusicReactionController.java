package com.teamwave.reactionservice.controller;

import com.teamwave.reactionservice.dto.MusicReactionDTO;
import com.teamwave.reactionservice.dto.ReactionDTO;
import com.teamwave.reactionservice.model.MusicReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.service.MusicReactionService;
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
@RequestMapping("/reactions/musics")
public class MusicReactionController {
    private final MusicReactionService musicReactionService;

    @GetMapping
    public ResponseEntity<List<MusicReaction>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID musicId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(musicReactionService.findAll(
            Example.of(
                MusicReaction.builder()
                .userId(userId)
                .musicId(musicId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<MusicReaction>> pagination(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID musicId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(musicReactionService.findAll(
            page, size,
            Example.of(
                MusicReaction.builder()
                .userId(userId)
                .musicId(musicId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<MusicReaction> create(@RequestBody @Valid MusicReactionDTO musicReactionDTO) {
        return ResponseEntity.ok(musicReactionService.create(musicReactionDTO));
    }


    @PutMapping("/{reactionId}")
    public ResponseEntity<MusicReaction> update(@PathVariable UUID reactionId, @RequestBody @Valid ReactionDTO reactionDTO) {
        return ResponseEntity.ok(musicReactionService.update(reactionId, reactionDTO));
    }

    @DeleteMapping("/{reactionId}")
    public ResponseEntity<Void> delete(@PathVariable UUID reactionId) {
        musicReactionService.delete(reactionId);
        return ResponseEntity.noContent().build();
    }
}

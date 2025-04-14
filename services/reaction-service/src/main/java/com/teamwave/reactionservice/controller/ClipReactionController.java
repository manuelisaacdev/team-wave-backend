package com.teamwave.reactionservice.controller;

import com.teamwave.reactionservice.dto.ClipReactionDTO;
import com.teamwave.reactionservice.dto.ReactionDTO;
import com.teamwave.reactionservice.model.ClipReaction;
import com.teamwave.reactionservice.model.ReactionType;
import com.teamwave.reactionservice.service.ClipReactionService;
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
@RequestMapping("/reactions/clips")
public class ClipReactionController {
    private final ClipReactionService clipReactionService;

    @GetMapping
    public ResponseEntity<List<ClipReaction>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID clipId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(clipReactionService.findAll(
            Example.of(
                    ClipReaction.builder()
                .userId(userId)
                .clipId(clipId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<ClipReaction>> pagination(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID clipId,
            @RequestParam(required = false) ReactionType reactionType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(clipReactionService.findAll(
            page, size,
            Example.of(
                ClipReaction.builder()
                .userId(userId)
                .clipId(clipId)
                .reactionType(reactionType)
                .build()
            ),
            orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<ClipReaction> create(@RequestBody @Valid ClipReactionDTO clipReactionDTO) {
        return ResponseEntity.ok(clipReactionService.create(clipReactionDTO));
    }


    @PutMapping("/{reactionId}")
    public ResponseEntity<ClipReaction> update(@PathVariable UUID reactionId, @RequestBody @Valid ReactionDTO reactionDTO) {
        return ResponseEntity.ok(clipReactionService.update(reactionId, reactionDTO));
    }

    @DeleteMapping("/{reactionId}")
    public ResponseEntity<Void> delete(@PathVariable UUID reactionId) {
        clipReactionService.delete(reactionId);
        return ResponseEntity.noContent().build();
    }
}

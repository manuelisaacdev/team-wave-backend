package com.teamwave.clipservice.controller;

import com.teamwave.clipservice.dto.FavoriteDTO;
import com.teamwave.clipservice.model.Clip;
import com.teamwave.clipservice.model.Favorite;
import com.teamwave.clipservice.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clips/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<Favorite>> findAll(
            @RequestParam Optional<UUID> clipId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC")Sort.Direction direction
            ) {
        return ResponseEntity.ok(favoriteService.findAll(
                Example.of(Favorite.builder()
                    .userId(userId)
                    .clip(clipId.map(id -> Clip.builder().id(id).build()).orElse(null))
                .build()),
                orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<Favorite> create(@RequestBody @Valid FavoriteDTO favoriteDTO) {
        return ResponseEntity.ok(favoriteService.create(favoriteDTO));
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> delete(@PathVariable UUID favoriteId) {
        favoriteService.delete(favoriteId);
        return ResponseEntity.noContent().build();
    }
}

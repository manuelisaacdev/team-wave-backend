package com.teamwave.artistservice.controller;

import com.teamwave.artistservice.model.Favorite;
import com.teamwave.artistservice.service.FavoriteService;
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
@RequestMapping("/artists/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<Favorite>> findAll(
        @RequestParam(required = false) UUID userId,
        @RequestParam(defaultValue = "createdAt") String orderBy,
        @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(favoriteService.findAll(
        Example.of(Favorite.builder().userId(userId).build()),
        orderBy, direction));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Favorite>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(favoriteService.findAll(
        page, size,
        Example.of(Favorite.builder().userId(userId).build()),
        orderBy, direction));
    }

    @PostMapping("/{userId}/{artistId}")
    public ResponseEntity<Favorite> create(@PathVariable UUID userId, @PathVariable UUID artistId) {
        return ResponseEntity.ok(favoriteService.create(userId, artistId));
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> delete(@PathVariable UUID favoriteId) {
        favoriteService.delete(favoriteId);
        return ResponseEntity.noContent().build();
    }
}

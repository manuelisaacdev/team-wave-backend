package com.teamwave.artistservice.controller;

import com.teamwave.artistservice.model.Artist;
import com.teamwave.artistservice.model.Follower;
import com.teamwave.artistservice.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists/followers")
public class FollowerController {
    private final FollowerService followerService;

    @GetMapping
    public ResponseEntity<List<Follower>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(followerService.findAll(
        Example.of(Follower.builder()
        .userId(userId)
        .artist(Objects.isNull(artistId) ? null : Artist.builder().id(artistId).build())
        .build()),
        orderBy, direction));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Follower>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(followerService.findAll(
        page, size,
        Example.of(Follower.builder()
        .userId(userId)
        .artist(Objects.isNull(artistId) ? null : Artist.builder().id(artistId).build())
        .build()),
        orderBy, direction));
    }

    @PostMapping("/{userId}/{artistId}")
    public ResponseEntity<Follower> create(@PathVariable UUID userId, @PathVariable UUID artistId) {
        return ResponseEntity.ok(followerService.create(userId, artistId));
    }

    @DeleteMapping("/{followerId}")
    public ResponseEntity<Follower> delete(@PathVariable UUID followerId) {
        followerService.delete(followerId);
        return ResponseEntity.noContent().build();
    }
}

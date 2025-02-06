package com.teamwave.artistservice.controller;

import com.teamwave.artistservice.model.Artist;
import com.teamwave.artistservice.model.Subscriber;
import com.teamwave.artistservice.service.SubscriberService;
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
@RequestMapping("/artists/subscribers")
public class SubscriberController {
    private final SubscriberService subscriberService;

    @GetMapping
    public ResponseEntity<List<Subscriber>> findAll(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(subscriberService.findAll(
        Example.of(Subscriber.builder()
        .userId(userId)
        .artist(Objects.isNull(artistId) ? null : Artist.builder().id(artistId).build())
        .build()),
        orderBy, direction));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Subscriber>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID artistId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(subscriberService.findAll(
        page, size,
        Example.of(Subscriber.builder()
        .userId(userId)
        .artist(Objects.isNull(artistId) ? null : Artist.builder().id(artistId).build())
        .build()),
        orderBy, direction));
    }

    @PostMapping("/{userId}/{artistId}")
    public ResponseEntity<Subscriber> create(@PathVariable UUID userId, @PathVariable UUID artistId) {
        return ResponseEntity.ok(subscriberService.create(userId, artistId));
    }

    @DeleteMapping("/{subscriberId}")
    public ResponseEntity<Subscriber> delete(@PathVariable UUID subscriberId) {
        subscriberService.delete(subscriberId);
        return ResponseEntity.noContent().build();
    }
}

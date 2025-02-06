package com.teamwave.artistservice.controller;

import com.teamwave.artistservice.dto.SocialMediaDTO;
import com.teamwave.artistservice.model.Artist;
import com.teamwave.artistservice.model.SocialMedia;
import com.teamwave.artistservice.model.SocialMediaType;
import com.teamwave.artistservice.service.SocialMediaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/artists/social-media")
public class SocialMediaController {
    private final SocialMediaService socialMediaService;

    @GetMapping
    public ResponseEntity<List<SocialMedia>> findAll(
            @RequestParam Optional<UUID> artistId,
            @RequestParam(required = false) SocialMediaType socialMediaType,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(socialMediaService.findAll(
                Example.of(SocialMedia.builder()
                        .socialMediaType(socialMediaType)
                        .artist(artistId.map(id -> Artist.builder().id(id).build()).orElse(null))
                .build()),
                orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<SocialMedia>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam Optional<UUID> artistId,
            @RequestParam(required = false) SocialMediaType socialMediaType,
            @RequestParam(defaultValue = "createAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(socialMediaService.findAll(
                page, size,
                Example.of(SocialMedia.builder()
                        .socialMediaType(socialMediaType)
                        .artist(artistId.map(id -> Artist.builder().id(id).build()).orElse(null))
                        .build()),
                orderBy, direction
        ));
    }

    @PostMapping("/{socialMediaId}")
    public ResponseEntity<SocialMedia> create(@PathVariable UUID socialMediaId, @RequestBody @Valid SocialMediaDTO socialMediaDTO) {
        return ResponseEntity.ok(socialMediaService.create(socialMediaId, socialMediaDTO));
    }

    @PutMapping("/{socialMediaId}")
    public ResponseEntity<SocialMedia> update(@PathVariable UUID socialMediaId, @RequestBody @Valid SocialMediaDTO socialMediaDTO) {
        return ResponseEntity.ok(socialMediaService.update(socialMediaId, socialMediaDTO));
    }

    @DeleteMapping("/{socialMediaId}")
    public ResponseEntity<Void> delete(@PathVariable UUID socialMediaId) {
        socialMediaService.delete(socialMediaId);
        return ResponseEntity.noContent().build();
    }
}

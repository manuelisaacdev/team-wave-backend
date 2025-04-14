package com.teamwave.albumservice.controller;

import com.teamwave.albumservice.dto.LabelDTO;
import com.teamwave.albumservice.model.Album;
import com.teamwave.albumservice.model.Label;
import com.teamwave.albumservice.service.LabelService;
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
@RequestMapping("/albums/labels")
public class LabelController {
    private final LabelService labelService;

    @GetMapping
    public ResponseEntity<List<Label>> findAll(
            @RequestParam(required = false) Optional<UUID> albumId,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(labelService.findAll(
            Example.of(Label.builder()
                .album(albumId.map(id -> Album.builder().id(id).build()).orElse(null))
            .build()),
            orderBy, direction
        ));
    }

    @PostMapping("/{albumId}")
    public ResponseEntity<Label> create(@PathVariable UUID albumId, @RequestBody @Valid LabelDTO labelDTO) {
        return ResponseEntity.ok(labelService.create(albumId, labelDTO));
    }

    @PutMapping("/{labelId}")
    public ResponseEntity<Label> update(@PathVariable UUID labelId, @RequestBody @Valid LabelDTO labelDTO) {
        return ResponseEntity.ok(labelService.update(labelId, labelDTO));
    }

    @DeleteMapping("/{labelId}")
    public ResponseEntity<Void> delete(@PathVariable UUID labelId) {
        labelService.delete(labelId);
        return ResponseEntity.noContent().build();
    }

}

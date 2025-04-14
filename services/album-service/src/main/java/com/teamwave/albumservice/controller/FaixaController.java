package com.teamwave.albumservice.controller;

import com.teamwave.albumservice.dto.FaixaDTO;
import com.teamwave.albumservice.model.Album;
import com.teamwave.albumservice.model.Faixa;
import com.teamwave.albumservice.service.FaixaService;
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
@RequestMapping("/albums/faixas")
public class FaixaController {
    private final FaixaService faixaService;

    @GetMapping
    public ResponseEntity<List<Faixa>> findAll(
        @RequestParam Optional<UUID> albumId,
        @RequestParam(defaultValue = "createdAt") String orderBy,
        @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(faixaService.findAll(
            Example.of(Faixa.builder()
            .album(albumId.map(id -> Album.builder().id(id).build()).orElse(null))
            .build()),
            orderBy, direction
        ));
    }

    @GetMapping("/pagination")
    public ResponseEntity<Page<Faixa>> pagination(
            @RequestParam Optional<UUID> albumId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String orderBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction
    ) {
        return ResponseEntity.ok(faixaService.findAll(
            page,
            size,
            Example.of(Faixa.builder()
            .album(albumId.map(id -> Album.builder().id(id).build()).orElse(null))
            .build()),
            orderBy, direction
        ));
    }

    @PostMapping
    public ResponseEntity<Faixa> create(@RequestBody @Valid FaixaDTO faixaDTO, @RequestHeader String authorization) {
        return ResponseEntity.ok(faixaService.create(faixaDTO, authorization));
    }

    @DeleteMapping("/{faixaId}")
    public ResponseEntity<Void> delete(@PathVariable UUID faixaId) {
        faixaService.delete(faixaId);
        return ResponseEntity.noContent().build();
    }
}

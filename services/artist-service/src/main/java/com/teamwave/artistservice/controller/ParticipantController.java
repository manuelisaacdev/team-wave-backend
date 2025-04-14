package com.teamwave.artistservice.controller;

import com.teamwave.artistservice.model.Participant;
import com.teamwave.artistservice.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participants")
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("/{musicId}/{artistId}")
    public ResponseEntity<Participant> create(@PathVariable UUID musicId, @PathVariable UUID artistId) {
        return ResponseEntity.ok(participantService.create(artistId, musicId));
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> delete(@PathVariable UUID participantId) {
        participantService.delete(participantId);
        return ResponseEntity.noContent().build();
    }
}

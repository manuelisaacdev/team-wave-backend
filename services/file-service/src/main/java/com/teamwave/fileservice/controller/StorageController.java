package com.teamwave.fileservice.controller;

import com.teamwave.fileservice.dto.FileDTO;
import com.teamwave.fileservice.dto.UploadDTO;
import com.teamwave.fileservice.exception.StorageFileNotFoundException;
import com.teamwave.fileservice.service.StorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class StorageController {
    private final StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<FileDTO> upload(@Valid UploadDTO uploadDTO) {
        return ResponseEntity.ok(storageService.resolve(uploadDTO));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}

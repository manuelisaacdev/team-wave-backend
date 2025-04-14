package com.teamwave.fileservice.service;

import com.teamwave.fileservice.dto.FileDTO;
import com.teamwave.fileservice.dto.UploadDTO;
import org.springframework.core.io.Resource;

public interface StorageService {
    void init();
    FileDTO resolve(UploadDTO uploadDTO);
    Resource loadAsResource(String filename);
}

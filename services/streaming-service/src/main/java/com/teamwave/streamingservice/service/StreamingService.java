package com.teamwave.streamingservice.service;

import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public interface StreamingService {
    Mono<Resource> loadResource(String filename, Path path, String range);
}

package com.teamwave.streamingservice.service.impl;

import com.teamwave.streamingservice.config.StreamingProperties;
import com.teamwave.streamingservice.service.StreamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

@Slf4j
@Service
public class StreamingServiceImpl implements StreamingService {
    private final ResourceLoader resourceLoader;
    private final StreamingProperties streamingProperties;

    public StreamingServiceImpl(ResourceLoader resourceLoader, StreamingProperties streamingProperties) {
        this.resourceLoader = resourceLoader;
        this.streamingProperties = streamingProperties;
    }

    @Override
    public Mono<Resource> loadResource(String filename, Path path, String range) {
        return Mono.fromSupplier(() -> resourceLoader.getResource(String.valueOf(
                streamingProperties.getLocation().resolve(path).resolve(filename).toUri()
        )));
    }

}

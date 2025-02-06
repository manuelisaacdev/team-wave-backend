package com.teamwave.streamingservice.controller;

import com.teamwave.streamingservice.config.StreamingProperties;
import com.teamwave.streamingservice.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/streaming")
public class StreamingController {
    private final StreamingService streamingService;
    private final StreamingProperties streamingProperties;

    @GetMapping(value = "/musics/{filename}", produces = "audio/mp3")
    public Mono<Resource> musics(@PathVariable String filename, @RequestHeader("Range") String range) {
        System.out.println("====================================> Music Range in bytes(): " + range);
        return streamingService.loadResource(filename, streamingProperties.getMusics(), range);
    }

    @GetMapping(value = "/clips/{filename}", produces = "video/mp4")
    public Mono<Resource> clips(@PathVariable String filename, @RequestHeader("Range") String range) {
        System.out.println("====================================> Clip Range in bytes(): " + range);
        return streamingService.loadResource(filename, streamingProperties.getClips(), range);
    }

}

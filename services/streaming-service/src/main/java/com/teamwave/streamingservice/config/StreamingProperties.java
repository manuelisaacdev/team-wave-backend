package com.teamwave.streamingservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Getter
@Setter
@Configuration
@ConfigurationProperties("streaming")
public class StreamingProperties {
    private Path location;
    private Path musics;
    private Path clips;
}

package com.teamwave.fileservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;


@Getter
@Setter
@Configuration
@ConfigurationProperties("storage")
public class StorageProperties {
    private Path location;
    private Path images;
    private Path musics;
    private Path clips;
    private Long maxImageFileSize;
}

package com.teamwave.fileservice.service.impl;

import com.teamwave.fileservice.config.KafkaConfig;
import com.teamwave.fileservice.config.StorageProperties;
import com.teamwave.fileservice.dto.FileDTO;
import com.teamwave.fileservice.dto.UploadDTO;
import com.teamwave.common.dto.kafka.DeleteFileDTO;
import com.teamwave.common.dto.kafka.FileUploadedDTO;
import com.teamwave.common.dto.kafka.MediaFileUploadedDTO;
import com.teamwave.fileservice.exception.StorageEmptyFileException;
import com.teamwave.fileservice.exception.StorageException;
import com.teamwave.fileservice.exception.StorageFileNotFoundException;
import com.teamwave.fileservice.service.MediaService;
import com.teamwave.fileservice.service.StorageService;
import com.teamwave.common.dto.kafka.FileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
    private final MediaService mediaService;
    private final StorageProperties storageProperties;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public StorageServiceImpl(MediaService mediaService,
                              StorageProperties storageProperties, KafkaTemplate<String, Object> kafkaTemplate) {
        this.mediaService = mediaService;
        this.kafkaTemplate = kafkaTemplate;
        this.storageProperties = storageProperties;
        init();
    }

    @Override
    public void init() {
        try {
            if (Files.notExists(storageProperties.getLocation())) {
                Files.createDirectories(storageProperties.getLocation());
                Files.createDirectories(storageProperties.getLocation().resolve(storageProperties.getImages()));
                Files.createDirectories(storageProperties.getLocation().resolve(storageProperties.getMusics()));
                Files.createDirectories(storageProperties.getLocation().resolve(storageProperties.getClips()));
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = storageProperties.getLocation().resolve(storageProperties.getImages()).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new StorageFileNotFoundException("Could not read file: " + filename);
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public FileDTO resolve(UploadDTO uploadDTO) {
        if (uploadDTO.file().isEmpty()) {
            throw new StorageEmptyFileException("Failed to store empty file.");
        }
        return switch (uploadDTO.fileType()) {
            case USER_PROFILE_PICTURE -> notify(
                uploadDTO,
                KafkaConfig.TOPIC_USER_PROFILE_PICTURE,
                store(uploadDTO.file(), buildDestinationFile(validateImageFileSize(uploadDTO.file()), storageProperties.getImages()))
            );
            case USER_COVER_PICTURE -> notify(
                    uploadDTO,
                    KafkaConfig.TOPIC_USER_COVER_PICTURE,
                    store(uploadDTO.file(), buildDestinationFile(validateImageFileSize(uploadDTO.file()), storageProperties.getImages()))
            );
            case ALBUM_COVER -> notify(
                    uploadDTO,
                    KafkaConfig.TOPIC_ALBUM_COVER,
                    store(uploadDTO.file(), buildDestinationFile(validateImageFileSize(uploadDTO.file()), storageProperties.getImages()))
            );
            case PLAYLIST_COVER -> notify(
                    uploadDTO,
                    KafkaConfig.TOPIC_PLAYLIST_COVER,
                    store(uploadDTO.file(), buildDestinationFile(validateImageFileSize(uploadDTO.file()), storageProperties.getImages()))
            );
            case MUSIC_COVER -> notify(
                    uploadDTO,
                    KafkaConfig.TOPIC_MUSIC_COVER,
                    store(uploadDTO.file(), buildDestinationFile(validateImageFileSize(uploadDTO.file()), storageProperties.getImages()))
            );
            case CLIP_THUMBNAIL -> notify(
                    uploadDTO,
                    KafkaConfig.TOPIC_CLIP_THUMBNAIL,
                    store(uploadDTO.file(), buildDestinationFile(validateImageFileSize(uploadDTO.file()), storageProperties.getImages()))
            );
            case MUSIC_AUDIO -> musicResolver(uploadDTO);
            case CLIP_VIDEO -> clipsResolver(uploadDTO);
        };
    }

    private FileDTO musicResolver(UploadDTO uploadDTO) {
        FileDTO fileDTO = store(uploadDTO.file(), buildDestinationFile(uploadDTO.file(), storageProperties.getMusics()));
        try {
            return notify(uploadDTO, KafkaConfig.TOPIC_MUSIC_AUDIO,fileDTO,
                    mediaService.getAudioDuration(storageProperties.getLocation()
                            .resolve(storageProperties.getMusics()).resolve(fileDTO.filename()).toFile()));
        } catch (Exception e) {
            delete(new DeleteFileDTO(fileDTO.filename(), FileType.MUSIC_AUDIO));
            throw new StorageException(e);
        }
    }

    private FileDTO clipsResolver(UploadDTO uploadDTO) {
        FileDTO fileDTO = store(uploadDTO.file(), buildDestinationFile(uploadDTO.file(), storageProperties.getClips()));
        try {
            return notify(uploadDTO, KafkaConfig.TOPIC_CLIP_VIDEO,fileDTO,
                    mediaService.getVideoDuration(storageProperties.getLocation()
                            .resolve(storageProperties.getMusics()).resolve(fileDTO.filename()).toFile()));
        } catch (Exception e) {
            delete(new DeleteFileDTO(fileDTO.filename(), FileType.CLIP_VIDEO));
            throw new StorageException(e);
        }
    }

    private Path buildDestinationFile(MultipartFile file, Path path) {
        return storageProperties.getLocation().resolve(path.resolve(String.format(
                "%s.%s",
                RandomStringUtils.secure().nextAlphanumeric(64),
                Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename()))
        ))).normalize().toAbsolutePath();
    }

    private FileDTO store(MultipartFile file, Path destinationFile) {
        try {
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile);
            }
            return new FileDTO(destinationFile.toFile().getName());
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    private FileDTO notify(UploadDTO uploadDTO, String topic, FileDTO fileDTO) {
        kafkaTemplate.send(topic, new FileUploadedDTO(uploadDTO.ownerId(), fileDTO.filename()));
        return fileDTO;
    }

    private FileDTO notify(UploadDTO uploadDTO, String topic, FileDTO fileDTO, Long duration) {
        kafkaTemplate.send(topic, new MediaFileUploadedDTO(uploadDTO.ownerId(), duration, fileDTO.filename()));
        return fileDTO;
    }

    private MultipartFile validateImageFileSize(MultipartFile file) {
        if (file.getSize() > storageProperties.getMaxImageFileSize()) {
            throw new StorageException("Image size exceeds maximum allowed size: " + storageProperties.getMaxImageFileSize());
        }
        return file;
    }

    private void delete(DeleteFileDTO deleteFileDTO) {
        if (Objects.isNull(deleteFileDTO.filename())) return;
        try {
            switch (deleteFileDTO.fileType()) {
                case USER_PROFILE_PICTURE, USER_COVER_PICTURE, ALBUM_COVER, PLAYLIST_COVER, MUSIC_COVER, CLIP_THUMBNAIL ->
                        Files.deleteIfExists(this.storageProperties.getLocation()
                        .resolve(storageProperties.getImages())
                        .resolve(deleteFileDTO.filename()).toAbsolutePath());
                case MUSIC_AUDIO ->
                        Files.deleteIfExists(this.storageProperties.getLocation()
                        .resolve(storageProperties.getMusics())
                        .resolve(deleteFileDTO.filename()).toAbsolutePath());
                case CLIP_VIDEO ->
                        Files.deleteIfExists(this.storageProperties.getLocation()
                        .resolve(storageProperties.getClips())
                        .resolve(deleteFileDTO.filename()).toAbsolutePath());
            }
        } catch (IOException e) {
            log.info("Error on try to delete file: {}", deleteFileDTO.filename());
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_DELETE_FILE)
    public void handleDelete(DeleteFileDTO payload) {
        log.info("Deleting files: {}", payload);
        delete(payload);
    }

}

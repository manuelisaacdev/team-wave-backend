package com.teamwave.clipservice.service.impl;

import com.teamwave.clipservice.config.KafkaConfig;
import com.teamwave.clipservice.dto.ClipDTO;
import com.teamwave.clipservice.model.Clip;
import com.teamwave.clipservice.repository.ClipRepository;
import com.teamwave.clipservice.service.ClipService;
import com.teamwave.common.dto.kafka.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ClipServiceImpl extends AbstractServiceImpl<Clip, UUID, ClipRepository> implements ClipService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ClipServiceImpl(ClipRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Clip create(ClipDTO clipDTO) {
        return super.save(Clip.builder()
        .title(clipDTO.title())
        .description(clipDTO.description())
        .releaseDate(clipDTO.releaseDate())
        .musicId(clipDTO.musicId())
        .build());
    }

    @Override
    public Clip update(UUID clipId, ClipDTO clipDTO) {
        return super.save(super.findById(clipId).toBuilder()
        .title(clipDTO.title())
        .description(clipDTO.description())
        .releaseDate(clipDTO.releaseDate())
        .musicId(clipDTO.musicId())
        .build());
    }

    @Override
    public void delete(UUID clipId) {
        delete(super.findById(clipId));
    }

    private void delete(Clip clip) {
        super.getRepository().delete(clip);

        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                clip.getThumbnail(),
                FileType.CLIP_THUMBNAIL
        ));

        kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                clip.getVideo(),
                FileType.CLIP_VIDEO
        ));

        kafkaTemplate.send(KafkaConfig.TOPIC_CLIP_DELETED, new ClipDeletedDTO(clip.getId()));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(groupId = KafkaConfig.GROUP_ID, topics = KafkaConfig.TOPIC_MUSIC_DELETED)
    public void handleMusicDeleted(MusicDeletedDTO musicDeletedDTO) {
        log.info("Music deleted: {}", musicDeletedDTO);
        super.getRepository().findOne(Example.of(Clip.builder()
        .musicId(musicDeletedDTO.musicId())
        .build())).ifPresent(this::delete);
    }

    @KafkaListener(groupId = KafkaConfig.GROUP_ID, topics = KafkaConfig.TOPIC_CLIP_REACTIONS)
    public void handleClipReactions(ClipReactionsDTO clipReactionsDTO) {
        log.info("Clip reactions: {}", clipReactionsDTO);
        super.getRepository().findById(clipReactionsDTO.clipId())
        .ifPresent(clip -> super.save(clip.toBuilder()
            .likes(clipReactionsDTO.likes())
            .dislikes(clipReactionsDTO.dislikes())
            .loves(clipReactionsDTO.loves())
            .build()
        ));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_CLIP_THUMBNAIL)
    public void handleClipThumbnail(FileUploadedDTO fileUploadedDTO) {
        log.info("Clip Thumbnail: {}", fileUploadedDTO);
        super.getRepository().findById(fileUploadedDTO.ownerId()).ifPresent(clip -> {
            String oldThumbnail = clip.getThumbnail();
            super.save(clip.toBuilder()
                    .thumbnail(fileUploadedDTO.filename())
                    .build());
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                    oldThumbnail,
                    FileType.CLIP_THUMBNAIL
            ));
        });
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_CLIP_VIDEO)
    public void handleMusicCover(MediaFileUploadedDTO mediaFileUploadedDTO) {
        log.info("Music audio: {}", mediaFileUploadedDTO);
        super.getRepository().findById(mediaFileUploadedDTO.ownerId()).ifPresent(clip -> {
            String oldVideo = clip.getVideo();
            super.save(clip.toBuilder()
                    .available(Boolean.TRUE)
                    .video(mediaFileUploadedDTO.filename())
                    .duration(mediaFileUploadedDTO.duration())
                    .build());
            kafkaTemplate.send(KafkaConfig.TOPIC_DELETE_FILE, new DeleteFileDTO(
                    oldVideo,
                    FileType.CLIP_VIDEO
            ));
        });
    }

}

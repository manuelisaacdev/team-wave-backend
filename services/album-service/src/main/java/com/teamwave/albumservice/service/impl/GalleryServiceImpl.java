package com.teamwave.albumservice.service.impl;

import com.teamwave.albumservice.config.KafkaConfig;
import com.teamwave.albumservice.dto.GalleryDTO;
import com.teamwave.albumservice.model.Gallery;
import com.teamwave.albumservice.repository.GalleryRepository;
import com.teamwave.albumservice.service.AlbumService;
import com.teamwave.albumservice.service.GalleryService;
import com.teamwave.common.dto.kafka.UserDeletedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class GalleryServiceImpl extends AbstractServiceImpl<Gallery, UUID, GalleryRepository> implements GalleryService {
    private final AlbumService albumService;

    public GalleryServiceImpl(GalleryRepository repository, AlbumService albumService) {
        super(repository);
        this.albumService = albumService;
    }

    @Override
    public Gallery create(GalleryDTO galleryDTO) {
        return super.save(Gallery.builder()
        .userId(galleryDTO.userId())
        .album(albumService.findById(galleryDTO.playlistId()))
        .build());
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_USER_DELETED)
    public void handleUserDeleted(UserDeletedDTO userDeletedDTO) {
        log.info("User deleted: {}", userDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(Gallery.builder()
        .userId(userDeletedDTO.userId())
        .build())));
    }
}

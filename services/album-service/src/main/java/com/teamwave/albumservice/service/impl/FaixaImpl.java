package com.teamwave.albumservice.service.impl;

import com.teamwave.albumservice.config.KafkaConfig;
import com.teamwave.albumservice.dto.FaixaDTO;
import com.teamwave.albumservice.dto.MusicInfoDTO;
import com.teamwave.albumservice.model.Album;
import com.teamwave.albumservice.model.Faixa;
import com.teamwave.albumservice.repository.FaixaRepository;
import com.teamwave.albumservice.service.AlbumService;
import com.teamwave.albumservice.service.FaixaService;
import com.teamwave.albumservice.service.MusicService;
import com.teamwave.common.dto.kafka.MusicAddedInAlbum;
import com.teamwave.common.dto.kafka.MusicDeletedDTO;
import com.teamwave.common.dto.kafka.MusicRemovedInAlbum;
import com.teamwave.common.dto.kafka.MusicUpdatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class FaixaImpl extends AbstractServiceImpl<Faixa, UUID, FaixaRepository> implements FaixaService {
    private final MusicService musicService;
    private final AlbumService albumService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FaixaImpl(FaixaRepository repository, MusicService musicService, AlbumService albumService, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.musicService = musicService;
        this.albumService = albumService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public Faixa create(FaixaDTO faixaDTO, String authorization) {
        MusicInfoDTO musicInfoDTO = musicService.getInfo(faixaDTO.musicId(), authorization);
        Album album = albumService.findById(faixaDTO.albumId());
        Faixa faixa = super.save(Faixa.builder()
        .musicId(faixaDTO.musicId())
        .duration(musicInfoDTO.duration())
        .album(album)
        .build());
        kafkaTemplate.send(KafkaConfig.TOPIC_MUSIC_ADDED_IN_ALBUM, new MusicAddedInAlbum(
                faixaDTO.albumId(),
                faixaDTO.musicId(),
                album.getName()
        ));
        return faixa;
    }

    @Override
    public void delete(UUID faixaId) {
        Faixa faixa = super.findById(faixaId);
        Album album = faixa.getAlbum();
        super.getRepository().delete(faixa);

        kafkaTemplate.send(KafkaConfig.TOPIC_MUSIC_ADDED_IN_ALBUM, new MusicRemovedInAlbum(
                album.getId(),
                faixa.getMusicId()
        ));
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_DELETED)
    public void handleMusicDeleted(MusicDeletedDTO musicDeletedDTO) {
        log.info("Music deleted: {}", musicDeletedDTO);
        super.getRepository().findOne(Example.of(Faixa.builder()
                .musicId(musicDeletedDTO.musicId()).build()
        )).ifPresent(faixa -> super.getRepository().delete(faixa));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_UPDATED)
    public void handleMusicUpdated(MusicUpdatedDTO musicUpdatedDTO) {
        log.info("Music updated: {}", musicUpdatedDTO);
        super.getRepository().findOne(Example.of(Faixa.builder()
            .musicId(musicUpdatedDTO.musicId()).build()
        )).ifPresent(faixa -> super.save(faixa.toBuilder()
            .duration(musicUpdatedDTO.duration())
            .build()
        ));
    }

}

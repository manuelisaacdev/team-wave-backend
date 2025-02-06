package com.teamwave.playlistservice.service.impl;

import com.teamwave.common.dto.kafka.MusicDeletedDTO;
import com.teamwave.common.dto.kafka.MusicUpdatedDTO;
import com.teamwave.playlistservice.config.KafkaConfig;
import com.teamwave.playlistservice.dto.MusicInfoDTO;
import com.teamwave.playlistservice.dto.MusicPlaylistDTO;
import com.teamwave.playlistservice.model.MusicPlaylist;
import com.teamwave.playlistservice.repository.MusicPlaylistRepository;
import com.teamwave.playlistservice.service.MusicPlaylistService;
import com.teamwave.playlistservice.service.MusicService;
import com.teamwave.playlistservice.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MusicPlaylistImpl extends AbstractServiceImpl<MusicPlaylist, UUID, MusicPlaylistRepository> implements MusicPlaylistService {
    private final MusicService musicService;
    private final PlaylistService playlistService;

    public MusicPlaylistImpl(MusicPlaylistRepository repository, MusicService musicService, PlaylistService playlistService) {
        super(repository);
        this.musicService = musicService;
        this.playlistService = playlistService;
    }

    @Override
    public MusicPlaylist create(MusicPlaylistDTO musicPlaylistDTO, String authorization) {
        MusicInfoDTO musicInfoDTO = musicService.getInfo(musicPlaylistDTO.musicId(), authorization);
        return super.save(MusicPlaylist.builder()
        .cover(musicInfoDTO.cover())
        .duration(musicInfoDTO.duration())
        .musicId(musicPlaylistDTO.musicId())
        .playlist(playlistService.findById(musicPlaylistDTO.musicId()))
        .build());
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // KAFKA METHODS
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_DELETED)
    public void handleMusicDeleted(MusicDeletedDTO musicDeletedDTO) {
        log.info("Music deleted: {}", musicDeletedDTO);
        super.getRepository().deleteAll(super.getRepository().findAll(Example.of(MusicPlaylist.builder()
            .musicId(musicDeletedDTO.musicId())
            .build()
        )));
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_MUSIC_UPDATED)
    public void handleMusicUpdated(MusicUpdatedDTO musicUpdatedDTO) {
        log.info("Music updated: {}", musicUpdatedDTO);
        super.getRepository().saveAll(super.getRepository().findAll(Example.of(MusicPlaylist.builder()
        .musicId(musicUpdatedDTO.musicId())
        .build()))
        .stream().map(musicPlaylist -> musicPlaylist.toBuilder()
            .cover(musicUpdatedDTO.cover())
            .duration(musicUpdatedDTO.duration())
            .build())
        .toList());
    }
}

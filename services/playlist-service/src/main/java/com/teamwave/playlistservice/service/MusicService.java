package com.teamwave.playlistservice.service;

import com.teamwave.playlistservice.dto.MusicInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient("music-service")
public interface MusicService {

    @GetMapping("/musics/info/{musicId}")
    MusicInfoDTO getInfo(@PathVariable("musicId") UUID musicId, @RequestHeader("authorization") String authorization);

}

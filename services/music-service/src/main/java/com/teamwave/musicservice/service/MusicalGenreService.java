package com.teamwave.musicservice.service;

import com.teamwave.common.model.MusicalGenre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient("musical-genres-service")
public interface MusicalGenreService {

    @GetMapping("/musical-genres")
    List<MusicalGenre> findAll(@RequestHeader String authorization);

    @GetMapping("/musical-genres/{musicalGenreId}")
    MusicalGenre findById(@PathVariable UUID musicalGenreId, @RequestHeader String authorization);

}

package com.teamwave.musicalgenresservice.service.impl;

import com.teamwave.common.dto.kafka.MusicalGenreUpdated;
import com.teamwave.musicalgenresservice.config.KafkaConfig;
import com.teamwave.musicalgenresservice.dto.MusicalGenreDTO;
import com.teamwave.musicalgenresservice.model.MusicalGenre;
import com.teamwave.musicalgenresservice.repository.MusicalGenreRepository;
import com.teamwave.musicalgenresservice.service.MusicalGenreService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MusicalGenreServiceImpl extends AbstractServiceImpl<MusicalGenre, UUID, MusicalGenreRepository> implements MusicalGenreService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MusicalGenreServiceImpl(MusicalGenreRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        super(repository);
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public MusicalGenre create(MusicalGenreDTO musicalGenreDTO) {
        return super.save(MusicalGenre.builder()
        .name(musicalGenreDTO.name())
        .build());
    }

    @Override
    public List<MusicalGenre> create(List<MusicalGenreDTO> musicalGenresDTO) {
        return super.save(musicalGenresDTO.stream()
        .map(musicalGenreDTO -> MusicalGenre.builder()
        .name(musicalGenreDTO.name())
        .build()).toList());
    }

    @Override
    public MusicalGenre update(UUID musicalGenreId, MusicalGenreDTO musicalGenreDTO) {
        MusicalGenre musicalGenre = super.save(super.findById(musicalGenreId).toBuilder()
        .name(musicalGenreDTO.name())
        .build());

        kafkaTemplate.send(KafkaConfig.TOPIC_MUSICAL_GENRE_UPDATED, new MusicalGenreUpdated(
                musicalGenre.getId(),
                musicalGenreDTO.name()
        ));

        return musicalGenre;
    }

}

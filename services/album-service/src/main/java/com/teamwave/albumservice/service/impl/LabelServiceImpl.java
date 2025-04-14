package com.teamwave.albumservice.service.impl;

import com.teamwave.albumservice.dto.LabelDTO;
import com.teamwave.albumservice.model.Label;
import com.teamwave.albumservice.repository.LabelRepository;
import com.teamwave.albumservice.service.AlbumService;
import com.teamwave.albumservice.service.LabelService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LabelServiceImpl extends AbstractServiceImpl<Label, UUID, LabelRepository> implements LabelService {
    private final AlbumService albumService;

    public LabelServiceImpl(LabelRepository repository, AlbumService albumService) {
        super(repository);
        this.albumService = albumService;
    }

    @Override
    public Label create(UUID albumId, LabelDTO labelDTO) {
        return super.save(Label.builder()
        .name(labelDTO.name())
        .album(albumService.findById(albumId))
        .build());
    }

    @Override
    public Label update(UUID labelId, LabelDTO labelDTO) {
        return super.save(super.findById(labelId).toBuilder()
        .name(labelDTO.name())
        .build());
    }
}

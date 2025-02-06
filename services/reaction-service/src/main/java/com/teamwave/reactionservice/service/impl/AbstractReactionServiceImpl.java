package com.teamwave.reactionservice.service.impl;

import com.teamwave.reactionservice.dto.ReactionDTO;
import com.teamwave.reactionservice.exception.DataNotFoundException;
import com.teamwave.reactionservice.model.Reaction;
import com.teamwave.reactionservice.service.AbstractReactionService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractReactionServiceImpl<T extends Reaction, R extends JpaRepository<T, UUID>> implements AbstractReactionService<T> {
    private final R repository;

    @Override
    public T findById(UUID id) throws DataNotFoundException {
        return repository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("Reaction not found: " + id));
    }

    @Override
    public List<T> findAll(Example<T> example, String orderBy, Sort.Direction direction) {
        return repository.findAll(example, Sort.by(direction, orderBy));
    }

    @Override
    public Page<T> findAll(int page, int size, Example<T> example, String orderBy, Sort.Direction direction) {
        return repository.findAll(example, PageRequest.of(page, size, Sort.by(direction, orderBy)));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(UUID reactId, ReactionDTO reactionDTO) {
        T entity = findById(reactId);
        entity.setReactionType(reactionDTO.reactionType());
        return save(entity);
    }

    @Override
    public void delete(UUID reactId) {

    }
}

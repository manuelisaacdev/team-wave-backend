package com.teamwave.reactionservice.service;

import com.teamwave.reactionservice.dto.ReactionDTO;
import com.teamwave.reactionservice.exception.DataNotFoundException;
import com.teamwave.reactionservice.model.Reaction;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.UUID;

public interface AbstractReactionService<T extends Reaction> {
    T findById(UUID id) throws DataNotFoundException;
    List<T> findAll(Example<T> example, String orderBy, Direction direction);
    Page<T> findAll(int page, int size, Example<T> example, String orderBy, Direction direction);
    T save(T entity);
    T update(UUID reactId, ReactionDTO reactionDTO);
    void delete(UUID reactId);
}

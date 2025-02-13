package com.teamwave.userservice.service.impl;

import com.teamwave.userservice.exception.DataNotFoundException;
import com.teamwave.userservice.service.AbstractService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractServiceImpl<T, ID, R extends JpaRepository<T, ID>> implements AbstractService<T, ID> {
    private final R repository;

    @Override
    public T findById(ID id) throws DataNotFoundException {
        return repository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("Entity not found"));
    }

    @Override
    public List<T> findAll(String orderBy, Sort.Direction direction) {
        return repository.findAll(Sort.by(direction, orderBy));
    }

    @Override
    public Page<T> findAll(int page, int size, Example<T> example, String orderBy, Sort.Direction direction) {
        return repository.findAll(example, PageRequest.of(page, size, direction, orderBy));
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

}

package com.teamwave.musicalgenresservice.service.impl;

import com.teamwave.musicalgenresservice.exception.DataNotFoundException;
import com.teamwave.musicalgenresservice.service.AbstractService;
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
    public List<T> findAllById(List<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public List<T> findAll(String orderBy, Sort.Direction direction) {
        return repository.findAll(Sort.by(direction, orderBy));
    }

    @Override
    public List<T> findAll(Example<T> example, String orderBy, Sort.Direction direction) {
        return repository.findAll(example, Sort.by(direction, orderBy));
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
    public List<T> save(List<T> entity) {
        return repository.saveAll(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

}

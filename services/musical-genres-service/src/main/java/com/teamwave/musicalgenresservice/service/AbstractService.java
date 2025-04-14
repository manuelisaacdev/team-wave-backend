package com.teamwave.musicalgenresservice.service;

import com.teamwave.musicalgenresservice.exception.DataNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

public interface AbstractService<T, ID> {
    List<T> findAllById(List<ID> ids);
    T findById(ID id) throws DataNotFoundException;
    List<T> findAll(String orderBy, Direction direction);
    List<T> findAll(Example<T> example, String orderBy, Direction direction);
    Page<T> findAll(int page, int size, Example<T> example, String orderBy, Direction direction);
    T save(T entity);
    List<T> save(List<T> entity);
    void deleteById(ID id);
}

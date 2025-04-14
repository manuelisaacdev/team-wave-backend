package com.teamwave.musicservice.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFountException extends EntityNotFoundException {
    public DataNotFountException(String message) {
        super(message);
    }
}

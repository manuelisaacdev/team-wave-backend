package com.teamwave.fileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StorageEmptyFileException extends StorageException {
    public StorageEmptyFileException(String message) {
        super(message);
    }
}

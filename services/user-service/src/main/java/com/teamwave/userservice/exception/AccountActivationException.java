package com.teamwave.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AccountActivationException extends RuntimeException {
    public AccountActivationException(String message) {
        super(message);
    }
}

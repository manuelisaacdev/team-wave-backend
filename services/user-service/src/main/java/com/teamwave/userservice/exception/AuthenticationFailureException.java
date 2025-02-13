package com.teamwave.userservice.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFailureException extends AuthenticationException {
    public AuthenticationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.github.manerajona.reactive.exception;

import com.github.manerajona.reactive.exception.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public class ErrorDetailsException extends ResponseStatusException {

    private final List<ErrorDetails> errors;

    public ErrorDetailsException(HttpStatus status, List<ErrorDetails> errors) {
        super(status);
        this.errors = errors;
    }

    public List<ErrorDetails> errors() {
        return new ArrayList<>(errors);
    }
}

package com.github.manerajona.reactive.common.exception;

import com.github.manerajona.reactive.common.exception.error.ErrorDetails;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Getter
public class ErrorDetailsException extends ResponseStatusException {

    private final List<ErrorDetails> errors;

    public ErrorDetailsException(HttpStatus status, List<ErrorDetails> errors) {
        super(status);
        this.errors = errors;
    }

    public ErrorDetailsException(HttpStatus status, ErrorDetails error) {
        super(status);
        this.errors = List.of(error);
    }
}

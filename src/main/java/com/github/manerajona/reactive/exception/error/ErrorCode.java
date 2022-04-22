package com.github.manerajona.reactive.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_FIELD_VALUE("The provided field is not valid."),
    RESOURCE_NOT_FOUND("The requested resource was not found."),
    HTTP_CLIENT_ERROR("The request failed because a 4xx error was received."),
    SERVICE_UNAVAILABLE("The server is unavailable to handle this request right now. Please try again later."),
    INTERNAL_ERROR("There was an error on the server and the request could not be completed."),
    BAD_REQUEST("The server cannot return a response due to an error on the client’s end.");

    private final String defaultMessage;

}

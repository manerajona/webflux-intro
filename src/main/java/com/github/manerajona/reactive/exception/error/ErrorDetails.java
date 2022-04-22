package com.github.manerajona.reactive.exception.error;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
@JsonPropertyOrder({"code", "detail", "field", "value", "location"})
public class ErrorDetails {
    @NotNull ErrorCode code;
    @NotNull String detail;
    String field;
    Object value;
    ErrorLocation location;
}

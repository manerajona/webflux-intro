package com.github.manerajona.reactive.ports.output.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwapiSingleResponse {
    @JsonProperty("message")
    private String message;
    @JsonProperty("result")
    private ResultDto result;
}

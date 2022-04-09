package com.github.manerajona.reactive.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class JediResponse {
    private UUID id;
    private String name;
    private String gender;
    @JsonProperty("birth_year")
    private String birthYear;
    private String planet;
    private String url;
}

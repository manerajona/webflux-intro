package com.github.manerajona.reactive.ports.output.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    @JsonProperty("properties")
    private PlanetDto properties;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("_id")
    private String id;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("__v")
    private Integer v;
    @JsonProperty("url")
    private String url;
}

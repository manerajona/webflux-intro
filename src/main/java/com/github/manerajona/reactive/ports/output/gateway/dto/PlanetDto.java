package com.github.manerajona.reactive.ports.output.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetDto {
    @JsonProperty("diameter")
    private String diameter;
    @JsonProperty("rotation_period")
    private String rotationPeriod;
    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    @JsonProperty("gravity")
    private String gravity;
    @JsonProperty("population")
    private String population;
    @JsonProperty("climate")
    private String climate;
    @JsonProperty("terrain")
    private String terrain;
    @JsonProperty("surface_water")
    private String surfaceWater;
    @JsonProperty("created")
    private String created;
    @JsonProperty("edited")
    private String edited;
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
}

package com.github.manerajona.reactive.ports.input.rs.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PlanetResponse {
    private String id;
    private String name;
    private Long population;
    private String climate;
    private String terrain;
    private String gravity;
    private Integer diameter;
    @JsonProperty("surface_water")
    private Integer surfaceWater;
    @JsonProperty("orbital_period")
    private Integer orbitalPeriod;
    @JsonProperty("rotation_period")
    private Integer rotationPeriod;
    private String url;
}

package com.github.manerajona.reactive.ports.input.rs.response;

public record PlanetResponse (
        String id,
        String name,
        Long population,
        String climate,
        String terrain,
        String gravity,
        Integer diameter,
        Integer surfaceWater,
        Integer orbitalPeriod,
        Integer rotationPeriod,
        String url
) { }

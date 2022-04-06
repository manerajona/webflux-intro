package com.github.manerajona.reactive.domain.model;

public record Planet(
        String name,
        Long population,
        String climate,
        String terrain,
        String gravity,
        Integer diameter,
        Integer surfaceWater,
        Integer orbitalPeriod,
        Integer rotationPeriod
) { }

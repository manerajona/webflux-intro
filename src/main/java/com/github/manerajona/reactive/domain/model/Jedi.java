package com.github.manerajona.reactive.domain.model;

import java.util.UUID;

public record Jedi (
        UUID id,
        String name,
        String gender,
        String birthYear,
        String planet,
        String url
) { }

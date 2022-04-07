package com.github.manerajona.reactive.ports.input.rs.response;

import java.util.UUID;

public record JediResponse(
        UUID id,
        String name,
        String gender,
        String birthYear,
        String planet,
        String url
) { }

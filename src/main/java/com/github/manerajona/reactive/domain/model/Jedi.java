package com.github.manerajona.reactive.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record Jedi (
        UUID id,
        String name,
        String gender,
        String planet,
        LocalDate birthday)
{}

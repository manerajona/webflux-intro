package com.github.manerajona.reactive.ports.input.rs.response;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

public record JediResponse(
        UUID id,
        String name,
        String gender,
        String planet,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date birthday
) { }

package com.github.manerajona.reactive.ports.input.rs.request;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

public record UpdateJediRequest(
        @Pattern(regexp = "[a-zA-Z\\s]+", message = "The name field must contain only text without numbers")
        String gender,
        String planet,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Date birthday
) { }

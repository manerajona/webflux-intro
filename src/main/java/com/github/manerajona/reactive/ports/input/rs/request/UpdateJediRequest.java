package com.github.manerajona.reactive.ports.input.rs.request;

import javax.validation.constraints.Pattern;

public record UpdateJediRequest(
        @Pattern(regexp = "[a-zA-Z\\s]+", message = "The name field must contain only text without numbers")
        String gender,
        String planet
) { }

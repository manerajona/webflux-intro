package com.github.manerajona.reactive.ports.input.rs.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record CreateJediRequest(
        @NotBlank
        @Pattern(regexp = "[a-zA-Z\\s\\-]+", message = "The name field must contain only text without numbers")
        String name,
        @NotBlank
        @Pattern(regexp = "[a-zA-Z\\s]+", message = "The name field must contain only text without numbers")
        String gender,
        @NotBlank
        String birthYear,
        @NotBlank
        String planet,
        @NotBlank
        String url
) { }

package com.github.manerajona.reactive.ports.input.rs.request;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public record CreateJediRequest(
        @NotBlank
        @Pattern(regexp = "[a-zA-Z\\s]+", message = "The name field must contain only text without numbers")
        String name,
        @NotBlank
        @Pattern(regexp = "[a-zA-Z\\s]+", message = "The name field must contain only text without numbers")
        String gender,
        @NotBlank
        String planet,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        Date birthday
) { }

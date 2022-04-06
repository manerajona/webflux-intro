package com.github.manerajona.reactive.ports.output.r2dbc.repository;

import com.github.manerajona.reactive.ports.output.r2dbc.entity.JediH2;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface JediR2dbcRepository extends ReactiveCrudRepository<JediH2, UUID> {
}

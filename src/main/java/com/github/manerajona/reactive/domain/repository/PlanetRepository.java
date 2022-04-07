package com.github.manerajona.reactive.domain.repository;

import com.github.manerajona.reactive.domain.model.Planet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetRepository {
    Mono<Planet> findOne(Long id);
    Flux<Planet> findAll();
}

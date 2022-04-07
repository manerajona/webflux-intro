package com.github.manerajona.reactive.domain.usecase;

import com.github.manerajona.reactive.domain.model.Planet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetService {
    Mono<Planet> getByIdIfExists(Long id);
    Flux<Planet> getList();
}

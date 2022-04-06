package com.github.manerajona.reactive.domain.repository;

import com.github.manerajona.reactive.domain.model.Jedi;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JediRepository {
    Mono<UUID> create(Jedi jedi);
    Mono<Jedi> findOne(UUID id);
    Mono<Jedi> update(UUID id, Jedi jedi);
    Flux<Jedi> findAll();
    Mono<Void> delete(UUID id);
}

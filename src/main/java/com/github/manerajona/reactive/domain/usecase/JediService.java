package com.github.manerajona.reactive.domain.usecase;

import com.github.manerajona.reactive.domain.model.Jedi;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JediService {
    Mono<UUID> createEntity(Jedi entity);
    Mono<Void> deleteById(UUID id);
    Mono<Jedi> getByIdIfExists(UUID id);
    Flux<Jedi> getList();
    Mono<Jedi> updateEntityIfExists(UUID id, Jedi entity);
}

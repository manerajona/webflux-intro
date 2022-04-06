package com.github.manerajona.reactive.domain.usecase.impl;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.domain.repository.JediRepository;
import com.github.manerajona.reactive.domain.usecase.JediService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JediServiceImpl implements JediService {

    private final JediRepository repository;

    @Override
    public Mono<UUID> createEntity(Jedi entity) {
        return repository.create(entity);
    }

    @Override
    public Mono<Jedi> getByIdIfExists(UUID id) {
        return repository.findOne(id);
    }

    @Override
    public Flux<Jedi> getList() {
        return repository.findAll();
    }

    @Override
    public Mono<Jedi> updateEntityIfExists(UUID id, Jedi entity) {
        return repository.update(id, entity);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return repository.delete(id);
    }

}

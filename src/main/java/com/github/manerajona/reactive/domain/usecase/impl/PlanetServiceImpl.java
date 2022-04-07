package com.github.manerajona.reactive.domain.usecase.impl;

import com.github.manerajona.reactive.domain.model.Planet;
import com.github.manerajona.reactive.domain.repository.PlanetRepository;
import com.github.manerajona.reactive.domain.usecase.PlanetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository repository;

    @Override
    public Mono<Planet> getByIdIfExists(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Flux<Planet> getList() {
        return repository.findAll();
    }
}

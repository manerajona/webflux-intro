package com.github.manerajona.reactive.ports.output.r2dbc.dao;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.domain.repository.JediRepository;
import com.github.manerajona.reactive.ports.output.r2dbc.entity.JediH2;
import com.github.manerajona.reactive.ports.output.r2dbc.mapper.JediH2Mapper;
import com.github.manerajona.reactive.ports.output.r2dbc.repository.JediR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Profile("h2")
@Component
@RequiredArgsConstructor
public class JediH2Dao implements JediRepository {

    private final JediR2dbcRepository repository;
    private final JediH2Mapper mapper;

    @Override
    @Transactional
    public Mono<UUID> create(Jedi jedi) {
        JediH2 jediH2 = mapper.jediToJediH2(jedi);
        return repository.save(jediH2).map(JediH2::getId);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Jedi> findOne(UUID id) {
        return repository.findById(id).map(mapper::jediH2ToJedi);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Jedi> findAll() {
        return repository.findAll().map(mapper::jediH2ToJedi);
    }

    @Override
    @Transactional
    public Mono<Jedi> update(UUID id, Jedi jedi) {
        return repository.findById(id)
                .defaultIfEmpty(new JediH2())
                .map(jediH2 -> {
                    Optional.ofNullable(jedi.gender()).ifPresent(jediH2::setGender);
                    Optional.ofNullable(jedi.birthYear()).ifPresent(jediH2::setBirthYear);
                    Optional.ofNullable(jedi.planet()).ifPresent(jediH2::setPlanet);
                    Optional.ofNullable(jedi.url()).ifPresent(jediH2::setUrl);
                    return jediH2;
                }).flatMap(updated -> Optional.ofNullable(updated.getId())
                        .map(i -> repository.save(updated))
                        .orElse(Mono.just(updated)))
                .map(mapper::jediH2ToJedi);
    }

    @Override
    @Transactional
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }
}

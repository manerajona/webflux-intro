package com.github.manerajona.reactive.ports.output.mongo.dao;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.domain.repository.JediRepository;
import com.github.manerajona.reactive.ports.output.mongo.document.JediMongo;
import com.github.manerajona.reactive.ports.output.mongo.mapper.JediMongoMapper;
import com.github.manerajona.reactive.ports.output.mongo.repository.JediMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Profile("!h2")
@Component
@RequiredArgsConstructor
public class JediMongoDao implements JediRepository {

    private final JediMongoRepository repository;
    private final JediMongoMapper mapper;

    @Override
    public Mono<UUID> create(Jedi jedi) {
        JediMongo document = mapper.jediToJediMongo(jedi);
        return repository.save(document).map(JediMongo::getId);
    }

    @Override
    public Mono<Jedi> findOne(UUID id) {
        return repository.findById(id).map(mapper::jediMongoToJedi);
    }

    @Override
    public Flux<Jedi> findAll() {
        return repository.findAll().map(mapper::jediMongoToJedi);
    }

    @Override
    public Mono<Jedi> update(UUID id, Jedi jedi) {
        return repository.findById(id)
                .defaultIfEmpty(new JediMongo())
                .map(jediMongo -> {
                    Optional.ofNullable(jedi.gender()).ifPresent(jediMongo::setGender);
                    Optional.ofNullable(jedi.birthYear()).ifPresent(jediMongo::setBirthYear);
                    Optional.ofNullable(jedi.planet()).ifPresent(jediMongo::setPlanet);
                    Optional.ofNullable(jedi.url()).ifPresent(jediMongo::setUrl);
                    return jediMongo;
                }).flatMap(updated -> Optional.ofNullable(updated.getId())
                        .map(i -> repository.save(updated))
                        .orElse(Mono.just(updated)))
                .map(mapper::jediMongoToJedi);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }
}

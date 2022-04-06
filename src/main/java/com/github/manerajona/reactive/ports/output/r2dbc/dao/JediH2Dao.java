package com.github.manerajona.reactive.ports.output.r2dbc.dao;

import lombok.RequiredArgsConstructor;

//@Primary
//@Component
@RequiredArgsConstructor
public class JediH2Dao //implements jediRepository
{

    /*
    private final JediR2dbcRepository repository;
    private final JediR2dbcMapper mapper;

    @Override
    @Transactional
    public Mono<Long> create(Jedi jedi) {
        JediR2dbc jpa = mapper.jediToJediR2dbc(jedi);
        return repository.save(jpa).map(JediR2dbc::getId);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Jedi> findOne(Long id) {
        return repository.findById(id).map(mapper::jediR2dbcToJedi);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<JediList> findAllByPageable(Pageable pageable) {
        // todo paginated list
        return Mono.empty();
    }

    @Override
    @Transactional
    public Mono<Jedi> update(Long id, Jedi jedi) {
        return repository.findById(id)
                .defaultIfEmpty(new JediR2dbc())
                .map(jediR2dbc -> {
                    jediR2dbc.setBirthday(jedi.getBirthday());
                    return jediR2dbc;
                }).flatMap(updated -> Optional.ofNullable(updated.getId())
                        .map(i -> repository.save(updated))
                        .orElse(Mono.just(updated)))
                .map(mapper::jediR2dbcToJedi);
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }*/
}

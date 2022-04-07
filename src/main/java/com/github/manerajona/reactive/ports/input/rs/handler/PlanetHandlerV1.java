package com.github.manerajona.reactive.ports.input.rs.handler;

import com.github.manerajona.reactive.domain.model.Planet;
import com.github.manerajona.reactive.domain.usecase.PlanetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlanetHandlerV1 {

    public static final String PLANET_URI = "/v1/planets";
    public static final String PLANET_ID_URI = PLANET_URI + "/{id}";

    private final PlanetService service;

    public Mono<ServerResponse> getPlanet(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return service.getByIdIfExists(id)
                .flatMap(planet -> ServerResponse.ok().bodyValue(planet))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnError(error -> log.error(error.getMessage(), error));
    }

    public Mono<ServerResponse> getPlanets(ServerRequest ignored) {
        return ServerResponse.ok().body(service.getList(), Planet.class)
                .doOnError(error -> log.error(error.getMessage(), error));
    }

}

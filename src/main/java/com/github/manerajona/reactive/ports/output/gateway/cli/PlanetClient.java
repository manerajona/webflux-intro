package com.github.manerajona.reactive.ports.output.gateway.cli;

import com.github.manerajona.reactive.domain.model.Planet;
import com.github.manerajona.reactive.domain.repository.PlanetRepository;
import com.github.manerajona.reactive.ports.output.gateway.dto.SwapiListResponse;
import com.github.manerajona.reactive.ports.output.gateway.dto.SwapiSingleResponse;
import com.github.manerajona.reactive.ports.output.gateway.mapper.PlanetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlanetClient implements PlanetRepository {

    private final WebClient webClient;
    private final PlanetMapper mapper;

    @Value("${swapi.dev.planets.uri}")
    private String uri;

    @Override
    public Mono<Planet> findOne(Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(uri + "/{id}").build(id))
                .retrieve()
                .bodyToMono(SwapiSingleResponse.class)
                .map(response -> mapper.planetDtoToPlanet(response.getResult().getProperties()));
    }

    @Override
    public Flux<Planet> findAll() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(uri).build())
                .retrieve()
                .bodyToMono(SwapiListResponse.class)
                .flatMapMany(response -> {
                    List<Planet> planets = mapper.resultDtoListToPlanetList(response.getResults());
                    return Flux.fromStream(planets.stream());
                });
    }
}

package com.github.manerajona.reactive.ports.output.gateway.cli;

import com.github.manerajona.reactive.common.exception.ErrorDetailsException;
import com.github.manerajona.reactive.common.exception.error.ApplicationErrorCode;
import com.github.manerajona.reactive.common.exception.error.ErrorDetails;
import com.github.manerajona.reactive.domain.model.Planet;
import com.github.manerajona.reactive.domain.repository.PlanetRepository;
import com.github.manerajona.reactive.ports.output.gateway.dto.PlanetDto;
import com.github.manerajona.reactive.ports.output.gateway.dto.SwapiListResponse;
import com.github.manerajona.reactive.ports.output.gateway.dto.SwapiSingleResponse;
import com.github.manerajona.reactive.ports.output.gateway.mapper.PlanetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new ErrorDetailsException(error.statusCode(),
                                ErrorDetails.builder()
                                        .code(ApplicationErrorCode.HTTP_CLIENT_ERROR)
                                        .detail(ApplicationErrorCode.HTTP_CLIENT_ERROR.getDefaultMessage())
                                        .build())))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new ErrorDetailsException(error.statusCode(),
                                ErrorDetails.builder()
                                        .code(ApplicationErrorCode.SERVICE_UNAVAILABLE)
                                        .detail(ApplicationErrorCode.SERVICE_UNAVAILABLE.getDefaultMessage())
                                        .build())))
                .bodyToMono(SwapiSingleResponse.class)
                .map(response -> {
                    PlanetDto planetDto = response.getResult().getProperties();
                    if ("unknown".equals(planetDto.getName())) {
                        throw new ErrorDetailsException(HttpStatus.NOT_FOUND,
                                ErrorDetails.builder()
                                        .code(ApplicationErrorCode.RESOURCE_NOT_FOUND)
                                        .detail("Planet not found")
                                        .build());
                    }
                    return mapper.planetDtoToPlanet(planetDto);
                });
    }

    @Override
    public Flux<Planet> findAll() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(uri).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new ErrorDetailsException(error.statusCode(),
                                ErrorDetails.builder()
                                        .code(ApplicationErrorCode.HTTP_CLIENT_ERROR)
                                        .detail(ApplicationErrorCode.HTTP_CLIENT_ERROR.getDefaultMessage())
                                        .build())))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new ErrorDetailsException(error.statusCode(),
                                ErrorDetails.builder()
                                        .code(ApplicationErrorCode.SERVICE_UNAVAILABLE)
                                        .detail(ApplicationErrorCode.SERVICE_UNAVAILABLE.getDefaultMessage())
                                        .build())))
                .bodyToMono(SwapiListResponse.class)
                .flatMapMany(response -> {
                    List<Planet> planets = Optional.ofNullable(response.getResults())
                            .map(mapper::resultDtoListToPlanetList)
                            .orElse(new ArrayList<>());

                    return Flux.fromStream(planets.stream());
                });
    }
}

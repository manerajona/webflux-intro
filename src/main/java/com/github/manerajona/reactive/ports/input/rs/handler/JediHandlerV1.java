package com.github.manerajona.reactive.ports.input.rs.handler;

import com.github.manerajona.reactive.common.exception.ErrorDetailsException;
import com.github.manerajona.reactive.common.exception.error.ApplicationErrorCode;
import com.github.manerajona.reactive.common.exception.error.ErrorDetails;
import com.github.manerajona.reactive.common.exception.error.ErrorLocation;
import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.domain.usecase.JediService;
import com.github.manerajona.reactive.ports.input.rs.mapper.JediControllerMapper;
import com.github.manerajona.reactive.ports.input.rs.request.CreateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.request.UpdateJediRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JediHandlerV1 {

    public static final String JEDIS_URI = "/v1/jedis";
    public static final String JEDIS_ID_URI = JEDIS_URI + "/{id}";

    private final JediService service;
    private final JediControllerMapper mapper;
    private final Validator validator;

    public Mono<ServerResponse> createJedis(ServerRequest request) {

        Mono<Jedi> mono = request.bodyToMono(CreateJediRequest.class)
                .doOnNext(this::validate)
                .map(mapper::createJediRequestToJedi);

        return mono.flatMap(service::createEntity)
                .flatMap(id -> {
                    URI location = UriComponentsBuilder.fromPath(request.path() + "/{id}").build().expand(id).toUri();
                    return ServerResponse.created(location).build();
                }).doOnError(error -> log.error(error.getMessage(), error));
    }

    public Mono<ServerResponse> getJedi(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id").trim());

        return service.getByIdIfExists(id)
                .flatMap(jedi -> ServerResponse.ok().bodyValue(jedi))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnError(error -> log.error(error.getMessage(), error));
    }

    public Mono<ServerResponse> getJedis(ServerRequest ignored) {
        return ServerResponse.ok().body(service.getList(), Jedi.class);
    }

    public Mono<ServerResponse> updateJedi(ServerRequest request) {
        return request.bodyToMono(UpdateJediRequest.class)
                .doOnNext(this::validate)
                .flatMap(jediRequest -> {
                    Jedi jedi = mapper.updateJediRequestToJedi(jediRequest);
                    UUID id = UUID.fromString(request.pathVariable("id").trim());
                    return service.updateEntityIfExists(id, jedi);
                })
                .flatMap(jedi -> Objects.nonNull(jedi.id()) ?
                        ServerResponse.noContent().build() :
                        ServerResponse.notFound().build()
                ).doOnError(error -> log.error(error.getMessage(), error));
    }

    public Mono<ServerResponse> deleteJedi(ServerRequest request) {
        UUID id = UUID.fromString(request.pathVariable("id").trim());
        return service.deleteById(id)
                .flatMap(voidMono -> ServerResponse.noContent().build())
                .doOnError(error -> log.error(error.getMessage(), error));
    }

    private <T> void validate(T dto) {
        BindingResult result = new BeanPropertyBindingResult(dto, dto.getClass().getName());
        validator.validate(dto, result);

        if(result.hasErrors()){
            List<ErrorDetails> errors = result.getFieldErrors().stream()
                    .map(fieldError -> ErrorDetails.builder()
                            .code(ApplicationErrorCode.INVALID_FIELD_VALUE)
                            .detail(fieldError.getDefaultMessage())
                            .field(fieldError.getField())
                            .value(fieldError.getRejectedValue())
                            .location(ErrorLocation.BODY)
                            .build()
                    ).collect(Collectors.toList());

            throw new ErrorDetailsException(HttpStatus.BAD_REQUEST, errors);
        }
    }

}

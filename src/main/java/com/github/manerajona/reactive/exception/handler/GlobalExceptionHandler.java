package com.github.manerajona.reactive.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.manerajona.reactive.exception.ErrorDetailsException;
import com.github.manerajona.reactive.exception.error.ApplicationErrorCode;
import com.github.manerajona.reactive.exception.error.ErrorDetails;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Order(-2)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper mapper;

    private static final List<ErrorDetails> INTERNAL_ERROR_DETAILS = List.of(ErrorDetails.builder()
            .code(ApplicationErrorCode.INTERNAL_ERROR)
            .detail(ApplicationErrorCode.INTERNAL_ERROR.getDefaultMessage())
            .build());

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        Pair<HttpStatus, List<ErrorDetails>> pair;
        if (throwable instanceof ErrorDetailsException ex) {
            pair = Pair.of(ex.getStatus(), ex.errors());
        } else {
            pair = Pair.of(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_DETAILS);
        }
        serverWebExchange.getResponse().setStatusCode(pair.getFirst());
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse().writeWith(getBody(serverWebExchange, pair.getSecond()));
    }

    @SneakyThrows
    private Mono<DataBuffer> getBody(ServerWebExchange serverWebExchange, List<ErrorDetails> errors) {
        DataBuffer dataBuffer = serverWebExchange.getResponse()
                .bufferFactory()
                .wrap(mapper.writeValueAsBytes(errors));

        return Mono.just(dataBuffer);
    }
    
}

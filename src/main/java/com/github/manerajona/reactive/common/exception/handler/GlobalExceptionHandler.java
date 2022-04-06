package com.github.manerajona.reactive.common.exception.handler;

import com.github.manerajona.reactive.common.exception.ErrorDetailsException;
import com.github.manerajona.reactive.common.exception.error.ApplicationErrorCode;
import com.github.manerajona.reactive.common.exception.error.ErrorDetails;
import com.github.manerajona.reactive.common.util.JsonUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final byte[] INTERNAL_ERROR_JSON = """
            [{
              "code": "INTERNAL_ERROR",
              "detail": "There was an error on the server and the request could not be completed."
            }]""".getBytes(StandardCharsets.UTF_8);

    private static final List<ErrorDetails> INTERNAL_ERROR_DETAILS = List.of(ErrorDetails.builder()
            .code(ApplicationErrorCode.INTERNAL_ERROR)
            .detail(ApplicationErrorCode.INTERNAL_ERROR.getDefaultMessage())
            .build());

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        Pair<HttpStatus, List<ErrorDetails>> pair;
        if (throwable instanceof ErrorDetailsException ex) {
            pair = Pair.of(HttpStatus.NOT_FOUND, ex.getErrors());
        } else {
            pair = Pair.of(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR_DETAILS);
        }
        serverWebExchange.getResponse().setStatusCode(pair.getFirst());
        serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return serverWebExchange.getResponse().writeWith(getBody(serverWebExchange, pair.getSecond()));
    }

    private Mono<DataBuffer> getBody(ServerWebExchange serverWebExchange, List<ErrorDetails> errors) {
        final DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        return Mono.just(getDataBuffer(bufferFactory, errors));
    }

    private DataBuffer getDataBuffer(DataBufferFactory bufferFactory, List<ErrorDetails> errors) {
        DataBuffer dataBuffer;
        try {
            dataBuffer = bufferFactory.wrap(JsonUtils.objectToBytes(errors));
        } catch (IOException e) {
            dataBuffer = bufferFactory.wrap(INTERNAL_ERROR_JSON);
        }
        return dataBuffer;
    }
    
}

package com.github.manerajona.reactive.config;

import io.netty.handler.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
public class WebClientConfig {

    @Bean
    protected WebClient webClient(){
        return WebClient.builder()
                .clientConnector(getConnector())
                .build();
    }

    protected ReactorClientHttpConnector getConnector() {
        return new ReactorClientHttpConnector(HttpClient.create()
                .wiretap("reactor.netty.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL));
    }
} 

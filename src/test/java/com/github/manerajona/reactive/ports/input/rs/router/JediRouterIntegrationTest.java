package com.github.manerajona.reactive.ports.input.rs.router;

import com.github.manerajona.reactive.ports.input.rs.handler.JediHandlerV1;
import com.github.manerajona.reactive.ports.input.rs.request.CreateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.request.UpdateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.response.JediResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JediRouterIntegrationTest {

    WebClient webClient;

    @BeforeEach
    void setUp() {
        webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }

    @Order(1)
    @Test
    void jediRoutes_shouldCreateNewJedi_success() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        webClient.post().uri(JediHandlerV1.JEDIS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CREATE_JEDI_REQUEST))
                .retrieve()
                .toBodilessEntity()
                .publishOn(Schedulers.parallel())
                .subscribe(responseEntity -> {
                    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

                    RESOURCE_LOCATION = Objects.requireNonNull(responseEntity.getHeaders().get(HttpHeaders.LOCATION))
                            .stream().findAny().orElse("");

                    assertThat(RESOURCE_LOCATION).containsOnlyOnce(JediHandlerV1.JEDIS_URI);

                    countDownLatch.countDown();
                });

        countDownLatch.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Order(2)
    @Test
    void jediRoutes_shouldGetExistingJedi_success() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        webClient.get().uri(RESOURCE_LOCATION)
                .retrieve()
                .bodyToMono(JediResponse.class)
                .publishOn(Schedulers.single())
                .subscribe(response -> {
                    assertThat(response.getName()).isEqualTo(CREATE_JEDI_REQUEST.name());
                    assertThat(response.getGender()).isEqualTo(CREATE_JEDI_REQUEST.gender());
                    assertThat(response.getBirthYear()).isEqualTo(CREATE_JEDI_REQUEST.birthYear());
                    assertThat(response.getPlanet()).isEqualTo(CREATE_JEDI_REQUEST.planet());
                    assertThat(response.getUrl()).isEqualTo(CREATE_JEDI_REQUEST.url());

                    countDownLatch.countDown();
                });

        countDownLatch.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Order(3)
    @Test
    void updateJedi_shouldUpdateExistingJedi_success() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        webClient.patch().uri(RESOURCE_LOCATION)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(UPDATE_JEDI_REQUEST))
                .retrieve()
                .toBodilessEntity()
                .publishOn(Schedulers.single())
                .subscribe(responseEntity -> {
                    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

                    countDownLatch.countDown();
                });

        countDownLatch.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Order(4)
    @Test
    void getJedis_shouldGetListOfJedis_success() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        webClient.get().uri(JediHandlerV1.JEDIS_URI)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(JediResponse.class)
                .publishOn(Schedulers.parallel())
                .subscribe(response -> {
                    assertThat(response.getName()).isEqualTo(CREATE_JEDI_REQUEST.name());

                    assertThat(response.getGender()).isEqualTo(UPDATE_JEDI_REQUEST.gender());
                    assertThat(response.getBirthYear()).isEqualTo(UPDATE_JEDI_REQUEST.birthYear());
                    assertThat(response.getPlanet()).isEqualTo(UPDATE_JEDI_REQUEST.planet());
                    assertThat(response.getUrl()).isEqualTo(UPDATE_JEDI_REQUEST.url());

                    countDownLatch.countDown();
                });

        countDownLatch.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Order(5)
    @Test
    void deleteJedi_shouldDeleteJedi_success() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        webClient.delete().uri(RESOURCE_LOCATION)
                .retrieve()
                .toBodilessEntity()
                .publishOn(Schedulers.single())
                .subscribe(responseEntity -> {
                    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

                    countDownLatch.countDown();
                });

        countDownLatch.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    @Order(6)
    @Test
    void jediRoutes_shouldGetExistingJedi_notFound() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        webClient.get().uri(RESOURCE_LOCATION)
                .retrieve()
                .bodyToMono(JediResponse.class)
                .subscribe(responseEntity -> {
                }, throwable -> countDownLatch.countDown());

        countDownLatch.await(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    /// Global

    private static final int TIMEOUT_MILLIS = 1000;

    private static final CreateJediRequest CREATE_JEDI_REQUEST = new CreateJediRequest(
            "Obi-Wan Kenobi",
            "male",
            "57BBY",
            "www.swapi.tech/api/planets/20",
            "www.swapi.tech/api/people/10");

    private static final UpdateJediRequest UPDATE_JEDI_REQUEST = new UpdateJediRequest(
            "female",
            "92BBY",
            "www.swapi.tech/api/planets/28",
            "www.swapi.tech/api/people/2");

    private static String RESOURCE_LOCATION;
}

package com.github.manerajona.reactive.ports.input.rs.router;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.ports.input.rs.handler.JediHandlerV1;
import com.github.manerajona.reactive.ports.input.rs.request.CreateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.request.UpdateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.response.JediResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebFluxTest(JediRouter.class)
class JediRouterUnitTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    JediHandlerV1 handler;

    @Test
    void jediRoutes_shouldCreateNewJedi_success() {
        final UUID id = UUID.randomUUID();
        final CreateJediRequest createRequest = new CreateJediRequest(
                "Obi-Wan Kenobi",
                "male",
                "57BBY",
                "www.swapi.tech/api/planets/20",
                "www.swapi.tech/api/people/10");

        final URI location = UriComponentsBuilder.fromPath(JediHandlerV1.JEDIS_ID_URI)
                .buildAndExpand(id.toString())
                .toUri();

        given(handler.createJedis(any())).willReturn(ServerResponse.created(location).build());

        webTestClient.post()
                .uri(JediHandlerV1.JEDIS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location(location.toString());
    }

    @Test
    void jediRoutes_shouldGetExistingJedi_success() {
        final UUID id = UUID.randomUUID();
        final Jedi jedi = new Jedi(id,
                "Luke Skywalker",
                "male",
                "19BBY",
                "www.swapi.tech/api/planets/1",
                "www.swapi.tech/api/people/1");

        given(handler.getJedi(any())).willReturn(ServerResponse.ok().bodyValue(jedi));

        webTestClient.get()
                .uri(JediHandlerV1.JEDIS_ID_URI, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JediResponse.class)
                .value(JediResponse::getId, equalTo(id))
                .value(JediResponse::getName, equalTo("Luke Skywalker"))
                .value(JediResponse::getGender, equalTo("male"))
                .value(JediResponse::getBirthYear, equalTo("19BBY"))
                .value(JediResponse::getPlanet, equalTo("www.swapi.tech/api/planets/1"))
                .value(JediResponse::getUrl, equalTo("www.swapi.tech/api/people/1"));
    }

    @Test
    void getJedis_shouldGetListOfJedis_success() {
        final UUID id = UUID.randomUUID();
        final Jedi jedi = new Jedi(id,
                "Luke Skywalker",
                "male",
                "19BBY",
                "www.swapi.tech/api/planets/1",
                "www.swapi.tech/api/people/1");

        List<Jedi> jedis = List.of(jedi, jedi, jedi);

        given(handler.getJedis(any())).willReturn(ServerResponse.ok().bodyValue(jedis));

        webTestClient.get()
                .uri(JediHandlerV1.JEDIS_URI)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(list -> (long) list.size(), equalTo(3L));
    }

    @Test
    void updateJedi_shouldUpdateExistingJedi_success() {
        final UUID id = UUID.randomUUID();
        final UpdateJediRequest updateRequest = new UpdateJediRequest(
                "non-binary",
                "92BBY",
                "www.swapi.tech/api/planets/28",
                "www.swapi.tech/api/people/2");

        given(handler.updateJedi(any())).willReturn(ServerResponse.noContent().build());

        webTestClient.patch()
                .uri(JediHandlerV1.JEDIS_ID_URI, id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updateRequest))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteJedi_shouldDeleteJedi_success() {
        final UUID id = UUID.randomUUID();

        given(handler.deleteJedi(any())).willReturn(ServerResponse.noContent().build());

        webTestClient.delete()
                .uri(JediHandlerV1.JEDIS_ID_URI, id)
                .exchange()
                .expectStatus().isNoContent();
    }
}

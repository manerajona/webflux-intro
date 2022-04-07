package com.github.manerajona.reactive.ports.input.rs.router;

import com.github.manerajona.reactive.domain.model.Planet;
import com.github.manerajona.reactive.ports.input.rs.handler.PlanetHandlerV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PlanetRouter {
    @Bean
    @RouterOperations({
            @RouterOperation(path = PlanetHandlerV1.PLANET_URI, method = RequestMethod.GET, operation = @Operation(operationId = "getPlanets",
                    responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Planet.class))))),
            @RouterOperation(path = PlanetHandlerV1.PLANET_ID_URI, method = RequestMethod.GET, operation = @Operation(operationId = "getPlanet",
                    parameters = @Parameter(name = "id", in = ParameterIn.PATH),
                    responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Planet.class)))))})
    public RouterFunction<ServerResponse> planetRoutes(PlanetHandlerV1 handler) {
        return route()
                .GET(PlanetHandlerV1.PLANET_URI, accept(APPLICATION_JSON), handler::getPlanets)
                .GET(PlanetHandlerV1.PLANET_ID_URI, accept(APPLICATION_JSON), handler::getPlanet)
                .build();
    }
}

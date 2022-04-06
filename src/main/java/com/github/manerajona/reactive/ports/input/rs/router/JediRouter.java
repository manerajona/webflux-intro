package com.github.manerajona.reactive.ports.input.rs.router;

import com.github.manerajona.reactive.ports.input.rs.handler.JediHandlerV1;
import com.github.manerajona.reactive.ports.input.rs.request.CreateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.request.UpdateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.response.JediResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
public class JediRouter {
    @Bean
    @RouterOperations({
            @RouterOperation(path = JediHandlerV1.JEDIS_URI, method = RequestMethod.POST, operation = @Operation(operationId = "createJedis",
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = CreateJediRequest.class))),
                    responses = @ApiResponse(responseCode = "201"))),
            @RouterOperation(path = JediHandlerV1.JEDIS_URI, method = RequestMethod.GET, operation = @Operation(operationId = "getJedis",
                    responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JediResponse.class))))),
            @RouterOperation(path = JediHandlerV1.JEDIS_ID_URI, method = RequestMethod.GET, operation = @Operation(operationId = "getJedi",
                    parameters = @Parameter(name = "id", in = ParameterIn.PATH),
                    responses = @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JediResponse.class))))),
            @RouterOperation(path = JediHandlerV1.JEDIS_ID_URI, method = RequestMethod.PATCH, operation = @Operation(operationId = "updateJedi",
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UpdateJediRequest.class))),
                    parameters = @Parameter(name = "id", in = ParameterIn.PATH),
                    responses = @ApiResponse(responseCode = "204"))),
            @RouterOperation(path = JediHandlerV1.JEDIS_ID_URI, method = RequestMethod.DELETE, operation = @Operation(operationId = "deleteJedi",
                    parameters = @Parameter(name = "id", in = ParameterIn.PATH),
                    responses = @ApiResponse(responseCode = "204")))})
    public RouterFunction<ServerResponse> jediRoutes(JediHandlerV1 handler) {
        return route()
                .POST(JediHandlerV1.JEDIS_URI, accept(APPLICATION_JSON), handler::createJedis)
                .GET(JediHandlerV1.JEDIS_URI, accept(APPLICATION_JSON), handler::getJedis)
                .GET(JediHandlerV1.JEDIS_ID_URI, accept(APPLICATION_JSON), handler::getJedi)
                .PATCH(JediHandlerV1.JEDIS_ID_URI, accept(APPLICATION_JSON), handler::updateJedi)
                .DELETE(JediHandlerV1.JEDIS_ID_URI, accept(APPLICATION_JSON), handler::deleteJedi)
                .build();
    }
}

package com.github.manerajona.reactive.ports.input.rs.mapper;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.ports.input.rs.request.CreateJediRequest;
import com.github.manerajona.reactive.ports.input.rs.request.UpdateJediRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JediHandlerMapper {
    Jedi createJediRequestToJedi(CreateJediRequest request);
    Jedi updateJediRequestToJedi(UpdateJediRequest request);
}

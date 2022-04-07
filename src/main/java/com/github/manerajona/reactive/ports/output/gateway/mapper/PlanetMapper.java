package com.github.manerajona.reactive.ports.output.gateway.mapper;

import com.github.manerajona.reactive.domain.model.Planet;
import com.github.manerajona.reactive.ports.output.gateway.dto.PlanetDto;
import com.github.manerajona.reactive.ports.output.gateway.dto.ResultDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlanetMapper {
    Planet planetDtoToPlanet(PlanetDto response);

    @IterableMapping(qualifiedByName = "resultDtoToPlanet")
    List<Planet> resultDtoListToPlanetList(List<ResultDto> response);

    @Named("resultDtoToPlanet")
    @Mapping(target = "id", source = "uid")
    Planet resultDtoToPlanet(ResultDto result);
}

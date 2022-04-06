package com.github.manerajona.reactive.ports.output.r2dbc.mapper;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.ports.output.r2dbc.entity.JediH2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface JediH2Mapper {
    @Mapping(target = "id", qualifiedByName = "generateUuid")
    JediH2 jediToJediH2(Jedi jedi);
    Jedi jediH2ToJedi(JediH2 jedi);

    @Named("generateUuid")
    default UUID generateId(@Nullable UUID uuid) {
        return Optional.ofNullable(uuid).orElse(UUID.randomUUID());
    }
}

package com.github.manerajona.reactive.ports.output.mongo.mapper;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.ports.output.mongo.document.JediMongo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.lang.Nullable;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface JediMongoMapper {
    @Mapping(target = "id", qualifiedByName = "generateUuid")
    JediMongo jediToJediMongo(Jedi jedi);
    Jedi jediMongoToJedi(JediMongo jedi);

    @Named("generateUuid")
    default UUID generateId(@Nullable UUID uuid) {
        return Optional.ofNullable(uuid).orElse(UUID.randomUUID());
    }
}

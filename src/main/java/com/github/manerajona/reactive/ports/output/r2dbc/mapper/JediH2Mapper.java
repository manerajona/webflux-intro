package com.github.manerajona.reactive.ports.output.r2dbc.mapper;

import com.github.manerajona.reactive.domain.model.Jedi;
import com.github.manerajona.reactive.ports.output.r2dbc.entity.JediH2;
import org.mapstruct.Mapper;

@Mapper
public interface JediH2Mapper {
    JediH2 jediToJediH2(Jedi jedi);
    Jedi jediH2ToJedi(JediH2 jedi);
}

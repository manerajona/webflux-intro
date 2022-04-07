package com.github.manerajona.reactive.ports.output.r2dbc.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table("jedi")
public class JediH2 {

    @Id
    private UUID id;
    private String name;
    private String gender;
    private String birthYear;
    private String planet;
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JediH2 jedi = (JediH2) o;
        return Objects.equals(id, jedi.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

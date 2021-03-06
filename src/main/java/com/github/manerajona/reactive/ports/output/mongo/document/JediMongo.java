package com.github.manerajona.reactive.ports.output.mongo.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Document
@Getter
@Setter
@ToString
@NoArgsConstructor
public class JediMongo {

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
        JediMongo jedi = (JediMongo) o;
        return Objects.equals(id, jedi.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

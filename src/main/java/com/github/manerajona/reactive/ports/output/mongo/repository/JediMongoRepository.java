package com.github.manerajona.reactive.ports.output.mongo.repository;

import com.github.manerajona.reactive.ports.output.mongo.document.JediMongo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface JediMongoRepository extends ReactiveMongoRepository<JediMongo, UUID> {
}

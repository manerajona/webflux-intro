package com.github.manerajona.reactive.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.io.IOException;

@Profile("h2")
@Configuration
@RequiredArgsConstructor
public class MigrationH2Config {

    private final ResourcePatternResolver resolver;

    @Value("${db.migration.h2.path}")
    private String resourcesPath;

    @Bean
    protected ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) throws IOException {

        Resource[] resources = resolver.getResources(resourcesPath + "/*.sql");

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(resources));

        return initializer;
    }
}

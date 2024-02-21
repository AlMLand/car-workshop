package com.almland.carworkshop.utils;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class PostgreSqlTestConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresqlDB() {
        var postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.3-alpine"));
        postgreSQLContainer.withDatabaseName("car_workshop");
        postgreSQLContainer.withUsername("alex");
        postgreSQLContainer.withPassword("alex");
        return postgreSQLContainer;
    }
}

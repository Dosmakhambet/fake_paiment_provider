package com.dosmakhambetbaktiyar.module41.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class PostgreContainerConfiguration {

    private static final PostgreSQLContainer POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:13.2")
                .withUsername("postgres")
                .withPassword("postgres")
                .withDatabaseName("fake_provider")
                .withInitScript("db/migration/V1__initialization_db.sql");
    }

    @BeforeAll
    public static void startContainer() {
        POSTGRES_CONTAINER.start();
    }

    @AfterAll
    public static void stopContainer() {
        POSTGRES_CONTAINER.stop();
    }

    @DynamicPropertySource
    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", ()-> r2dbcUrl());
        registry.add("spring.r2dbc.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.r2dbc.password", POSTGRES_CONTAINER::getPassword);
    }

    private static String r2dbcUrl() {
        return String.format(
                "r2dbc:postgresql://%s:%s/%s",
                POSTGRES_CONTAINER.getHost(),
                POSTGRES_CONTAINER.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                POSTGRES_CONTAINER.getDatabaseName()
        );

    }
}

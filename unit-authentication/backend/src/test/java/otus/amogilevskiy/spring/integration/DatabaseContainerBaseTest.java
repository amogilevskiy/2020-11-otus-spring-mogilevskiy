package otus.amogilevskiy.spring.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class DatabaseContainerBaseTest {

    static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:12")
            .withDatabaseName("prop")
            .withUsername("postgres")
            .withPassword("pass")
            .withExposedPorts(5432);

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        container.start();
        registry.add("spring.datasource.url",
                () -> container.getJdbcUrl());
        registry.add("spring.datasource.username", () -> container.getUsername());
        registry.add("spring.datasource.password", () -> container.getPassword());
    }

}

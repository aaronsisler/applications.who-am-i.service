package com.ebsolutions.applications.whoami.acceptance;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(PersistenceConfigProperties.class)
public class PersistenceConfig {

  private final PersistenceConfigProperties persistenceConfigProperties;

  @Bean
  public PostgreSQLContainer<?> postgresContainer() {
    PostgreSQLContainer<?> container =
        new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName(persistenceConfigProperties.database())
            .withUsername(persistenceConfigProperties.username())
            .withPassword(persistenceConfigProperties.password())
            .withReuse(true);

    container.start();
    return container;
  }

  @Bean
  public DataSource dataSource(PostgreSQLContainer<?> container) {
    return DataSourceBuilder.create()
        .driverClassName(container.getDriverClassName())
        .url(container.getJdbcUrl() + "&currentSchema=" + persistenceConfigProperties.schema())
        .username(container.getUsername())
        .password(container.getPassword())
        .build();
  }
}

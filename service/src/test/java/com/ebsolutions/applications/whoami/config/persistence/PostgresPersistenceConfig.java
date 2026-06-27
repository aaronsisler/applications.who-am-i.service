package com.ebsolutions.applications.whoami.config.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
@EnableConfigurationProperties(PersistenceConfigProperties.class)
@RequiredArgsConstructor
public class PostgresPersistenceConfig {

  private final PersistenceConfigProperties persistenceConfigProperties;

  @Bean(initMethod = "start", destroyMethod = "stop")
  @SuppressWarnings("resource")
  public PersistenceContainer persistenceContainer() {

    PostgreSQLContainer<?> container =
        new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName(persistenceConfigProperties.database())
            .withUsername(persistenceConfigProperties.username())
            .withPassword(persistenceConfigProperties.password());

    return new PostgresPersistenceContainer(container);
  }
}
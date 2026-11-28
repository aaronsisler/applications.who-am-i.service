package com.ebsolutions.applications.whoami;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * A throwaway Postgres in a container, for local runs and tests. @ServiceConnection points the
 * datasource and Flyway at it, replacing the URL and credentials from application.yaml.
 */
@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

  /** Same major version as production (ADR 0005); the tag follows the latest 18.x patch release. */
  @Bean
  @ServiceConnection
  PostgreSQLContainer postgres() {
    return new PostgreSQLContainer(DockerImageName.parse("postgres:18"));
  }
}

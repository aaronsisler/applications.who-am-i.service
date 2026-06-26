package com.ebsolutions.applications.whoami.testfixture.config.persistence;

import lombok.RequiredArgsConstructor;
import org.testcontainers.containers.PostgreSQLContainer;

@RequiredArgsConstructor
public class PostgresPersistenceContainer implements PersistenceContainer {

  private final PostgreSQLContainer<?> container;

  @Override
  public void start() {
    container.start();
  }

  @Override
  public void stop() {
    container.stop();
  }

  @Override
  public String getJdbcUrl() {
    return container.getJdbcUrl();
  }

  @Override
  public String getUsername() {
    return container.getUsername();
  }

  @Override
  public String getPassword() {
    return container.getPassword();
  }

  @Override
  public String getDriverClassName() {
    return container.getDriverClassName();
  }
}
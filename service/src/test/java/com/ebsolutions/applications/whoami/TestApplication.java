package com.ebsolutions.applications.whoami;

import org.springframework.boot.SpringApplication;

/**
 * Starts the real application against a Postgres container: no local database needed. The "local"
 * profile supplies the remaining datasource placeholders; the container replaces the connection.
 */
public class TestApplication {

  static void main(String[] args) {
    SpringApplication.from(Application::main)
        .with(TestcontainersConfiguration.class)
        .withAdditionalProfiles("local")
        .run(args);
  }
}

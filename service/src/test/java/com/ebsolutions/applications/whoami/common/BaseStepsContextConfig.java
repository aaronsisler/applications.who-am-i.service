package com.ebsolutions.applications.whoami.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Config that will be used to provide beans used during testing, such
 * as the ObjectMapper for JSON serialization and deserialization.
 */
@TestConfiguration
public class BaseStepsContextConfig {
  @Bean
  protected ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }
}

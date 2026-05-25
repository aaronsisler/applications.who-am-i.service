package com.ebsolutions.applications.whoami.common.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ObjectMapperConfig {

  @Bean
  protected com.fasterxml.jackson.databind.ObjectMapper objectMapper() {
    return new com.fasterxml.jackson.databind.ObjectMapper().findAndRegisterModules();
  }

}

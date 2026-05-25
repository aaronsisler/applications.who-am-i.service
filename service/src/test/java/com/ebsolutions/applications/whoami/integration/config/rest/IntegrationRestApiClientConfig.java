package com.ebsolutions.applications.whoami.integration.config.rest;

import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import com.ebsolutions.applications.whoami.integration.http.MockMvcClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@TestConfiguration
public class IntegrationRestApiClientConfig {

  @Bean
  public RestApiClient restApiClient(MockMvc mockMvc, ObjectMapper objectMapper) {
    return new MockMvcClient(mockMvc, objectMapper);
  }

}

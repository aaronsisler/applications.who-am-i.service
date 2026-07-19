package com.ebsolutions.applications.whoami.config.http;

import com.ebsolutions.applications.whoami.testfixture.http.RestApiClient;
import com.ebsolutions.applications.whoami.testfixture.http.SpringRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class SpringRestClientConfig {

  @Bean
  public RestApiClient restApiClient(RestClient restClient, ObjectMapper objectMapper) {
    return new SpringRestClient(restClient, objectMapper);
  }

}

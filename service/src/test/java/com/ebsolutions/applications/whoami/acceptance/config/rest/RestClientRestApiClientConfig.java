package com.ebsolutions.applications.whoami.acceptance.config.rest;

import com.ebsolutions.applications.whoami.acceptance.http.RestClientRestRestApiClient;
import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

@TestConfiguration
@Profile("acceptance")
public class RestClientRestApiClientConfig {

  @Bean
  public RestApiClient restApiClient(RestClient restClient) {
    return new RestClientRestRestApiClient(restClient);
  }

}

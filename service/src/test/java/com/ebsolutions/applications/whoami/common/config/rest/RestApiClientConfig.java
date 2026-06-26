package com.ebsolutions.applications.whoami.common.config.rest;

import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import com.ebsolutions.applications.whoami.common.http.SpringRestClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class RestApiClientConfig {

  @Bean
  public RestApiClient restApiClient(RestClient restClient) {
    return new SpringRestClient(restClient);
  }

}

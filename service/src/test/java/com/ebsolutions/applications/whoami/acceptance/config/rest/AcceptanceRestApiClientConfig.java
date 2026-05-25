package com.ebsolutions.applications.whoami.acceptance.config.rest;

import com.ebsolutions.applications.whoami.acceptance.http.SpringRestClient;
import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("acceptance")
public class AcceptanceRestApiClientConfig {

  @Bean
  public RestApiClient restApiClient(org.springframework.web.client.RestClient restClient) {
    return new SpringRestClient(restClient);
  }

}

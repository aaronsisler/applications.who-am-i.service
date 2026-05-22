package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Config that will be used to provide beans used during testing, such
 * as the RestApiClient for calling REST APIs in the tests.
 */
@TestConfiguration
public class HttpStepsContextConfig {

  @Bean
  public RestApiClient restApiClient() {
    return Mockito.mock(RestApiClient.class);
  }

}

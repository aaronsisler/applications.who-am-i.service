package com.ebsolutions.applications.whoami.testfixture.http;

import org.springframework.http.MediaType;

public interface RestApiClient {

  ScenarioResponse get(String route);

  ScenarioResponse post(
      String route,
      MediaType contentType,
      String rawRequestBody
  );

}

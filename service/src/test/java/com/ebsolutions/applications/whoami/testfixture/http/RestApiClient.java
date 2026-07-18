package com.ebsolutions.applications.whoami.testfixture.http;

import java.util.Map;
import org.springframework.http.MediaType;

public interface RestApiClient {

  ScenarioResponse delete(String route, String id);

  ScenarioResponse get(String route);

  ScenarioResponse get(String route, String id);

  ScenarioResponse post(
      String route,
      MediaType contentType,
      Map<String, Object> requestBody
  );

  ScenarioResponse put(
      String route,
      String id,
      MediaType contentType,
      Map<String, Object> requestBody
  );

}

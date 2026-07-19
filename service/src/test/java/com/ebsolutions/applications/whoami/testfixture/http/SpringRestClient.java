package com.ebsolutions.applications.whoami.testfixture.http;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;


@RequiredArgsConstructor
public final class SpringRestClient implements RestApiClient {

  private final RestClient restClient;

  @Override
  public ScenarioResponse get(String route) {
    try {
      return restClient
          .get()
          .uri(route)
          .exchange((request, response) -> {
            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

            return new ScenarioResponse(
                response.getStatusCode().value(),
                body,
                response.getHeaders().toSingleValueMap()
            );
          });
    } catch (Exception e) {
      throw new RuntimeException("Failed to perform GET request to " + route, e);
    }
  }

  @Override
  public ScenarioResponse post(
      String route,
      MediaType contentType,
      String rawRequestBody
  ) {
    try {
      return restClient
          .post()
          .uri(route)
          .contentType(contentType)
          .body(rawRequestBody)
          .exchange(
              (request, response) -> {
                String responseBody =
                    new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

                return new ScenarioResponse(
                    response.getStatusCode().value(),
                    responseBody,
                    response.getHeaders().toSingleValueMap()
                );

              });
    } catch (Exception e) {
      throw new RuntimeException("Failed to perform POST request to " + route, e);
    }
  }

}

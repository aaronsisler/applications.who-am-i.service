package com.ebsolutions.applications.whoami.testfixture.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;


@RequiredArgsConstructor
public final class SpringRestClient implements RestApiClient {

  private final RestClient restClient;
  private final ObjectMapper objectMapper;

  @Override
  public ScenarioResponse delete(String route, String id) {
    try {
      String uri = String.join("/", route, id);

      return restClient
          .delete()
          .uri(uri)
          .exchange((request, response) -> {
            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

            return new ScenarioResponse(
                response.getStatusCode().value(),
                body,
                response.getHeaders().toSingleValueMap()
            );
          });
    } catch (Exception e) {
      String errorMessage =
          String.format("Failed to perform DELETE request to %s with id %s", route, id);

      throw new RuntimeException(errorMessage, e);
    }
  }

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
  public ScenarioResponse get(String route, String id) {
    try {
      String uri = String.join("/", route, id);

      return restClient
          .get()
          .uri(uri)
          .exchange((request, response) -> {
            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);

            return new ScenarioResponse(
                response.getStatusCode().value(),
                body,
                response.getHeaders().toSingleValueMap()
            );
          });
    } catch (Exception e) {
      String errorMessage =
          String.format("Failed to perform GET request to %s with id %s", route, id);

      throw new RuntimeException(errorMessage, e);
    }
  }

  public ScenarioResponse post(
      String route,
      MediaType contentType,
      Map<String, Object> requestBody
  ) {
    try {
      return restClient
          .post()
          .uri(route)
          .contentType(contentType)
          .body(objectMapper.writeValueAsString(requestBody))
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

  public ScenarioResponse put(
      String route,
      String id,
      MediaType contentType,
      Map<String, Object> requestBody
  ) {
    try {
      String uri = String.join("/", route, id);

      return restClient
          .put()
          .uri(uri)
          .contentType(contentType)
          .body(objectMapper.writeValueAsString(requestBody))
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
      String errorMessage =
          String.format("Failed to perform PUT request to %s with id %s", route, id);

      throw new RuntimeException(errorMessage, e);
    }
  }

}

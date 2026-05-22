package com.ebsolutions.applications.whoami.integration.http;

import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import com.ebsolutions.applications.whoami.common.testfixture.ScenarioResponse;
import com.ebsolutions.applications.whoami.core.config.ApiPaths;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RequiredArgsConstructor
public class MockMvcClient implements RestApiClient {

  private final MockMvc mockMvc;
  private final ObjectMapper objectMapper;

  @Override
  public ScenarioResponse delete(String route, String id) {
    try {

      String uri = String.join("/", route, id);

      MockHttpServletResponse response = mockMvc
          .perform(MockMvcRequestBuilders.delete(uri))
          .andReturn()
          .getResponse();

      return
          new ScenarioResponse(
              response.getStatus(),
              response.getContentAsString(),
              new HashMap<>()
          );

    } catch (Exception e) {
      throw new RuntimeException("Failed to perform DELETE request to " + route, e);
    }
  }

  @Override
  public ScenarioResponse get(String route) {
    try {

      MockHttpServletResponse response = mockMvc
          .perform(MockMvcRequestBuilders.get(route))
          .andReturn()
          .getResponse();

      return
          new ScenarioResponse(
              response.getStatus(),
              response.getContentAsString(),
              new HashMap<>()
          );

    } catch (Exception e) {
      throw new RuntimeException("Failed to perform GET request to " + route, e);
    }
  }

  @Override
  public ScenarioResponse get(String route, String id) {
    try {

      String uri = String.join("/", route, id);

      MockHttpServletResponse response = mockMvc
          .perform(MockMvcRequestBuilders.get(uri))
          .andReturn()
          .getResponse();

      return
          new ScenarioResponse(
              response.getStatus(),
              response.getContentAsString(),
              new HashMap<>()
          );

    } catch (Exception e) {
      String errorMessage =
          String.format("Failed to perform GET request to %s with id %s", route, id);

      throw new RuntimeException(errorMessage, e);
    }
  }

  @Override
  public ScenarioResponse post(String route,
                               MediaType contentType,
                               Map<String, Object> requestBody) {
    try {
      String json = objectMapper.writeValueAsString(requestBody);

      MockHttpServletResponse response =
          mockMvc.perform(MockMvcRequestBuilders.post(ApiPaths.APP_USERS_PATH)
                  .contentType(contentType)
                  .content(json))
              .andReturn()
              .getResponse();

      return
          new ScenarioResponse(
              response.getStatus(),
              response.getContentAsString(),
              new HashMap<>()
          );

    } catch (JsonProcessingException jpe) {
      throw new RuntimeException("Failed to serialize request body for POST request to " + route,
          jpe);
    } catch (Exception e) {
      throw new RuntimeException("Failed to perform POST request to " + route, e);
    }
  }

  @Override
  public ScenarioResponse put(
      String route,
      String id,
      MediaType contentType,
      Map<String, Object> requestBody) {
    try {
      String json = objectMapper.writeValueAsString(requestBody);

      MockHttpServletResponse response =
          mockMvc.perform(MockMvcRequestBuilders.put(ApiPaths.APP_USERS_PATH)
                  .contentType(contentType)
                  .content(json))
              .andReturn()
              .getResponse();

      return
          new ScenarioResponse(
              response.getStatus(),
              response.getContentAsString(),
              new HashMap<>()
          );

    } catch (JsonProcessingException jpe) {
      throw new RuntimeException("Failed to serialize request body for POST request to " + route,
          jpe);
    } catch (Exception e) {
      throw new RuntimeException("Failed to perform POST request to " + route, e);
    }
  }
}

package com.ebsolutions.applications.whoami.tooling;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {
  public MediaType mediaType;

  // Store the request payload for the scenario
  public Map<String, Object> requestPayload = new HashMap<>();

  // Store the latest response for the scenario
  public MockHttpServletResponse latestResponse;
}

package com.ebsolutions.applications.whoami.tooling;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {
  public MediaType mediaType;

  public Map<String, Object> requestPayload = new HashMap<>();

  public MockHttpServletResponse latestResponse;
  public Map<String, Object> responseBody = new HashMap<>();

  public void reset() {
    requestPayload = new HashMap<>();
    mediaType = null;
    latestResponse = null;
  }
}

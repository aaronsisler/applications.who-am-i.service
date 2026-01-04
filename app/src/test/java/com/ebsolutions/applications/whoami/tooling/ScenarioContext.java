package com.ebsolutions.applications.whoami.tooling;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
public class ScenarioContext {
  // Store the request payload for the scenario
  public Map<String, Object> requestPayload = new HashMap<>();

  // Store the latest response for the scenario
  public MvcResult latestResponse;
}

package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.common.testfixture.ScenarioResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {
  public MediaType requestContentType;

  public ScenarioResponse response;

  public Map<String, Object> requestPayload = new HashMap<>();

  public void reset() {
    requestContentType = null;
    response = null;
    requestPayload.clear();
  }
}

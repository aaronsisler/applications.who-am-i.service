package com.ebsolutions.applications.whoami.acceptance;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AcceptanceScenarioContext {
  public MediaType requestContentType;

  public Map<String, Object> requestPayload = new HashMap<>();

  public ResponseEntity<String> response;

  public void reset() {
    requestContentType = null;
    requestPayload.clear();
    response = null;
  }
}
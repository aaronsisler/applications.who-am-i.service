package com.ebsolutions.applications.whoami.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {
  public MediaType mediaType;

  public Map<String, Object> requestPayload = new HashMap<>();

  public List<String> listOfRequestContents = new ArrayList<>();

  public Map<String, Object> responseBody = new HashMap<>();

  public List<MockHttpServletResponse> responses = new ArrayList<>();

  public void reset() {
    mediaType = null;
    requestPayload.clear();
    responseBody.clear();
    responses.clear();
  }
}

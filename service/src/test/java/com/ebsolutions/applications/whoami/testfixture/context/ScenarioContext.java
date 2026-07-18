package com.ebsolutions.applications.whoami.testfixture.context;

import com.ebsolutions.applications.whoami.testfixture.ScenarioResponse;
import io.cucumber.spring.ScenarioScope;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class ScenarioContext {

  public MediaType requestContentType;
  public ScenarioResponse response;
  public Map<String, Object> requestPayload = new HashMap<>();

  public ScenarioContext() {
    System.out.printf(
        "%nCREATED ScenarioContext=%s thread=%s%n",
        System.identityHashCode(this),
        Thread.currentThread().getName());
  }

  public void reset() {
    requestContentType = null;
    response = null;
    requestPayload.clear();
  }

}

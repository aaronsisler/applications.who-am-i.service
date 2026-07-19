package com.ebsolutions.applications.whoami.testfixture.context;

import com.ebsolutions.applications.whoami.testfixture.http.ScenarioResponse;
import io.cucumber.spring.ScenarioScope;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope(proxyMode = ScopedProxyMode.NO)
public class ScenarioContext {

  public MediaType requestContentType;
  public ScenarioResponse response;
  public Map<String, Object> requestPayload = new HashMap<>();
  public String rawRequestBody;

}

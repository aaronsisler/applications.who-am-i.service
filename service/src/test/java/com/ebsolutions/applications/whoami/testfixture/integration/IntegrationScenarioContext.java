package com.ebsolutions.applications.whoami.testfixture.integration;

import com.ebsolutions.applications.whoami.testfixture.ScenarioContext;
import com.ebsolutions.applications.whoami.testfixture.ScenarioResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class IntegrationScenarioContext extends ScenarioContext {

  public List<Map<String, Object>> listOfRequestContents = new ArrayList<>();

  public List<ScenarioResponse> responses;

  @Override
  public void reset() {
    super.reset();

    listOfRequestContents.clear();
    responses.clear();
  }
}
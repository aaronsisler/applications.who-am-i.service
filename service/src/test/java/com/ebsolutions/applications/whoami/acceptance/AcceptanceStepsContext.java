package com.ebsolutions.applications.whoami.acceptance;

import com.ebsolutions.applications.whoami.common.BaseStepsContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import({AcceptanceStepsContextConfig.class, PersistenceConfig.class})
public class AcceptanceStepsContext extends BaseStepsContext {
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected RestClient restClient;
  @Autowired
  protected AcceptanceScenarioContext acceptanceScenarioContext;
}
package com.ebsolutions.applications.whoami.integration;

import com.ebsolutions.applications.whoami.common.HttpStepsContext;
import com.ebsolutions.applications.whoami.integration.config.rest.RestApiClientConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@AutoConfigureMockMvc
@CucumberContextConfiguration
@SpringBootTest
@Import({IntegrationStepsContextConfig.class, RestApiClientConfig.class})
public class IntegrationStepsContext extends HttpStepsContext {

  @Autowired
  protected IntegrationScenarioContext integrationScenarioContext;

}
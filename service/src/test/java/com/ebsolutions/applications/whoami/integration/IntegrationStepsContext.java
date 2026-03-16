package com.ebsolutions.applications.whoami.integration;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.common.BaseStepsContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
@Import(IntegrationStepsContextConfig.class)
public class IntegrationStepsContext extends BaseStepsContext {
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected IntegrationScenarioContext integrationScenarioContext;
  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected AppUserRepository appUserRepository;
}
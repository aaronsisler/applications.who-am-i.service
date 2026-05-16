package com.ebsolutions.applications.whoami.integration;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.common.BaseStepsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest
@AutoConfigureMockMvc
//@CucumberContextConfiguration
@Import(IntegrationStepsContextConfig.class)
public class IntegrationStepsContext extends BaseStepsContext {

  @Autowired
  protected IntegrationScenarioContext integrationScenarioContext;

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected AppUserRepository appUserRepository;
}
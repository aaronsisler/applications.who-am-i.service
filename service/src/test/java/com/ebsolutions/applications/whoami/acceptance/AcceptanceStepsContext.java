package com.ebsolutions.applications.whoami.acceptance;

import com.ebsolutions.applications.whoami.acceptance.config.persistence.PersistenceConfig;
import com.ebsolutions.applications.whoami.common.BaseStepsContext;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import({AcceptanceStepsContextConfig.class, PersistenceConfig.class})
public class AcceptanceStepsContext extends BaseStepsContext {

  @Autowired
  protected PostgreSQLContainer<?> persistenceContainer;

  @Autowired
  protected RestClient restClient;
}

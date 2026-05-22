package com.ebsolutions.applications.whoami.acceptance;

import com.ebsolutions.applications.whoami.acceptance.config.persistence.PersistenceConfig;
import com.ebsolutions.applications.whoami.acceptance.config.rest.RestClientRestApiClientConfig;
import com.ebsolutions.applications.whoami.common.HttpStepsContext;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import({
    AcceptanceStepsContextConfig.class,
    RestClientRestApiClientConfig.class,
    PersistenceConfig.class
})
public class AcceptanceStepsContext extends HttpStepsContext {

  @Autowired
  protected PostgreSQLContainer<?> persistenceContainer;

}

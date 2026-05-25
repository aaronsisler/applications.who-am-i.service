package com.ebsolutions.applications.whoami.acceptance;

import com.ebsolutions.applications.whoami.acceptance.config.persistence.PersistenceConfig;
import com.ebsolutions.applications.whoami.acceptance.config.rest.AcceptanceRestApiClientConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import({
    AcceptanceRestApiClientConfig.class,
    PersistenceConfig.class
})
@CucumberContextConfiguration
public class AcceptanceCucumberConfig {
}

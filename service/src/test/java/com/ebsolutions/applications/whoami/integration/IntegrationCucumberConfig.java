package com.ebsolutions.applications.whoami.integration;

import com.ebsolutions.applications.whoami.integration.config.rest.IntegrationRestApiClientConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@CucumberContextConfiguration
@SpringBootTest
@Import({
    IntegrationRestApiClientConfig.class
})
public class IntegrationCucumberConfig {
}

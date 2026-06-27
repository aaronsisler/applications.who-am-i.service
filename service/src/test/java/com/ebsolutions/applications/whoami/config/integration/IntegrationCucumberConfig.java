package com.ebsolutions.applications.whoami.config.integration;

import com.ebsolutions.applications.whoami.config.rest.RestApiClientConfig;
import com.ebsolutions.applications.whoami.config.rest.RestClientConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@CucumberContextConfiguration
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@Import({
    RestClientConfig.class,
    RestApiClientConfig.class
})
@ComponentScan(basePackages = {
    "com.ebsolutions.applications.whoami.common.testfixture"
})
public class IntegrationCucumberConfig {
}

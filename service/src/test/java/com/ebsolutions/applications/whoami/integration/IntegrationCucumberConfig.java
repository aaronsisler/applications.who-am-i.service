package com.ebsolutions.applications.whoami.integration;

import com.ebsolutions.applications.whoami.common.CommonCucumberTest;
import com.ebsolutions.applications.whoami.integration.config.rest.IntegrationRestApiClientConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;

@Import({
    IntegrationRestApiClientConfig.class
})
@AutoConfigureMockMvc
@CommonCucumberTest
public class IntegrationCucumberConfig {
}

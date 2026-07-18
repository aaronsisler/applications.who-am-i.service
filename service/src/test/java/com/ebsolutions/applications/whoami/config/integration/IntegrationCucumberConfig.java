package com.ebsolutions.applications.whoami.config.integration;

import com.ebsolutions.applications.whoami.config.http.RestClientConfig;
import com.ebsolutions.applications.whoami.config.http.SpringRestClientConfig;
import com.ebsolutions.applications.whoami.testfixture.CommonCucumberTest;
import com.ebsolutions.applications.whoami.testfixture.port.CommonPortOverrides;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import({
    RestClientConfig.class,
    SpringRestClientConfig.class,
})
@CommonCucumberTest
@Profile("integration")
public class IntegrationCucumberConfig extends CommonPortOverrides {
}

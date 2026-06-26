package com.ebsolutions.applications.whoami.testfixture.acceptance;

import com.ebsolutions.applications.whoami.testfixture.CommonCucumberTest;
import com.ebsolutions.applications.whoami.testfixture.config.persistence.PersistenceConfig;
import com.ebsolutions.applications.whoami.testfixture.config.rest.RestApiClientConfig;
import org.springframework.context.annotation.Import;

@Import({
    RestApiClientConfig.class,
    PersistenceConfig.class
})
@CommonCucumberTest
public class AcceptanceCucumberConfig {
}

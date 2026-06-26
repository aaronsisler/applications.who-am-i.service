package com.ebsolutions.applications.whoami.acceptance;

import com.ebsolutions.applications.whoami.acceptance.config.persistence.PersistenceConfig;
import com.ebsolutions.applications.whoami.common.CommonCucumberTest;
import com.ebsolutions.applications.whoami.common.config.rest.RestApiClientConfig;
import org.springframework.context.annotation.Import;

@Import({
    RestApiClientConfig.class,
    PersistenceConfig.class
})
@CommonCucumberTest
public class AcceptanceCucumberConfig {
}

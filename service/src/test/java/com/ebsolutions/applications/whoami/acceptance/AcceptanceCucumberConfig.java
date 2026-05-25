package com.ebsolutions.applications.whoami.acceptance;

import com.ebsolutions.applications.whoami.acceptance.config.persistence.PersistenceConfig;
import com.ebsolutions.applications.whoami.acceptance.config.rest.AcceptanceRestApiClientConfig;
import com.ebsolutions.applications.whoami.common.CommonCucumberTest;
import org.springframework.context.annotation.Import;

@Import({
    AcceptanceRestApiClientConfig.class,
    PersistenceConfig.class
})
@CommonCucumberTest
public class AcceptanceCucumberConfig {
}

package com.ebsolutions.applications.whoami.config.integration;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.config.http.RestClientConfig;
import com.ebsolutions.applications.whoami.config.http.SpringRestClientConfig;
import com.ebsolutions.applications.whoami.testfixture.CommonCucumberTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@Profile("integration")
@CommonCucumberTest
@Import({
    SpringRestClientConfig.class,
    RestClientConfig.class,
})
public class IntegrationCucumberConfig {
  @MockitoBean
  public AppUserRepository appUserRepository;
}

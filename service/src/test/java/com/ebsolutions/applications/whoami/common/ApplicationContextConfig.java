package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Config that will be used to provide mocked beans for the application context during testing.
 * This allows us to isolate the components under test and avoid dependencies on real
 * implementations of repositories or services.Each bean defined here will be a Mockito mock.
 */
@TestConfiguration
public class ApplicationContextConfig {

  @Bean
  public AppUserRepository appUserRepository() {
    return Mockito.mock(AppUserRepository.class);
  }

}

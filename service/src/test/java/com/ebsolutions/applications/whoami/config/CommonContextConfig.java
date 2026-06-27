package com.ebsolutions.applications.whoami.config;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config that will be used to provide mocked beans for the application context during testing.
 * This allows us to isolate the components under test and avoid dependencies on real
 * implementations of repositories or services.Each bean defined here will be a Mockito mock.
 */
@Configuration
public class CommonContextConfig {

  @Bean
  public AppUserRepository appUserRepository() {
    return Mockito.mock(AppUserRepository.class);
  }

}

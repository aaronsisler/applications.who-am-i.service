package com.ebsolutions.applications.whoami.integration;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class IntegrationStepsContextConfig {

  @Bean
  AppUserRepository createAppUserRepository() {
    return Mockito.mock(AppUserRepository.class);
  }
}
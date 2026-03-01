package com.ebsolutions.applications.whoami.support;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class StepContextConfig {

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }

  @Bean
  AppUserRepository appUserRepository() {
    return Mockito.mock(AppUserRepository.class);
  }
}
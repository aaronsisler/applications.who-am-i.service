package com.ebsolutions.applications.whoami.tooling;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class BaseStepsConfig {

  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
  }

  @Bean
  AppUserRepository appUserRepository() {
    return Mockito.mock(AppUserRepository.class);
  }

  //  @Bean
  //  LocalDateTimeGenerator localDateTimeGenerator() {
  //    return Mockito.mock(LocalDateTimeGenerator.class);
  //  }

  //  @Bean
  //  UuidGenerator uuidGenerator() {
  //    return Mockito.mock(UuidGenerator.class);
  //  }

  //  @Bean
  //  EmailAddressValidator emailAddressValidator() {
  //    return Mockito.mock(EmailAddressValidator.class);
  //  }
}
package com.ebsolutions.applications.whoami.tooling;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.ebsolutions.applications.whoami.core.LocalDateTimeGenerator;
import com.ebsolutions.applications.whoami.core.UuidGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class BaseStepsConfig {

  @Bean
  ObjectMapper objectMapper() {
    //    return JsonMapper.builder()
    //        .addModule(new JavaTimeModule())
    //        .build();
    return new ObjectMapper().findAndRegisterModules();
  }

  @Bean
  LocalDateTimeGenerator localDateTimeGenerator() {
    LocalDateTimeGenerator mock = Mockito.mock(LocalDateTimeGenerator.class);

    Mockito.when(mock.now()).thenReturn(BaseSteps.MOCKED_NOW);

    return mock;
  }

  @Bean
  UuidGenerator uuidGenerator() {
    UuidGenerator mock = Mockito.mock(UuidGenerator.class);

    Mockito.when(mock.generate()).thenReturn(BaseSteps.MOCKED_UUID);

    return mock;
  }

  @Bean
  AppUserRepository appUserRepository() {
    return Mockito.mock(AppUserRepository.class);
  }
}
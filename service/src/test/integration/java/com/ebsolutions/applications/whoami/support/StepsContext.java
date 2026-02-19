package com.ebsolutions.applications.whoami.support;

import com.ebsolutions.applications.whoami.appuser.core.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@CucumberContextConfiguration
@Import(StepContextConfig.class)
public class StepsContext {
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ScenarioContext scenarioContext;
  @Autowired
  protected AppUserRepository appUserRepository;

  protected String normalize(String value) {
    return switch (value) {
      case TestFixtures.BLANK_STRING_IDENTIFIER -> StringUtils.EMPTY;
      case TestFixtures.NULL_STRING_IDENTIFIER -> null;
      case TestFixtures.TEXT_FIELD_MAX_LENGTH_45_IDENTIFIER ->
          TestFixtures.TEXT_FIELD_MAX_LENGTH_45_VALUE;
      case TestFixtures.EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_IDENTIFIER ->
          TestFixtures.EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_VALUE;
      default -> value;
    };
  }
}

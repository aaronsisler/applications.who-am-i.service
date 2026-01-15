package com.ebsolutions.applications.whoami.tooling;

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
@Import(BaseStepsConfig.class)
public class BaseSteps {
  protected static final String BLANK_STRING_IDENTIFIER = "<blank>";
  protected static final String NULL_STRING_IDENTIFIER = "<null>";

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
      case BLANK_STRING_IDENTIFIER -> StringUtils.EMPTY;
      case NULL_STRING_IDENTIFIER -> null;
      default -> value;
    };
  }
}

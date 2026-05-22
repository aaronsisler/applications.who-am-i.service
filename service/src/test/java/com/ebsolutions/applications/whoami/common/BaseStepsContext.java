package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.common.testfixture.PlaceholderTokens;
import com.ebsolutions.applications.whoami.common.testfixture.ValidationTestValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({
    BaseStepsContextConfig.class
})
public class BaseStepsContext {

  @Autowired
  public ScenarioContext scenarioContext;

  @Autowired
  public ObjectMapper objectMapper;

  protected String normalizeTestFixture(String value) {
    return switch (value) {
      case PlaceholderTokens.BLANK_STRING_IDENTIFIER -> StringUtils.EMPTY;
      case PlaceholderTokens.NULL_STRING_IDENTIFIER -> null;
      case PlaceholderTokens.TEXT_FIELD_MAX_LENGTH_45_IDENTIFIER ->
          ValidationTestValues.TEXT_FIELD_MAX_LENGTH_45_VALUE;
      case PlaceholderTokens.EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_IDENTIFIER ->
          ValidationTestValues.EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_VALUE;
      default -> value;
    };
  }
}
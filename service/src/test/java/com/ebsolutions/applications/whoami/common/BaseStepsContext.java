package com.ebsolutions.applications.whoami.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

@Import(BaseStepsContextConfig.class)
public abstract class BaseStepsContext {

  protected String normalizeTestFixture(String value) {
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

  protected MediaType normalizeMediaType(String value) {
    return switch (value) {
      case MediaType.APPLICATION_JSON_VALUE -> MediaType.APPLICATION_JSON;
      case MediaType.TEXT_PLAIN_VALUE -> MediaType.TEXT_PLAIN;
      default -> throw new IllegalArgumentException("Unsupported media type: " + value);
    };
  }
}
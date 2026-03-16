package com.ebsolutions.applications.whoami.common;

import org.apache.commons.lang3.StringUtils;


public abstract class BaseStepsContext {
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
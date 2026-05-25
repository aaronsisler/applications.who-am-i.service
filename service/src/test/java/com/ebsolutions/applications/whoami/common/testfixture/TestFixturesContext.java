package com.ebsolutions.applications.whoami.common.testfixture;

import org.apache.commons.lang3.StringUtils;

public interface TestFixturesContext {

  default String normalizeTestFixture(String value) {
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

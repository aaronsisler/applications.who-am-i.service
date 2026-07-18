package com.ebsolutions.applications.whoami.testfixture.context;

import com.ebsolutions.applications.whoami.testfixture.PlaceholderTokens;
import com.ebsolutions.applications.whoami.testfixture.ValidationTestValues;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

public interface PlaceholderTokenResolver {

  default MediaType resolveMediaType(String value) {
    return switch (value) {
      case MediaType.APPLICATION_JSON_VALUE -> MediaType.APPLICATION_JSON;
      case MediaType.TEXT_PLAIN_VALUE -> MediaType.TEXT_PLAIN;
      default -> throw new IllegalArgumentException("Unsupported media type: " + value);
    };
  }

  default String resolvePlaceholderToken(String value) {
    return switch (value) {
      case PlaceholderTokens.BLANK_STRING_IDENTIFIER -> StringUtils.EMPTY;
      case PlaceholderTokens.NULL_STRING_IDENTIFIER -> null;
      case PlaceholderTokens.TEXT_FIELD_MAX_LENGTH_45_IDENTIFIER ->
          ValidationTestValues.TEXT_FIELD_MAX_LENGTH_45_VALUE;
      case PlaceholderTokens.EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_IDENTIFIER ->
          ValidationTestValues.EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_VALUE;
      case PlaceholderTokens.NOT_APPLICABLE_IDENTIFIER -> ValidationTestValues.NOT_APPLICABLE_VALUE;
      default -> value;
    };
  }

}

package com.ebsolutions.applications.whoami.testfixture;

import org.springframework.http.MediaType;

public interface HttpTestFixturesContext {

  default MediaType normalizeMediaType(String value) {
    return switch (value) {
      case MediaType.APPLICATION_JSON_VALUE -> MediaType.APPLICATION_JSON;
      case MediaType.TEXT_PLAIN_VALUE -> MediaType.TEXT_PLAIN;
      default -> throw new IllegalArgumentException("Unsupported media type: " + value);
    };
  }

}
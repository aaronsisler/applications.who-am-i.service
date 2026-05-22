package com.ebsolutions.applications.whoami.common;

import com.ebsolutions.applications.whoami.common.http.RestApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

@Import({
    HttpStepsContextConfig.class
})
public class HttpStepsContext extends ApplicationStepsContext {

  @Autowired
  public RestApiClient restApiClient;

  protected MediaType normalizeMediaType(String value) {
    return switch (value) {
      case MediaType.APPLICATION_JSON_VALUE -> MediaType.APPLICATION_JSON;
      case MediaType.TEXT_PLAIN_VALUE -> MediaType.TEXT_PLAIN;
      default -> throw new IllegalArgumentException("Unsupported media type: " + value);
    };
  }
}
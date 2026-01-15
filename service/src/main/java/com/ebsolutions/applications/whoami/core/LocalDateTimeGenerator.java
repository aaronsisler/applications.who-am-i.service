package com.ebsolutions.applications.whoami.core;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeGenerator {
  /**
   * Returns the current local date and time.
   *
   * @return The current LocalDateTime.
   */
  public LocalDateTime now() {
    return LocalDateTime.now();
  }
}

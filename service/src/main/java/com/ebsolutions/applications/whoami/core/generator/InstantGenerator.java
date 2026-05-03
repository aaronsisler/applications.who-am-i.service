package com.ebsolutions.applications.whoami.core.generator;

import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class InstantGenerator {
  /**
   * Returns the current local date and time.
   *
   * @return The current Instant.
   */
  public Instant now() {
    return Instant.now();
  }
}

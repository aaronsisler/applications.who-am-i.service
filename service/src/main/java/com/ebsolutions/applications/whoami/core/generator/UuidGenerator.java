package com.ebsolutions.applications.whoami.core.generator;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public final class UuidGenerator {
  /**
   * Generates a random UUID.
   *
   * @return A randomly generated UUID.
   */
  public UUID generate() {
    return UUID.randomUUID();
  }
}

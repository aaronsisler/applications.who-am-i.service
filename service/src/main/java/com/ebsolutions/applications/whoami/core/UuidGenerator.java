package com.ebsolutions.applications.whoami.core;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UuidGenerator {
  /**
   * Generates a random UUID.
   *
   * @return A randomly generated UUID.
   */
  public UUID randomUuid() {
    return UUID.randomUUID();
  }
}

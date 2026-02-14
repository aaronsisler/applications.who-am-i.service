package com.ebsolutions.applications.whoami.support;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestFixtures {
  public static final String BLANK_STRING_IDENTIFIER = "<blank>";
  public static final String NULL_STRING_IDENTIFIER = "<null>";
  public static final UUID MOCKED_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
  public static final LocalDateTime MOCKED_NOW = LocalDateTime.of(2024, 1, 1, 12, 0);
}

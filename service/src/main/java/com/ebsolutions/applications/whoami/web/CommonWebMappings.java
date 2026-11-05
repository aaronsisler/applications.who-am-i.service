package com.ebsolutions.applications.whoami.web;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/** Conversions every web mapper needs. Mappers extend this; it is not a mapper itself. */
public interface CommonWebMappings {

  default OffsetDateTime toOffsetDateTime(Instant instant) {
    return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
  }

  default Instant toInstant(OffsetDateTime dateTime) {
    return dateTime == null ? null : dateTime.toInstant();
  }
}

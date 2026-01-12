package com.ebsolutions.applications.whoami.core;

import org.jspecify.annotations.NonNull;


public class DuplicateDataException extends RuntimeException {
  public DuplicateDataException(@NonNull String message, Throwable cause) {
    super(message, cause);
  }
}

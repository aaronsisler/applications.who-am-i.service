package com.ebsolutions.applications.whoami.core;

import org.jspecify.annotations.NonNull;


public class InvalidDataFormatException extends RuntimeException {
  public InvalidDataFormatException(@NonNull String message) {
    super(message);
  }
}

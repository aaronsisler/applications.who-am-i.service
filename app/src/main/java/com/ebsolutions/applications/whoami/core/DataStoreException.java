package com.ebsolutions.applications.whoami.core;

public class DataStoreException extends RuntimeException {
  public DataStoreException(String message) {
    super(message);
  }

  public DataStoreException(String message, Throwable cause) {
    super(message, cause);
  }
}

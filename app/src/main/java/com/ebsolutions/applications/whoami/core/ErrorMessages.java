package com.ebsolutions.applications.whoami.core;

import org.apache.commons.lang3.NotImplementedException;

public class ErrorMessages {
  public static final String UNEXPECTED_SERVER_ERROR = "Unexpected server error";
  public static final String UNSUPPORTED_CONTENT_TYPE = "Unsupported Content-Type";
  public static final String HTTP_METHOD_NOT_ALLOWED = "HTTP method not allowed";
  public static final String MISSING_AUTH_N_HEADER = "Missing Authentication header";
  public static final String EMAIL_ALREADY_EXISTS = "Email address already exists";
  public static final String APP_USER_NOT_SAVED = "App user cannot be saved";

  private ErrorMessages() {
    throw new NotImplementedException("Private constructor; not to be instantiated");
  }
}

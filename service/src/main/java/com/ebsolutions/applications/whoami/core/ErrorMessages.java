package com.ebsolutions.applications.whoami.core;

public enum ErrorMessages {

  APP_USER_NOT_SAVED("App user cannot be saved"),
  EMAIL_ALREADY_EXISTS("Email address already exists"),
  EMAIL_FORMAT_INVALID("emailAddress must be a well-formed email address"),
  HTTP_METHOD_NOT_ALLOWED("HTTP method not allowed"),
  MESSAGE_NOT_READABLE("Submitted message was not readable"),
  MISSING_AUTH_N_HEADER("Missing Authentication header"),
  UNEXPECTED_SERVER_ERROR("Unexpected server error"),
  UNSUPPORTED_CONTENT_TYPE("Unsupported Content-Type");

  private final String message;

  ErrorMessages(String message) {
    this.message = message;
  }

  public String message() {
    return message;
  }
}

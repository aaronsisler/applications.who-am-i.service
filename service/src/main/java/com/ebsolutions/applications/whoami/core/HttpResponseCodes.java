package com.ebsolutions.applications.whoami.core;

public final class HttpResponseCodes {
  public static final String BAD_REQUEST = "400";
  public static final String UNAUTHORIZED = "401";
  public static final String FORBIDDEN = "403";
  public static final String METHOD_NOT_ALLOWED = "405";
  public static final String CONFLICT = "409";
  public static final String UNSUPPORTED_MEDIA_TYPE = "415";
  public static final String INTERNAL_SERVER_ERROR = "500";
  public static final String SERVICE_UNAVAILABLE = "503";

  private HttpResponseCodes() {
  }
}
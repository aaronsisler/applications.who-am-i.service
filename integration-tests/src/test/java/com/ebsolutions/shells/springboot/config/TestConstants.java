package com.ebsolutions.shells.springboot.config;

public class TestConstants {
  public static final int APPLICATION_START_TIME_WAIT_PERIOD_IN_MILLISECONDS = 30000;
  public static final int QUEUE_POLLING_WAIT_PERIOD_IN_MILLISECONDS = 5000;
  public static final String EMPTY_STRING_ENUM = "EMPTY_STRING";
  public static final String EMPTY_STRING = "";

  public static final String BASE_URL = "http://localhost:8080";
  public static final String HEALTH_CHECK_URI = "/actuator/health";
  public static final String INFO_CHECK_URI = "/actuator/info";
}

package com.ebsolutions.applications.whoami.support;

public class TestFixtures {
  public static final String BLANK_STRING_IDENTIFIER = "<blank>";
  public static final String NULL_STRING_IDENTIFIER = "<null>";
  public static final String TEXT_FIELD_MAX_LENGTH_45_IDENTIFIER = "<textFieldMaxLength45>";
  public static final String EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_IDENTIFIER =
      "<emailAddressFieldMaxLength100>";

  public static final String TEXT_FIELD_MAX_LENGTH_45_VALUE = "a".repeat(46);
  public static final String EMAIL_ADDRESS_FIELD_MAX_LENGTH_100_VALUE =
      "a".repeat(90) + "@example.com";
}

package com.ebsolutions.applications.whoami.testfixture.runner;

public final class PackageNames {
  public static final String BASE_PACKAGE_NAME = "com.ebsolutions.applications.whoami";
  public static final String CORE_PACKAGE_NAME = "core";
  public static final String INTEGRATION_PACKAGE_NAME = "integration";
  public static final String NONFUNCTIONAL_PACKAGE_NAME = "nonfunctional";
  public static final String TEST_FIXTURE_PACKAGE_NAME = "testfixture";
  private static final String CONFIG_PACKAGE_NAME = "config";
  public static final String INTEGRATION_CONFIG_PACKAGE_NAME =
      CONFIG_PACKAGE_NAME + "." + INTEGRATION_PACKAGE_NAME;

  private PackageNames() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Utility class");
  }
}

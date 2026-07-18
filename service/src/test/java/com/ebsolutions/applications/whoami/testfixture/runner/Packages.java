package com.ebsolutions.applications.whoami.testfixture.runner;

import static com.ebsolutions.applications.whoami.testfixture.runner.PackageNames.BASE_PACKAGE_NAME;
import static com.ebsolutions.applications.whoami.testfixture.runner.PackageNames.CORE_PACKAGE_NAME;
import static com.ebsolutions.applications.whoami.testfixture.runner.PackageNames.INTEGRATION_CONFIG_PACKAGE_NAME;
import static com.ebsolutions.applications.whoami.testfixture.runner.PackageNames.NONFUNCTIONAL_PACKAGE_NAME;
import static com.ebsolutions.applications.whoami.testfixture.runner.PackageNames.TEST_FIXTURE_PACKAGE_NAME;

public final class Packages {


  public static final String BASE_PACKAGE = BASE_PACKAGE_NAME;

  public static final String CORE_PACKAGE =
      BASE_PACKAGE_NAME + "." + CORE_PACKAGE_NAME;

  public static final String INTEGRATION_CONFIG_PACKAGE =
      BASE_PACKAGE_NAME + "." + INTEGRATION_CONFIG_PACKAGE_NAME;

  public static final String NONFUNCTIONAL_PACKAGE =
      BASE_PACKAGE_NAME + "." + NONFUNCTIONAL_PACKAGE_NAME;

  public static final String TEST_FIXTURE_PACKAGE =
      BASE_PACKAGE_NAME + "." + TEST_FIXTURE_PACKAGE_NAME;


  private Packages() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Utility class");
  }
}

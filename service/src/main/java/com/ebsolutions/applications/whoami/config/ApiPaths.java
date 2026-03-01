package com.ebsolutions.applications.whoami.config;

public class ApiPaths {
  /**
   * Base path for all API endpoints related
   * to app users {@link com.ebsolutions.applications.whoami.appuser.core.AppUserBaseController}
   */
  public static final String APP_USERS_PATH = "/app-users";

  /**
   * Base path for all Spring Boot Actuator endpoints
   */
  private static final String APPLICATION_BASE_PATH = "/actuator";
  /**
   * Spring Boot Actuator Info Endpoint
   * <p>
   * This endpoint provides general information about the
   * application, such as build details, version, and other metadata.
   */
  public static final String APPLICATION_INFO_PATH = APPLICATION_BASE_PATH + "/info";
  /**
   * Spring Boot Actuator Health Endpoint
   * <p>
   * This endpoint provides health status information about the application,
   * including checks for database connectivity, external service availability,
   * and other critical components.
   */
  public static final String APPLICATION_HEALTH_PATH = APPLICATION_BASE_PATH + "/health";

  /**
   * This class should not be instantiated
   */
  private ApiPaths() {
  }
}

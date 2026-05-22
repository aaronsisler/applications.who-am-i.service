Feature: Non Functional: Application Endpoints - Integration
  In order to enforce proper API usage
  As a client
  I want to receive a clear 405 response when using unsupported HTTP methods

  Background:
    Given the application is running

  @Ignore
  Scenario Outline: 405 - Using an unsupported HTTP method should fail
    When the client makes a "<httpMethod>" request to "<endpoint>" with id "<id>"
    Then the response status should be 405
    And the response body should include an error code "METHOD_NOT_ALLOWED"

    Examples:
      | httpMethod | endpoint         | id  |
      | GET        | /actuator/health | 123 |
      | POST       | /actuator/health |     |
      | PUT        | /actuator/health | 123 |
      | DELETE     | /actuator/health | 123 |

  @Ignore
  Scenario Outline: 404 - Using an incorrect path should fail
    When the client makes a "<httpMethod>" request to "<endpoint>"
    Then the response status should be 404
    And the response body should include an error code "NOT_FOUND"

    Examples:
      | httpMethod | endpoint |
      | GET        | /taco    |

  @Ignore
  Scenario Outline: 500 - Any unexpected error should return a 500 status code
    When the client makes a "<httpMethod>" request to "<endpoint>"
    Then the response status should be 500
    And the response body should include an error code "INTERNAL_SERVER_ERROR"

    Examples:
      | httpMethod | endpoint               |
      | GET        | /system-test/exception |
Feature: Application System Endpoints - Integration
  In order to enforce proper API usage
  As a client
  I want to receive a clear 405 response when using unsupported HTTP methods

  Scenario Outline: 405 - Using an unsupported HTTP method should fail
    Given the application is running
    When the client makes a "<httpMethod>" request to "<endpoint>"
    Then the response status should be 405
    And the response body should include an error code "METHOD_NOT_ALLOWED"

    Examples:
      | httpMethod | endpoint         |
      | POST       | /actuator/health |
      | PUT        | /actuator/health |
      | DELETE     | /actuator/health |
      | PATCH      | /actuator/health |

  Scenario Outline: 404 - Using an incorrect path should fail
    Given the application is running
    When the client makes a "<httpMethod>" request to "<endpoint>"
    Then the response status should be 404
    And the response body should include an error code "NOT_FOUND"

    Examples:
      | httpMethod | endpoint |
      | GET        | /taco    |

  Scenario Outline: 500 - Any unexpected error should return a 500 status code
    Given the application is running
    When the client makes a "<httpMethod>" request to "<endpoint>"
    Then the response status should be 500
    And the response body should include an error code "INTERNAL_SERVER_ERROR"

    Examples:
      | httpMethod | endpoint               |
      | GET        | /system-test/exception |
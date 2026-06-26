Feature: Non Functional: Application Endpoints - Integration
  In order to assist with proper API usage
  As a provider of a REST API
  I want to receive a clear response for unknown requests and unexpected errors

  Background:
    Given the application is running

#  @Ignore
  Scenario Outline: 404 - Using an incorrect path should fail
    When the client makes a "<httpMethod>" request to "<endpoint>"
    Then the response status code should be <responseStatusCode>
    And the response body should include an error code "NOT_FOUND"

    Examples:
      | httpMethod | endpoint | responseStatusCode |
      | GET        | /taco    | 404                |

#  @Ignore
  Scenario Outline: 500 - Any unexpected error should return a 500 status code
    When the client makes a "<httpMethod>" request to "<endpoint>"
    Then the response status code should be <responseStatusCode>
    And the response body should include an error code "INTERNAL_SERVER_ERROR"

    Examples:
      | httpMethod | endpoint               | responseStatusCode |
      | GET        | /system-test/exception | 500                |
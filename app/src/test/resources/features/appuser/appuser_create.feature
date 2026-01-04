Feature: Create App User
  As a client of the WhoAmI service
  I want to create a new app user
  So that they can be registered in the system

  Background:
    Given the application is running

  # Happy path: all required fields supplied
  Scenario: Creating an app user with all required fields
    Given a valid create-user request contains the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the data store returns the created user
    When I submit the create-user request
    Then the response status should be 201
    And the response body should contain:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |

  # Negative path: missing required fields
  @Ignore
  Scenario Outline: Creating an app user with missing required fields should fail
    Given a valid create-user request contains the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the create-user request is missing the "<missingField>" field
    When I submit the create-user request
    Then the response status should be 400
    And the error message should contain "<missingField> is required"

    Examples:
      | missingField |
      | emailAddress |
      | firstName    |
      | lastName     |

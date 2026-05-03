Feature: Create App User - Acceptance

  Background:
    Given the application is running

  Scenario: 201 - Creating an app user with required fields
    Given the client provides a create-user request body with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 201
    And the create-user response body should contain:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the create-user response should contain a valid client-facing ID
    And the create-user response should contain a valid createdAt timestamp
    And the create-user response should contain a valid updatedAt timestamp

  Scenario: 409 - Duplicate email
    Given the client provides a create-user request body with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    When the client submits the create-user request
    Then the create-user response status should be 201
    And the client provides a create-user request body with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    When the client submits the create-user request
    Then the create-user response status should be 409
    And the create-user response should contain exactly 1 error
    And the create-user response should contain an error with:
      | field | <null>               |
      | code  | EMAIL_ALREADY_EXISTS |
#
  Scenario: 500 - Database unavailable
    Given the client provides a create-user request body with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the database is unavailable
    When the client submits the create-user request
    Then the create-user response status should be 500
    And the create-user response should contain an error with:
      | field | <null>                |
      | code  | INTERNAL_SERVER_ERROR |
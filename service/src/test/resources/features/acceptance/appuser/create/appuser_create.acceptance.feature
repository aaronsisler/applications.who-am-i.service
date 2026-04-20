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
#    And the response body should contain:
#      | emailAddress | johnny.appleseed@gmail.com |
#      | firstName    | Johnny                     |
#      | lastName     | Appleseed                  |
    And the create-user response should contain a valid client-facing ID
    And the create-user response should contain a valid createdAt timestamp
    And the create-user response should contain a valid updatedAt timestamp
#    And the app user should be persisted in the data store
#
#  Scenario: Duplicate email returns 409
#    Given an app user already exists with email "test@example.com"
#    When the client submits a create-user request with email "test@example.com"
#    Then the response status should be 409
#    And the error message should contain "Email address already exists"
#
#  Scenario: Database unavailable returns 500
#    Given the database is unavailable
#    When the client submits a valid create-user request
#    Then the response status should be 500
#    And the error message should contain "App user cannot be saved"
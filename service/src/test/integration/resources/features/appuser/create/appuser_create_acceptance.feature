Feature: Create App User (Acceptance)

  Background:
    Given the application is running

  Scenario: Successfully creating an app user
    Given the client provides a valid create-user request
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the request has content type "application/json"
    When the client submits the request
    Then the response status should be 201
    And the response body should contain:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And a client-facing ID should be returned
    And createdAt and updatedAt timestamps should be returned
    And the app user should be persisted in the data store

  Scenario: Unsupported content type returns 415
    Given the client provides a valid create-user request
    And the request has content type "text/plain"
    When the client submits the request
    Then the response status should be 415

  Scenario: Database unavailable returns 503
    Given the database is unavailable
    When the client submits a valid create-user request
    Then the response status should be 503
    And the error message should contain "App user cannot be saved"
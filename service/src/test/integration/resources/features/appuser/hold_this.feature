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

Feature: Create App User (Integration)

  Scenario Outline: Missing required fields returns 400
    Given a create-user request missing "<missingField>"
    When the client submits the request
    Then the response status should be 400
    And the error message should contain "<missingField> must not be null"
    And the data store should not be called

    Examples:
      | missingField |
      | emailAddress |
      | firstName    |
      | lastName     |

  Scenario Outline: Blank field values return 400
    Given a create-user request with invalid value for "<field>"
    When the client submits the request
    Then the response status should be 400
    And the error message should contain "<field> length must be between"

    Examples:
      | field        |
      | firstName    |
      | lastName     |
      | emailAddress |

  Scenario: Malformed JSON returns 400
    Given a create-user request with malformed JSON
    When the client submits the request
    Then the response status should be 400
    And the error message should indicate the request was not readable

  Scenario: Duplicate email returns 409
    Given a create-user request with an existing email
    And the data store reports the email already exists
    When the client submits the request
    Then the response status should be 409
    And the error message should contain "Email address already exists"

  Scenario: Data store failure returns 503
    Given the data store throws an exception
    When the client submits a valid create-user request
    Then the response status should be 503
    And the error message should contain "App user cannot be saved"

Feature: App User Domain Logic (Unit)

  Scenario: Generated client-facing UUIDs are unique
    Given a UUID generator
    When multiple UUIDs are generated
    Then each UUID should be unique

  Scenario: Created and updated timestamps use the provided clock
    Given a fixed system clock
    When a new app user is created
    Then createdAt should match the fixed time
    And updatedAt should match the fixed time

  Scenario Outline: Email format validation
    Given an email "<emailAddress>"
    When the email is validated
    Then the validation result should be "<result>"

    Examples:
      | emailAddress   | result  |
      | john@gmail.com | valid   |
      | john@          | invalid |
      | not-an-email   | invalid |

  Scenario Outline: Field length validation
    Given field "<field>" with value "<value>"
    When the field is validated
    Then the validation result should be invalid

    Examples:
      | field        | value                                       |
      | firstName    |                                             |
      | lastName     |                                             |
      | firstName    | ThisIsToLongOfAValueToBeProvidedToThisField |
      | emailAddress | a-very-long-email-address-exceeding-limits  |

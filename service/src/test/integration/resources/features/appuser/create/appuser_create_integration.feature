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
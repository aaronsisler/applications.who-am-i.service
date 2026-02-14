Feature: Create App User - Integration

  Scenario Outline: Missing required fields returns 400
    Given the client provides a create-user request with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the create-user request has a content type of "application/json"
    And the client provides the create-user request without the "<missingField>" field
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "<missingField> must not be null"
    And the data store was not called to save the new user

    Examples:
      | missingField |
      | emailAddress |
      | firstName    |
      | lastName     |

  Scenario Outline: 400 - Creating an app user with blank values should fail
    Given the client provides a create-user request with the following fields:
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | emailAddress | <emailAddress> |
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "<field> length must be between <minLength> and <maxLength>"
    And the data store was not called to save the new user

    Examples:
      | field        | minLength | maxLength | firstName | lastName  | emailAddress               |
      | firstName    | 1         | 45        | <blank>   | Appleseed | johnny.appleseed@gmail.com |
      | lastName     | 1         | 45        | Johnny    | <blank>   | johnny.appleseed@gmail.com |
      | emailAddress | 1         | 100       | Johnny    | Appleseed | <blank>                    |

  Scenario: Malformed JSON returns 400
    Given a create-user request with malformed JSON
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "Submitted message was not readable"
    And the data store was not called to save the new user

  Scenario: Unsupported content type returns 415
    Given the client provides a create-user request with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the create-user request has a content type of "text/plain"
    When the client submits the create-user request
    Then the create-user response status should be 415
    And the data store was not called to save the new user
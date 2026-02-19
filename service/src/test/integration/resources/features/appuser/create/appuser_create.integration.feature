Feature: Create App User - Integration

  Scenario Outline: Missing required fields returns 400
    Given the client provides a create-user request body with the following fields:
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

  Scenario Outline: 400 - Creating an app user with invalid field length should fail
    Given the client provides a create-user request body with the following fields:
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | emailAddress | <emailAddress> |
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "<nullField> must not be null"
    And the data store was not called to save the new user

    Examples:
      | nullField    | firstName | lastName  | emailAddress               |
      | firstName    | <null>    | Appleseed | johnny.appleseed@gmail.com |
      | lastName     | Johnny    | <null>    | johnny.appleseed@gmail.com |
      | emailAddress | Johnny    | Appleseed | <null>                     |

  Scenario Outline: 400 - Creating an app user with invalid field length should fail
    Given the client provides a create-user request body with the following fields:
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | emailAddress | <emailAddress> |
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "<field> length must be between <minLength> and <maxLength>"
    And the data store was not called to save the new user

    Examples:
      | field        | minLength | maxLength | firstName              | lastName               | emailAddress                    |
      | firstName    | 1         | 45        | <blank>                | Appleseed              | johnny.appleseed@gmail.com      |
      | lastName     | 1         | 45        | Johnny                 | <blank>                | johnny.appleseed@gmail.com      |
      | emailAddress | 1         | 100       | Johnny                 | Appleseed              | <blank>                         |
      | firstName    | 1         | 45        | <textFieldMaxLength45> | Appleseed              | johnny.appleseed@gmail.com      |
      | lastName     | 1         | 45        | Johnny                 | <textFieldMaxLength45> | johnny.appleseed@gmail.com      |
      | emailAddress | 1         | 100       | Johnny                 | Appleseed              | <emailAddressFieldMaxLength100> |

  Scenario: Malformed JSON returns 400
    Given a create-user request body with malformed JSON
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "Submitted message was not readable"
    And the data store was not called to save the new user

  Scenario: Unsupported content type returns 415
    Given the client provides a create-user request body with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the create-user request has a content type of "text/plain"
    When the client submits the create-user request
    Then the create-user response status should be 415
    And the data store was not called to save the new user

  Scenario: Each created app user receives a unique client-facing identifier
    Given the client provides two unique create-user request bodies
    And the data store is configured to save the new users
    When the client submits both of the create-user requests
    Then each create-user response should include unique client-facing identifiers
Feature: Create App User - Integration

  Scenario Outline: 400 - Creating an app user with missing required fields
    Given the client provides a create-user request body with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the create-user request has a content type of "application/json"
    And the client provides the create-user request without the "<missingField>" field
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response should contain exactly 1 error
    And the create-user response should contain an error with:
      | field | <missingField> |
      | code  | NOT_NULL       |
    And the data store was not called to save the new user

    Examples:
      | missingField |
      | emailAddress |
      | firstName    |
      | lastName     |


  Scenario Outline: 400 - Creating an app user with a null required field
    Given the client provides a create-user request body with the following fields:
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | emailAddress | <emailAddress> |
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response should contain exactly 1 error
    And the create-user response should contain an error with:
      | field | <nullField> |
      | code  | NOT_NULL    |
    And the data store was not called to save the new user

    Examples:
      | nullField    | firstName | lastName  | emailAddress               |
      | firstName    | <null>    | Appleseed | johnny.appleseed@gmail.com |
      | lastName     | Johnny    | <null>    | johnny.appleseed@gmail.com |
      | emailAddress | Johnny    | Appleseed | <null>                     |


  Scenario Outline: 400 - Creating an app user with invalid field length
    Given the client provides a create-user request body with the following fields:
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | emailAddress | <emailAddress> |
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response should contain exactly <errorCount> error
    And the create-user response should contain an error with:
      | field | <field> |
      | code  | SIZE    |
    And the data store was not called to save the new user

    Examples:
      | errorCount | field        | firstName              | lastName               | emailAddress                    |
      | 1          | firstName    | <blank>                | Appleseed              | johnny.appleseed@gmail.com      |
      | 1          | lastName     | Johnny                 | <blank>                | johnny.appleseed@gmail.com      |
      | 1          | emailAddress | Johnny                 | Appleseed              | <blank>                         |
      | 1          | firstName    | <textFieldMaxLength45> | Appleseed              | johnny.appleseed@gmail.com      |
      | 1          | lastName     | Johnny                 | <textFieldMaxLength45> | johnny.appleseed@gmail.com      |
      | 2          | emailAddress | Johnny                 | Appleseed              | <emailAddressFieldMaxLength100> |

  Scenario Outline: 400 - Creating an app user with invalid email length
    Given the client provides a create-user request body with the following fields:
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | emailAddress | <emailAddress> |
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response should contain exactly <errorCount> error
    And the create-user response should contain an error with:
      | field | <field> |
      | code  | SIZE    |
    And the create-user response should contain an error with:
      | field | <field>              |
      | code  | EMAIL_FORMAT_INVALID |
    And the data store was not called to save the new user

    Examples:
      | errorCount | field        | firstName | lastName  | emailAddress                    |
      | 2          | emailAddress | Johnny    | Appleseed | <emailAddressFieldMaxLength100> |

  Scenario: 400 - Creating an app user with malformed JSON
    Given a create-user request body with malformed JSON
    And the create-user request has a content type of "application/json"
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response should contain exactly 1 error
    And the create-user response should contain an error with:
      | code | MALFORMED_JSON |
    And the data store was not called to save the new user


  Scenario: 415 - Creating an app user with unsupported content type
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
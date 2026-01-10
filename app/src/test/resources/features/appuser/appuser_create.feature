Feature: Create App User
  As a client of the WhoAmI service
  I want to create a new app user
  So that they can be registered in the system

  Background:
    Given the application is running

  Scenario Outline: Creating an app user with missing required fields should fail
    Given the client provides a create-user request with the following fields:
      | emailAddress | johnny.appleseed@gmail.com |
      | firstName    | Johnny                     |
      | lastName     | Appleseed                  |
    And the client provides the create-user request without the "<missingField>" field
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "<missingField> must not be null"

    Examples:
      | missingField |
      | emailAddress |
      | firstName    |
      | lastName     |

  Scenario Outline: Creating an app user with blank values should fail
    Given the client provides a create-user request with the following fields:
      | firstName    | <firstName>    |
      | lastName     | <lastName>     |
      | emailAddress | <emailAddress> |
    When the client submits the create-user request
    Then the create-user response status should be 400
    And the create-user response error message should contain "<field> length must be between <minLength> and <maxLength>"

    Examples:
      | field        | minLength | maxLength | firstName | lastName  | emailAddress               |
      | firstName    | 1         | 45        | <blank>   | Appleseed | johnny.appleseed@gmail.com |
      | lastName     | 1         | 45        | Johnny    | <blank>   | johnny.appleseed@gmail.com |
      | emailAddress | 1         | 100       | Johnny    | Appleseed | <blank>                    |

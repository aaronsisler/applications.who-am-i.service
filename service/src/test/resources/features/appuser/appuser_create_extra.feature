#  # Happy path: all required fields supplied
#  @Ignore
#  Scenario: Creating an app user with all required fields
#    Given the client provides a create-user request with the following fields:
#      | emailAddress | johnny.appleseed@gmail.com |
#      | firstName    | Johnny                     |
#      | lastName     | Appleseed                  |
#    And the data store returns the created user
#    When I submit the create-user request
#    Then the create-user response status should be 201
#    And the response body should contain:
#      | emailAddress | johnny.appleseed@gmail.com |
#      | firstName    | Johnny                     |
#      | lastName     | Appleseed                  |
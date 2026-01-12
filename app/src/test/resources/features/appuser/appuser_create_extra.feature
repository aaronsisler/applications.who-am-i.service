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
#
#
#  # This will be Phase 2
##  Scenario Outline: Creating an app user with invalid email format should fail
##    Given the client provides a create-user request with emailAddress "<email>"
##    And the client provides firstName "Johnny"
##    And the client provides lastName "Appleseed"
##    When the client submits the create-user request
##    Then the response status should be 400
##    And the response error message should contain "emailAddress must be a well-formed email"
##
##    Examples:
##      | email             |
##      | john@             |
##      | not-an-email      |
##      | johnny@@gmail.com |
#
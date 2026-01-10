#Feature: Create App User
#  As a client of the WhoAmI service
#  I want to create a new app user
#  So that they can be registered in the system
#
#  Background:
#    Given the application is running
#
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
#  # Negative path: missing required fields
#  Scenario Outline: Creating an app user with missing required fields should fail
#    Given the client provides a create-user request with the following fields:
#      | emailAddress | johnny.appleseed@gmail.com |
#      | firstName    | Johnny                     |
#      | lastName     | Appleseed                  |
#    And the client provides the create-user request without the "<missingField>" field
#    When the client submits the create-user request
#    Then the response status should be 400
#    And the response error message should contain "<missingField> must not be null"
#
#    Examples:
#      | missingField |
#      | emailAddress |
#      | firstName    |
#      | lastName     |
#
#  Scenario Outline: Creating an app user with blank values should fail
#    Given the client provides a create-user request with the following fields:
#      | emailAddress | johnny.appleseed@gmail.com |
#      | firstName    | <firstName>                |
#      | lastName     | <lastName>                 |
#    When I submit the create-user request
#    Then the create-user response status should be 400
#    And the error message should contain "<field> must not be blank"
#
#    Examples:
#      | field     | firstName | lastName  |
#      | firstName |           | Appleseed |
#      | lastName  | Johnny    |           |
#
#  Scenario: Creating an app user with fields exceeding maximum length should fail
#    Given the client provides a create-user request with the following fields:
#      | emailAddress | a-very-long-email-address-that-exceeds-the-defined-limit@gmail.com |
#      | firstName    | ThisNameIsWayTooLongForTheSystemAccordingToTheSpec                 |
#      | lastName     | Appleseed                                                          |
#    When I submit the create-user request
#    Then the create-user response status should be 400
#    And the error message should contain "firstName exceeds maximum length"
#
#  Scenario: Data store failure while creating an app user should return server error
#    Given the client provides a valid create-user request
#    And the data store is unavailable
#    When the client submits the create-user request
#    Then the response status should be 503
#    And the response error message should contain "Unable to save app user"
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
#
#  Scenario: Creating an app user with an email that already exists should fail
#    Given the data store contains an app user with emailAddress "johnny.appleseed@gmail.com"
#    And the client provides a create-user request with the same emailAddress
#    When the client submits the create-user request
#    Then the response status should be 409
#    And the response error message should contain "emailAddress already exists"
#
#  Scenario: Creating an app user with malformed JSON should fail
#    Given a create-user request with invalid JSON payload
#    When I submit the create-user request
#    Then the create-user response status should be 400
#    And the error message should contain "Malformed JSON request"
#
#  Scenario: Submitting without application/json content type should fail
#    Given the client submits create-user request with Content-Type "text/plain"
#    When the client submits the create-user request
#    Then the response status should be 415
#
#

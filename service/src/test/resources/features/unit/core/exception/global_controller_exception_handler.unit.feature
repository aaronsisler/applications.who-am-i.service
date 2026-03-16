Feature: Global Controller Exception Handler - Unit

  Scenario Outline: Method - Map Validation Code
    Given a validation code "<validationCode>" is provided
    When the code is mapped to an error code
    Then the resulting error code should be "<errorCode>"

    Examples:
      | validationCode | errorCode             |
      | <null>         | NOT_NULL              |
      | <blank>        | INTERNAL_SERVER_ERROR |
      | NotNull        | NOT_NULL              |
      | NotBlank       | NOT_BLANK             |
      | Size           | SIZE                  |
      | Email          | EMAIL_FORMAT_INVALID  |
      | Email          | INTERNAL_SERVER_ERROR |
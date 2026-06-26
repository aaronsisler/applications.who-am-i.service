Feature: Error Code Mapper - Unit

  Scenario Outline: Method - Map
    Given a string value "<input>" is provided
    When the string value is mapped to an error code
    Then the resulting error code should be "<errorCode>"

    Examples:
      | input    | errorCode             |
      | <null>   | INTERNAL_SERVER_ERROR |
      | <blank>  | INTERNAL_SERVER_ERROR |
      | NotNull  | NOT_NULL              |
      | NotBlank | NOT_BLANK             |
      | Size     | SIZE                  |
      | Email    | EMAIL_FORMAT_INVALID  |

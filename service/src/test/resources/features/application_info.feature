Feature: Application Health

  Scenario: Application status and build information is made available through default endpoints
    Given the application is running
    When the info endpoint is invoked
    Then the correct info response is returned

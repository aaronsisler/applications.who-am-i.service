Feature: Integration: Health and Info Endpoints

  Scenario: Info endpoint is accessible
    Given application is up
    When the info endpoint is invoked
    Then the correct info response is returned

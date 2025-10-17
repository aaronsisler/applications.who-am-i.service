Feature: Integration: changeme

  Scenario: changeme
    Given application is up
    When the info endpoint is invoked
    Then the correct info response is returned

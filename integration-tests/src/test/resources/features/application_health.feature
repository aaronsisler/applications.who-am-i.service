Feature: Integration: changeme

  Scenario: 
    Given application is up
    When the health endpoint is invoked
    Then the correct health response is returned

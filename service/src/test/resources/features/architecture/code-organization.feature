Feature: How the code is organised
  The code is organised by business module, such as "app-user".
  Inside each feature the business rules sit at the centre, and all technical
  details (web, database, Spring) sit at the edges and depend inwards.
  These rules apply to every module automatically, including ones added later.
  How modules may depend on EACH OTHER is checked by Spring Modulith.

  Background:
    Given the production code of the application
# Scenario: Business rules do not depend on technology
# Then the domain of every module is free of framework and infrastructure code
# Scenario: Use cases do not depend on technology
# Then the application layer of every module is free of framework and infrastructure code
# Scenario: Adapters reach the core only through its ports
# Then adapters never use the application services directly
# Scenario: Web and database adapters are independent of each other
# Then inbound and outbound adapters do not depend on each other
# Scenario: Database details stay inside the database adapter
# Then only persistence adapters use SQL
# Scenario: The API contract stays at the edge
# Then code generated from the API specs is only used by the web adapter of its module

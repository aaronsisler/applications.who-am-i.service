# 0004. Cucumber for all tests

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-21 (how suites and their Spring contexts are organized)

## Context

Business rules hidden in JUnit methods are invisible to non-developers, and test styles drift when
some tests are Gherkin and others are not.

## Decision

Every test is a Cucumber scenario, including domain rules and architecture rules. Domain features
are written for non-technical readers, using `Rule:` to group scenarios under plain-language rules.

### Suites

- Cucumber runs through the JUnit Platform Suite engine, because Maven Surefire cannot discover
  feature files by itself.
- Each suite is a `@Suite` class whose annotations select its feature package (`@SelectPackages`,
  for example `features.architecture`) and its glue package (`GLUE_PROPERTY_NAME`), and optionally
  filter by tag. Suites do not switch behavior with Spring profiles.
- A suite lives in its own test package, together with its step definitions and exactly one
  `@CucumberContextConfiguration` class, named after the suite:

  |                Class                |                Role                 |
  |-------------------------------------|-------------------------------------|
  | `ArchitectureTest`                  | The suite                           |
  | `ArchitectureSteps`                 | Step definitions                    |
  | `ArchitectureCucumberConfiguration` | The Spring context the steps run in |

- Suites without Docker end in `Test` and run in Surefire (`mvn test`). Suites that need the full
  application and Postgres end in `IT` and run in Failsafe (`mvn verify`).

- Settings shared by every suite (report naming, output) live in
  `src/test/resources/junit-platform.properties`.

### Spring contexts

- Steps with no Spring needs (domain, architecture) use an empty context, because cucumber-spring
  requires one.
- Suites that need the full application share one configuration class, so Spring builds and caches
  that context only once.

## Consequences

- Product owners and testers can read and review the rules directly.
- Domain error messages become part of the specification ("rejected because ..."), so changing a
  message means changing a feature file.
- Spring contexts and beans live across scenarios, so every step class with state resets it in a
  `@Before` hook.
- Purely technical checks (architecture, wiring) are slightly more verbose in Gherkin than in JUnit;
  we accept that for consistency.
- Adding a suite means adding a package with three small classes and a feature folder; no shared
  configuration has to change.

## Alternatives considered

- **Gherkin only for business-facing behaviour, JUnit for the rest:** less indirection for
  technical tests, but two styles and no readable record of the domain rules.
- **One suite with Spring profiles selecting the context:** fewer classes, but which context a
  scenario gets depends on how it is run, and different contexts cannot run in the same build.


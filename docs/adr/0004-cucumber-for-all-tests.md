# 0004. Cucumber for all tests

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Business rules hidden in JUnit methods are invisible to non-developers, and test styles drift when
some tests are Gherkin and others are not.

## Decision

Every test is a Cucumber scenario, including domain rules and architecture rules. Domain features
are written for non-technical readers, using `Rule:` to group scenarios under plain-language rules.

Each suite selects its feature folder and its glue packages. Every glue path contains exactly one
`@CucumberContextConfiguration`; suites that share a configuration class share a cached Spring
context. Steps with no Spring needs (domain, architecture) use an empty "plain" context, because
cucumber-spring requires one.

## Consequences

- Product owners and testers can read and review the rules directly.
- Domain error messages become part of the specification ("rejected because ..."), so changing a
  message means changing a feature file.
- Spring contexts and beans live across scenarios, so every step class with state resets it in a
  `@Before` hook.
- Purely technical checks (architecture, wiring) are slightly more verbose in Gherkin than in JUnit;
  we accept that for consistency.

## Alternatives considered

- **Gherkin only for business-facing behaviour, JUnit for the rest:** less indirection for
  technical tests, but two styles and no readable record of the domain rules.


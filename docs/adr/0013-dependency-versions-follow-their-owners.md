# 0013. Dependency versions follow their owners

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Upgrading one library to its latest version can break another that was built against an older one.
We hit this with google-java-format (newer than Spotless supported), JUnit (older in Spring Boot's
dependency management than Cucumber needs) and ArchUnit (newer than Spring Modulith was tested
with).

## Decision

Each version is decided by the project that depends on it most closely:

|        Version         |                              Decided by                               |
|------------------------|-----------------------------------------------------------------------|
| Libraries Boot manages | Spring Boot's dependency management                                   |
| ArchUnit               | Spring Modulith                                                       |
| Minimum JUnit          | Cucumber's JUnit Platform engine (override Boot's property if needed) |
| google-java-format     | Spotless (its default for the release in use)                         |

Versions are declared as properties named after the artifact (`<artifactId>.version`), plugins are
declared once in `<pluginManagement>`, and each entry is commented with its version source.

## Consequences

- Upgrades happen in the order owners catch up, not all at once.
- Overrides of managed versions are explicit and commented with their reason.
- Checking an owner's changelog or build file is part of every upgrade.

## Alternatives considered

- **Always the latest of everything:** the source of the incompatibilities above.


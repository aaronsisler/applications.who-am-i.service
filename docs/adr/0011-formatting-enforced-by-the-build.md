# 0011. Formatting enforced by the build

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Pull requests should only show what was actually changed, never differences caused by someone's IDE
settings. IDE configuration cannot be enforced, and we cannot control the pipeline's environment
variables.

## Decision

- **Spotless** runs `check` in the `validate` phase of every build, locally and in the pipeline.
  Unformatted files fail the build within seconds, before anything compiles. Developers fix them
  with `mvn spotless:apply`. The build never formats code automatically.
- What Spotless formats:

|      Files       |                      Formatter                       |
|------------------|------------------------------------------------------|
| Java             | google-java-format (Google style, imports reordered) |
| `pom.xml`        | sortPom                                              |
| Feature files    | Gherkin formatter                                    |
| Markdown         | flexmark (CommonMark)                                |
| YAML, properties | Whitespace only                                      |

- **YAML** gets whitespace checks only, because Spotless's YAML formatters either drop comments or
  require Node.js. Its layout comes from `.editorconfig` in the IDE.
- **SQL migrations are never formatted**, because Flyway checksums applied migrations.
- The google-java-format version is pinned to the default of the Spotless release in use.
- In a multi-module build, Spotless is listed in the parent's `<plugins>`, so root files are
  checked too.

## Consequences

- Formatting differences cannot be merged.
- YAML consistency depends on everyone using the committed `.editorconfig`; it is not enforced by
  the pipeline. yamllint was considered and rejected as too heavy (it needs Python or Docker).
- Upgrading Spotless means checking its default formatter version and running `spotless:apply` in
  a separate commit.

## Alternatives considered

- **Checkstyle:** reports problems but cannot fix them.
- **Applying formatting in local builds via a CI-detecting profile:** depends on the pipeline
  setting `CI=true`, which we cannot guarantee.


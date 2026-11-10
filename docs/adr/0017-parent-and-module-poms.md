# 0017. Parent and module poms

- **Status:** Accepted
- **Date:** 2026-11-05

## Context

Plugin configurations (Spotless, OpenAPI Generator, compiler processors) are long and change
rarely; dependencies change often. Mixing them makes both harder to read. A second module, such as
component tests, is likely.

## Decision

- The **parent pom** owns *how*: every version property, `<dependencyManagement>`, and every
  plugin's version and shared configuration in `<pluginManagement>`. Plugins that must run for
  every module and for root files (Spotless, flatten) are also listed in its `<plugins>`.
- **Module poms** own *what*: dependencies (without versions), plugin declarations (without
  versions or shared configuration), and module-specific details such as one OpenAPI Generator
  execution per contract.
- Modules never redeclare a version property or shared plugin setting from the parent.
- Lists a module extends, such as annotation processors or compiler arguments, use
  `combine.children="append"`.
- Every entry carries a comment naming where its version comes from.

## Consequences

- Dependency changes and configuration changes happen in different files.
- Adding a module is a new folder and one `<modules>` entry.
- The effective configuration is split across files; `mvn help:effective-pom -pl <module>` shows
  the merged result.
- A redeclared property in a module silently overrides the parent, so code review must watch for
  it.

## Alternatives considered

- **A single pom:** fewer files, but long plugin configuration sits between the dependencies, and
  adding a module later means moving every source path.


# 0012. IDE formatting through Spotless Applier

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Developers want files formatted on save, matching exactly what the build checks. The
google-java-format IntelliJ plugin bundles its own formatter version, which would have to be kept in
step with the pom on every machine.

## Decision

- The IntelliJ plugin **Spotless Applier** is the only formatting plugin. It runs `spotless:apply`
  through Maven for the saved file, so the IDE uses exactly the configuration and versions in the
  pom.
- It is declared in `.idea/externalDependencies.xml` (Required Plugins), which is committed.
- **Actions on Save:**
  - Run spotless: Java, Cucumber Scenario, XML, Markdown
  - Reformat code: YAML only
  - Optimize imports, Rearrange code, Run code cleanup: off
- `.editorconfig` holds editor rules shared by every IDE, including IntelliJ's YAML spacing
  (`ij_yaml_spaces_within_brackets = true`, `ij_yaml_spaces_within_braces = true`).
- `.idea/codeStyles` is committed with the Project scheme; EditorConfig support stays enabled.

## Consequences

- No formatter version to manage per developer.
- Each save pauses briefly while Maven runs.
- Actions on Save is a per-user setting, so it is documented in `CONTRIBUTING.md` rather than
  enforced. The build (0011) remains the guarantee.

## Alternatives considered

- **google-java-format plugin:** instant, but Java only and its version drifts from the pom.
- **Both plugins:** fastest for Java, but two version sources to maintain.


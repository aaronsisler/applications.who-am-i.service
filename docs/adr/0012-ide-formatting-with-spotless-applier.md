# 0012. IDE formatting through Spotless Applier

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-10 (import layout, settings paths, EditorConfig notice)

## Context

Developers want files formatted on save, matching exactly what the build checks. The
google-java-format IntelliJ plugin bundles its own formatter version, which would have to be kept in
step with the pom on every machine. IntelliJ's own import organizer orders imports differently from
google-java-format, which makes the import order flip back and forth.

## Decision

- The IntelliJ plugin **Spotless Applier** is the only formatting plugin. It runs `spotless:apply`
  through Maven for the saved file, so the IDE uses exactly the configuration and versions in the
  pom.
- It is declared under **Settings → Appearance & Behavior → Required Plugins**, which writes
  `.idea/externalDependencies.xml`; that file is committed.
- **Actions on Save** (Settings → Tools → Actions on Save):

|      Option      |                                  Setting                                  |
|------------------|---------------------------------------------------------------------------|
| Run spotless     | On, for file types **Java**, **Cucumber Scenario**, **XML**, **Markdown** |
| Reformat code    | On, for file type **YAML** only                                           |
| Optimize imports | Off                                                                       |
| Rearrange code   | Off                                                                       |
| Run code cleanup | Off                                                                       |

- **`.editorconfig`** holds the editor rules shared by every IDE, plus IntelliJ-specific keys that
  make IntelliJ's own behaviour match the formatters:

```ini
[*.java]
max_line_length = 100
ij_continuation_indent_size = 4
# google-java-format's import layout: static imports, a blank line, all other imports
ij_java_imports_layout = $*,|,*
# never collapse imports into wildcards (google-java-format forbids them)
ij_java_class_count_to_use_import_on_demand = 999
ij_java_names_count_to_use_import_on_demand = 999

[*.{yaml,yml}]
indent_size = 2
ij_yaml_spaces_within_brackets = true
ij_yaml_spaces_within_braces = true
```

- Because IntelliJ's import layout now matches, **Optimize imports on the fly** (Settings → Editor →
  General → Auto Import) may stay on.
- `.idea/codeStyles` is committed with the Project scheme. IntelliJ shows "Settings may be
  overridden by EditorConfig, Indents Detection" there: that is expected, and **Disable must not be
  clicked**, because it can switch EditorConfig off for the whole team.

## Consequences

- No formatter version to manage per developer.
- Each save pauses briefly while Maven runs.
- Actions on Save is a per-user setting, so it is documented in `CONTRIBUTING.md` rather than
  enforced. The build (0011) remains the guarantee.

## Alternatives considered

- **google-java-format plugin:** instant, but Java only and its version drifts from the pom.
- **Both plugins:** fastest for Java, but two version sources to maintain.
- **Turning off every IntelliJ import feature:** stops the flipping, but also loses automatic
  adding and removing of imports while typing.


## Code formatting

Formatting is automatic: you should never have to fix indentation or import order by hand.
The **build is the source of truth**. Every Maven build runs `spotless:check` in the `validate`
phase, so unformatted code fails within seconds, before anything compiles, locally and in the
pipeline alike. The IDE settings below just make sure your files are already correct when you
save them.

### Where each file type gets its formatting

|             File type             |         Formatted by         |           Rules come from            | Checked by the build |
|-----------------------------------|------------------------------|--------------------------------------|----------------------|
| Java (`.java`)                    | Spotless: google-java-format | `pom.xml` (pinned formatter version) | Yes                  |
| `pom.xml`                         | Spotless: sortPom            | `pom.xml`                            | Yes                  |
| Cucumber features (`.feature`)    | Spotless: Gherkin formatter  | `pom.xml`                            | Yes                  |
| YAML (`.yaml`, OpenAPI contracts) | IntelliJ formatter           | `.editorconfig`                      | Whitespace only      |
| Properties (`.properties`)        | Editor whitespace settings   | `.editorconfig`                      | Whitespace only      |
| Markdown (`.md`)                  | IntelliJ formatter           | `.idea/codeStyles`                   | No                   |
| SQL migrations (`db/migration`)   | **Nothing, ever**            | n/a                                  | No                   |

**Never reformat SQL migrations.** Flyway stores a checksum of every migration it has run.
Changing an applied migration, even just its whitespace, makes the application refuse to start
against any database where it already ran.

### IDE setup (IntelliJ)

The project declares **Spotless Applier** as a required plugin; accept IntelliJ's prompt to
install it. It runs the same Spotless configuration as the build, so there is no formatter
version to keep in sync on your machine.

Then, under **Settings → Tools → Actions on Save**:

|      Option      |                            Setting                             |
|------------------|----------------------------------------------------------------|
| Run spotless     | **On** for file types **Java**, **Cucumber Scenario**, **XML** |
| Reformat code    | **On** for file types **YAML** and **Markdown** only           |
| Optimize imports | **Off** (Spotless orders and removes imports)                  |
| Rearrange code   | **Off**                                                        |
| Run code cleanup | **Off**                                                        |

Under **Settings → Editor → General → On Save**: remove trailing spaces on all lines, and
ensure every saved file ends with a line break. `.editorconfig` overrides these per file type
where needed.

After a save, Spotless takes a moment while Maven runs; the file then reloads formatted.

### Commands

|       Command        |                                      What it does                                      |
|----------------------|----------------------------------------------------------------------------------------|
| `mvn spotless:apply` | Formats everything Spotless covers. Run this when the build reports unformatted files. |
| `mvn spotless:check` | Checks formatting without changing anything; this is what the pipeline runs.           |

If a commit is blocked or the build fails on formatting, run `mvn spotless:apply`, review the
diff, and commit again.

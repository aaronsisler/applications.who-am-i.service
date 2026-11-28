## Prerequisites

|  Tool  |                                    Version                                     |                 Install                 |
|--------|--------------------------------------------------------------------------------|-----------------------------------------|
| JDK    | Amazon Corretto 25 ([ADR 0020](./docs/adr/0020-amazon-corretto-as-the-jdk.md)) | `sdk install java 25.0.5-amzn` (SDKMAN) |
| Maven  | As required by the Maven Enforcer rule in the parent `pom.xml`                 | `sdk install maven`                     |
| Podman | Any recent version, for Testcontainers in local runs and tests                 | See the Podman docs for your OS         |

There is no Maven Wrapper. The build checks the Maven and Java versions with the Maven Enforcer
plugin and fails early with a clear message if either is too old. Use the current Corretto 25
patch release; `sdk list java | grep amzn` shows the available versions.

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
| Markdown (`.md`)                  | Spotless: flexmark           | `pom.xml`                            | Yes                  |
| YAML (`.yaml`, OpenAPI contracts) | IntelliJ formatter           | `.editorconfig`                      | Whitespace only      |
| Properties (`.properties`)        | Editor whitespace settings   | `.editorconfig`                      | Whitespace only      |
| SQL migrations (`db/migration`)   | **Nothing, ever**            | n/a                                  | No                   |

**Never reformat SQL migrations.** Flyway stores a checksum of every migration it has run.
Changing an applied migration, even just its whitespace, makes the application refuse to start
against any database where it already ran. Schema changes always go in a **new** migration.

### IDE setup (IntelliJ)

The project declares **Spotless Applier** as a required plugin; accept IntelliJ's prompt to
install it. It runs the same Spotless configuration as the build, so there is no formatter
version to keep in sync on your machine.

Then, under **Settings → Tools → Actions on Save**:

|      Option      |                                   Setting                                    |
|------------------|------------------------------------------------------------------------------|
| Run spotless     | **On** for file types **Java**, **Cucumber Scenario**, **XML**, **Markdown** |
| Reformat code    | **On** for file type **YAML** only                                           |
| Optimize imports | **Off** (Spotless orders and removes imports)                                |
| Rearrange code   | **Off**                                                                      |
| Run code cleanup | **Off**                                                                      |

Under **Settings → Editor → General → On Save**: remove trailing spaces on all lines, and
ensure every saved file ends with a line break. `.editorconfig` overrides these per file type
where needed.

After a save, Spotless takes a moment while Maven runs; the file then reloads formatted.

### Commands

|       Command        |                                      What it does                                      |
|----------------------|----------------------------------------------------------------------------------------|
| `mvn spotless:apply` | Formats everything Spotless covers. Run this when the build reports unformatted files. |
| `mvn spotless:check` | Checks formatting without changing anything; this is what the pipeline runs.           |

If the build fails on formatting, run `mvn spotless:apply`, review the diff, and commit again.

## Versioning and releases

We use [GitHub Flow](https://docs.github.com/en/get-started/using-github/github-flow): branch
from `main`, open a pull request, and merge when the checks pass. **Every merge to `main` is a
release.** The release workflow works out the next version, builds and tests it, tags the commit
`vX.Y.Z`, publishes a GitHub Release, and deploys.

### How the version is chosen

The workflow takes the latest `v*` tag and bumps it based on the PR title:

| PR title contains | Bump  |    Example    |
|-------------------|-------|---------------|
| nothing (default) | Patch | 0.5.0 → 0.5.1 |
| `#minor`          | Minor | 0.5.1 → 0.6.0 |
| `#major`          | Major | 0.6.0 → 1.0.0 |

While we're below 1.0, breaking API changes are a **minor** bump, as
[Semantic Versioning](https://semver.org/spec/v2.0.0.html#spec-item-4) allows for 0.x versions.
Use `#major` only when we deliberately declare 1.0 or later make a breaking change after it.

### What this means for you

- **Never edit the version in the pom.** The version is `${revision}`, set by the pipeline with
  `-Drevision=…` and written into the published pom by the flatten plugin. Local builds use the
  default `revision` from the parent pom.
- **Write the PR title as a release note.** It becomes the entry in the GitHub Release, e.g.
  "Add acceptance tests for App User creation #minor". Explain the why in the PR description,
  which the release links to.
- **Merge with "Squash and merge".** The squash commit's message is the PR title, which is where
  the workflow looks for `#minor` and `#major`. Rebase merging is turned off because it would
  lose the PR title.
- **Release notes live in [GitHub Releases](https://github.com/<owner>/<repo>/releases)**, not in
  `CHANGELOG.md`, which is kept only for history before v0.5.0.

## Architecture decisions

Significant decisions are recorded as ADRs in [`docs/adr`](./docs/adr). If a PR makes a decision
that someone would later ask "why did we do it this way?" about, add the next numbered ADR and a
line in the ADR index in the same PR.

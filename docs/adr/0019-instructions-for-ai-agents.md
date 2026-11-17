# 0019. Instructions for AI agents

- **Status:** Accepted
- **Date:** 2026-11-14

## Context

AI coding agents work on this repository. An agent knows nothing between sessions: it only knows
what is loaded into its context at the start, plus what it reads while working. Everything loaded at
startup costs space in every session and dilutes the instructions that matter. The ADR folder will
keep growing.

## Decision

- **`AGENTS.md`** at the repository root holds the few instructions every task needs: what the
  service is, where the ADRs and `CONTRIBUTING.md` are, and the commands that must pass. It is the
  cross-tool convention; Claude Code reads it when no `CLAUDE.md` exists.
- **`.claude/rules/*.md`** hold short, imperative rules per file type, scoped with `paths`
  frontmatter, so each loads only when an agent works on matching files.
- **Rules state the "what"; ADRs keep the "why".** Every rule names the ADR it comes from, and an
  agent reads that record only when it needs the reasoning.
- **ADRs are never imported** into instruction files (`@path`). They are read on demand.
- **An ADR that changes how code is written updates the matching rule file in the same pull
  request.**

|    Rule file    |               Applies to                |
|-----------------|-----------------------------------------|
| `java.md`       | `**/*.java`                             |
| `maven.md`      | `**/pom.xml`                            |
| `contracts.md`  | `**/contracts/**/*.yaml`                |
| `cucumber.md`   | `**/*.feature`, `**/src/test/**/*.java` |
| `migrations.md` | `**/db/migration/*.sql`                 |
| `adr.md`        | `docs/adr/**`                           |

## Consequences

- The ADR folder can grow without costing context in every session.
- Rules and records can drift apart if the same-pull-request rule is skipped; review must check it.
- Rules are Claude Code specific. Another tool needs its own path-scoped files, which should point
  to the ADRs rather than restate them.

## Alternatives considered

- **One long instruction file:** everything loads every session, relevant or not.
- **Importing the ADRs:** complete, but the whole folder enters every session's context.
- **No instructions:** agents rediscover conventions by reading code, inconsistently.


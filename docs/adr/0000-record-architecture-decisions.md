# 0000. Record architecture decisions

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Decisions about structure, tooling and conventions are made in conversations and pull requests,
then forgotten. Months later nobody remembers why something is the way it is, until a change
breaks it, or someone has to dig the reasoning out of an old comment.

## Decision

We record every significant decision as a short Markdown file in `docs/adr/`, one decision per
file, numbered in order. Each record states the context, the decision, its consequences and the
alternatives considered.

A decision is significant if a reasonable colleague might later ask "why did we do it this way?"

## Consequences

- The reasoning lives next to the code, is reviewed in pull requests, and is formatted by the build
  like every other Markdown file.
- Records are never rewritten to reverse a decision. A new record supersedes the old one, so the
  history of our thinking stays readable.
- Writing a record takes a few minutes; skipping it costs far more the first time someone has to
  reconstruct the reasoning.


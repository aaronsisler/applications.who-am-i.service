# 0006. Contract-first APIs with OpenAPI

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Hand-written request and response classes let the public API change by accident: renaming a field
in code silently breaks clients. Other modules and test suites also need the same API shapes.

## Decision

- Each module's HTTP API is defined first in an **OpenAPI 3.0.3** contract in
  `src/main/resources/openapi/`. Shared schemas (problems, resource metadata) live in a `common`
  folder and are referenced with relative `$ref` paths.
- Contracts are written in **YAML**. Values (examples, defaults, versions, response codes, human
  text) are double-quoted; keywords and identifiers are not.
- The **OpenAPI Generator** Maven plugin generates the API code into `target/generated-sources`.
  Generated code is never edited or committed, and is only used inside the module's web adapter
  (enforced by ArchUnit).
- Schema names describe the API, not the implementation: `CreateAppUserRequest`,
  `AppUserResponse`, no `Dto` suffix.
- We stay on OpenAPI 3.0.3 until the generator's 3.1 support is complete.

## Consequences

- The contract is the reviewable artifact; changes to the public API are visible in pull requests.
- The domain, commands and generated models describe similar data. This is deliberate: the API
  shape, the use case input and the business rules change for different reasons.
- Validation constraints in the contract must also be implemented where the rules live, and the two
  must be kept consistent.
- Generated models use `OffsetDateTime` for `date-time` fields; adapters convert to `Instant`
  (see 0010).

## Alternatives considered

- **Code-first (springdoc):** less ceremony, but the contract follows the code, including
  accidental changes.
- **JSON contracts:** no indentation issues, but no comments and noisier diffs.
- **Contracts in a root-level folder:** equally valid; we keep them with the service because only
  this repository generates from them.


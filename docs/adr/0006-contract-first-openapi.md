# 0006. Contract-first APIs with OpenAPI

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-10 (contract location, generation details, validation, Swagger UI)

## Context

Hand-written request and response classes let the public API change by accident: renaming a field
in code silently breaks clients. Other modules and test suites also need the same API shapes.

## Decision

### Contracts

- Each module's HTTP API is defined first in an **OpenAPI 3.0.3** contract in
  `src/main/resources/contracts/inbound/` (for example `app-user.yaml`). Shared schemas (problems,
  resource metadata, page metadata) live in `contracts/inbound/common/` and are referenced with
  relative `$ref` paths.
- Contracts are written in **YAML**. Values (examples, defaults, versions, response codes, human
  text) are double-quoted; keywords and identifiers are not. Flow lists use spaced brackets
  (`[ id, name ]`), matching `.editorconfig`.
- Schema names describe the API, not the implementation: `CreateAppUserRequest`,
  `AppUserResponse`, no `Dto` suffix.
- We stay on OpenAPI 3.0.3 until the generator's 3.1 support is complete.

### Generation

- The **OpenAPI Generator** Maven plugin generates code into `target/`. Generated code is never
  edited or committed, and is only used inside the module's web adapter (enforced by ArchUnit).
- Shared generator settings live in the parent pom; each module adds **one execution per contract**,
  naming only the contract and its packages (`${generated-base.package}.<module>.adapter.in.web
  .generated.model`).
- Schemas from `common/` are generated **into each module's own package** as part of that module's
  execution. The YAML is shared, not the Java, so no module depends on another's generated classes.
- **Only models are generated** (`generateApis=false`). Controllers are written by hand.
- Generated models carry **Bean Validation** annotations from the contract (`useBeanValidation`)
  and a Lombok `@Builder`.
- The **Actuator contract** is generated into *test* sources only: Spring implements those
  endpoints, and only tests need the models.

### Validation

Validation happens at two levels, for different reasons:

- **At the edge:** `@Valid` on generated request models rejects malformed requests with a 400 and
  `violations` (see 0007), using the constraints written in the contract.
- **In the domain:** records enforce the business rules, whatever the entry point (HTTP, events,
  tests). The domain never relies on the edge having validated.

The contract's constraints and the domain's rules must be kept consistent.

### Documentation

- **Swagger UI shows the contract**, not a specification inferred from the code. The `web` module
  serves `src/main/resources/contracts/` at the URL `/contracts/**`, and springdoc lists:
  1. the contract (selected by default), and
  2. springdoc's generated `/v3/api-docs`, for comparing the implementation with the contract.

## Consequences

- The contract is the reviewable artifact; changes to the public API are visible in pull requests.
- The domain, commands and generated models describe similar data. This is deliberate: the API
  shape, the use case input and the business rules change for different reasons.
- Generated models use `OffsetDateTime` for `date-time` fields; adapters convert to `Instant`
  (see 0010).
- Because controllers are hand-written, nothing at compile time checks that their paths, methods
  and parameters match the contract. API scenarios cover this, and the second Swagger UI entry
  makes differences visible.
- Each module gets its own copies of the common models (`PageMetadata`, `Problem`, `Violation`).
  `Problem` and `Violation` are not used at runtime: error responses are built with Spring's
  `ProblemDetail` (see 0007).

## Alternatives considered

- **Code-first (springdoc):** less ceremony, but the contract follows the code, including
  accidental changes.
- **Generating API interfaces (`generateApis=true`, `interfaceOnly`):** controllers implement
  them, so the compiler catches drift in paths, methods and parameters. A stronger guarantee; a
  one-line change in the parent pom if drift becomes a problem.
- **Validation only in the domain:** a single place for rules, but malformed requests reach the
  core and clients get less precise errors.
- **JSON contracts:** no indentation issues, but no comments and noisier diffs.
- **Contracts in a root-level folder:** equally valid; they live with the service because only this
  repository generates from them.
- **One execution for all contracts (`inputSpecRootDirectory`):** merges every contract into one
  package, breaking module boundaries.


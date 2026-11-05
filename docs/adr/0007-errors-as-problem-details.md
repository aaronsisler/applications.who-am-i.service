# 0007. Errors as Problem Details

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

A custom error format duplicates what the HTTP status already says, and drifts from what Spring
actually produces. Clients still need a machine-readable reason for specific failures.

## Decision

- Errors are **RFC 9457 Problem Details** (`application/problem+json`), produced with Spring's
  `ProblemDetail`.
- Two extension members:
  - **`code`**: a machine-readable reason for cases the status can't express, such as
    `EMAIL_ADDRESS_TAKEN`. Each API documents the codes its operations return.
  - **`violations`**: present only for validation failures (400), each with `field`, `code` and
    `message`.
- Codes that repeat the status (`NOT_FOUND`, `METHOD_NOT_ALLOWED`, ...) are not used.
- Field-level codes describe the problem from the client's side (`REQUIRED`, `TOO_LONG`,
  `TOO_SHORT`, `OUT_OF_RANGE`, `INVALID_FORMAT`), not the validation technology.
- `code` is a string, not a shared enum (see 0008).
- Reusable responses (`InvalidRequest`, `NotFound`) are referenced from every contract.

## Consequences

- The contract documents exactly what Spring sends.
- Modules add their own codes without editing a shared file.
- Error `detail` texts must not contain personal data, such as email addresses.

## Alternatives considered

- **A custom `ErrorDto` with a global `ErrorCode` enum:** every module edits one shared file, and
  adding a code breaks clients that deserialize strictly.


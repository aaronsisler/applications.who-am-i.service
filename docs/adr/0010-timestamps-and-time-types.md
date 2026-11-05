# 0010. Timestamps and time types

- **Status:** Proposed
- **Date:** 2026-11-03

## Context

Resources expose `createdAt` and `updatedAt`. We need to decide who sets them, where they live in
the code, and which Java types represent them. JDBC and generated API models use `OffsetDateTime`,
which compares unequal for the same instant with different offsets.

## Decision

- **Types:** `Instant` in the domain and application layers. `OffsetDateTime` only in adapters
  (JDBC and generated models), converted with `instant.atOffset(ZoneOffset.UTC)` and
  `toInstant()`. Never `LocalDateTime` for moments in time. Postgres columns are `timestamptz`.
- **Audit timestamps are not part of the domain objects.** Use cases return a read model (for
  example `AppUserView`) that carries them.
- **The application sets them** from an injected `java.time.Clock`, truncated to microseconds to
  match what Postgres stores. `created_at` is written only on insert; `updated_at` on every write.
- **Business time** (for example "listed at") belongs in the domain under a business name, also set
  from the `Clock`.

## Consequences

- Scenarios can control time ("Given the time is ..."), and fakes and real adapters receive the
  same values.
- Anything writing to the tables outside the application must set the timestamps itself.
- `updatedAt` is not used for concurrency control; a `version` column would be.

## Alternatives considered

- **Database defaults and triggers:** one clock and impossible to forget, even for scripts and
  other writers, but time cannot be controlled in tests. Prefer this if anything other than this
  service writes these tables.
- **Timestamps on the domain objects:** simpler, but objects are "maybe saved" until stored, and
  every domain operation must carry values it doesn't decide.


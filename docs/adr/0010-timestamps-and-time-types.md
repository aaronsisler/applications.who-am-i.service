# 0010. Timestamps and time types

- **Status:** Accepted
- **Date:** 2026-11-10

## Context

Resources expose `createdAt` and `updatedAt`. We need to decide who sets them, where they live in
the code, and which Java types represent them.

Two options exist for setting them: the database (column defaults, plus a trigger for `updated_at`,
since Postgres has no "on update") or the application. Tests need exact, repeatable values, and only
this service writes its tables.

JDBC and the generated API models use `OffsetDateTime`, which compares unequal for the same moment
with different offsets.

## Decision

### Who sets them

- **The application sets every timestamp**, from a `java.time.Clock` injected into the application
  services:

  ```java
  Instant now = clock.instant().truncatedTo(ChronoUnit.MICROS);
  ```
- Production configuration provides `Clock.systemUTC()`. Test configuration provides a
  `TestClock` that scenarios can set and move forward ("Given the time is ...").
- Values are **truncated to microseconds**, the precision Postgres stores, so a value read back
  equals the value written.
- `created_at` is written only on insert; `updated_at` on every write.
- Timestamp columns are `timestamptz NOT NULL` **without defaults**, so a write that forgets them
  fails immediately instead of storing the database's time.

### Where they live

- Audit timestamps are **not part of domain objects.** Use cases return a read model (for example
  `AppUserView`) that carries them.
- **Business time** (for example "listed at") belongs in the domain under a business name, also
  set from the `Clock`.

### Types

|                 Where                 |                                     Type                                      |
|---------------------------------------|-------------------------------------------------------------------------------|
| Domain and application layers         | `Instant`                                                                     |
| JDBC adapter and generated API models | `OffsetDateTime`, converted with `atOffset(ZoneOffset.UTC)` and `toInstant()` |
| Postgres                              | `timestamptz`                                                                 |
| Never, for moments in time            | `LocalDateTime`                                                               |

## Consequences

- Scenarios control time and assert exact values, such as "an update moves `updatedAt` but never
  `createdAt`".
- In-memory fakes and the real adapter receive identical values from the application.
- Anything writing to the tables outside the service must set the timestamps itself: test steps
  inserting rows directly, and Flyway migrations that add data (using `now()`).
- `updatedAt` is not used for concurrency control; a `version` column would be.
- Several application instances may differ by milliseconds; irrelevant for audit timestamps.

## Alternatives considered

- **Database defaults and triggers:** one clock and impossible to forget, even for scripts and
  other writers, but tests cannot control time. Prefer this if other systems ever write these
  tables.
- **Timestamps on the domain objects:** simpler, but objects are "maybe saved" until stored, and
  every domain operation must carry values it doesn't decide.
- **`Instant.now()` called directly:** no injection needed, but no test can control the result.


# 0014. Time-ordered UUIDs as identifiers

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Earlier schemas used two keys per table: a database-generated `bigint` identity as the primary key
and a UUID as the public identifier. We want one identifier per resource that indexes well, is
safe to expose, and exists before the record is stored.

Random UUIDs (version 4) insert at random positions in an index, which fragments it as tables grow.
Time-ordered UUIDs (version 7, RFC 9562) start with a millisecond timestamp, so new values append to
the end of the index like a sequence, while the remaining random bits keep them unguessable.

## Decision

- Every resource has **exactly one identifier**: a **UUIDv7**, used as the primary key and exposed
  in the API as `id`.
- The **application generates it** in the domain (for example `AppUserId.newId()`), using the
  `com.github.f4b6a3:uuid-creator` library, because the JDK has no UUIDv7 generator.
- **No internal-only keys.** Tables have no separate `bigint` key alongside the UUID.
- **Exception:** when a resource's creation time must not be inferable and is not otherwise
  exposed, use a random **UUIDv4** as its single identifier instead.

## Consequences

- The id exists before the insert: domain objects are complete when created, events can carry the
  id, and retried requests can reuse it.
- One key per table: no mapping between internal and external ids, and the id in logs and support
  tickets is the id in the database.
- Anyone can decode a UUIDv7's creation time. That is acceptable for resources whose `createdAt` is
  already in the API, which is why the exception above exists.
- Keys are 16 bytes instead of 8, so indexes and foreign keys are wider than with `bigint`;
  irrelevant at our scale.
- Ids from different instances within the same millisecond are not strictly ordered. Never sort by
  id to get creation order; use `created_at`.
- Scenarios that assert exact ids must create them explicitly rather than through `newId()`.

## Alternatives considered

- **`bigint` identity primary key plus a public UUID:** sound and compact, but two keys per table,
  and the id only exists after the insert.
- **UUIDv7 primary key plus a UUIDv4 public id:** hides the creation time, but the public id still
  needs a unique index with random inserts, which is the index most API reads use. It also doubles
  columns and indexes for information `createdAt` already exposes.
- **Database-generated `uuidv7()` (Postgres 18):** no library needed, but the id would only exist
  after the insert.
- **ULID or TSID:** same idea, but ULID is usually stored as text and TSID's 64 bits need node ids
  per instance to avoid collisions. UUIDv7 is the standard and fits the native `uuid` type.


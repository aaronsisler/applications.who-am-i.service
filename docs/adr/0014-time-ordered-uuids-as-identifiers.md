# 0014. Time-ordered UUIDs as identifiers

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-04 (generator library)

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
- The **application generates it** in the domain, because the JDK has no UUIDv7 generator. We use
  **Java UUID Generator** (`com.fasterxml.uuid:java-uuid-generator`), whose time-based epoch
  generator produces RFC 9562 version 7 UUIDs:

  ```java
  public record AppUserId(UUID value) {

    private static final TimeBasedEpochGenerator GENERATOR = Generators.timeBasedEpochGenerator();

    public static AppUserId newId() {
      return new AppUserId(GENERATOR.generate());
    }
  }
  ```

  The generator is thread-safe and created once, not per call. The library is used only at the
  moment an id is created; everything else handles a plain `java.util.UUID`.

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
- The domain imports `com.fasterxml.uuid`. The ArchUnit rule forbidding Jackson in the domain
  matches `com.fasterxml.jackson..` only; broadening it to `com.fasterxml..` would block this
  library too.

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
- **uuid-creator (`com.github.f4b6a3:uuid-creator`):** equally capable. Java UUID Generator was
  chosen as the more widely used of the two; either would do.


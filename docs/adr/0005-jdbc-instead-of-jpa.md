# 0005. Spring JDBC instead of JPA

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

JPA's main benefits (managed entities, dirty checking, lazy loading) are largely unused in a
hexagonal design, because persistence adapters map rows to domain objects at the boundary. Its
costs remain: generated SQL that is hard to see, N+1 queries, flush timing surprises, and
`merge` issuing a SELECT before every INSERT for application-assigned ids.

## Decision

- Persistence adapters use **`JdbcClient`** with explicit SQL.
- Saves are a single upsert statement (`insert ... on conflict ... do update`).
- The schema is owned by **Flyway** migrations in `src/main/resources/db/migration`.
- Spring Modulith's event publication registry uses its JDBC implementation.

## Consequences

- The SQL you read is the SQL that runs; performance problems are visible in the code.
- Row mapping is written by hand, which is trivial for flat tables but grows with complex graphs.
- Applied migrations must never change, not even their whitespace, because Flyway verifies their
  checksums (see 0011).
- The storage contract (0003) proves each JDBC adapter behaves like its in-memory fake.

## Alternatives considered

- **JPA / Hibernate:** worth it for rich, deeply related object graphs and teams fluent in its
  pitfalls; neither applies here.
- **Spring Data JDBC:** repositories without sessions or lazy loading; a reasonable middle ground,
  but assigned ids need extra handling and the SQL is less explicit.


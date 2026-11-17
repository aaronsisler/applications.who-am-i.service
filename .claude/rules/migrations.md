---

paths:
- "**/db/migration/*.sql"
-------------------------

# Flyway migrations

- Never modify a migration that may have run anywhere; add a new one instead.
- Never reformat existing migrations, not even whitespace: Flyway checksums them.
- Timestamp columns are `timestamptz NOT NULL` without defaults (ADR 0010).


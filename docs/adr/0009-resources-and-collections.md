# 0009. Resources and collections

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Every API needs the same answers: how resources are identified, how lists are returned, how search
works, and which status codes mean what.

## Decision

- Stored resources share **`AuditedResource`**: `id` (UUID), `createdAt`, `updatedAt`, all
  read-only. The identifier is called `id`, never `externalId`; internal keys never appear in the
  API.
- **Collections are objects, not bare arrays:** `{ "items": [...], "page": {...} }`, with shared
  `PageMetadata` (`number`, `size`, `totalElements`, `totalPages`). This leaves room for later
  fields such as links.
- **One collection endpoint for list and search:** `GET /app-users` without filters lists
  everything; query parameters narrow it.
- **Status codes:**
  - A search or list returns **200**, even with no matches. The empty page is the result, and
    clients always parse the same shape.
  - Creating returns **201** with a `Location` header.
  - Actions with nothing to return, such as `DELETE`, return **204**.
- **Offset pagination** (`page`, `size`, with a maximum size) for now. `sort` is a request enum.
- Update is a `PUT` that replaces all editable fields.

## Consequences

- Clients handle every list the same way.
- Totals cost a second (`count`) query; revisit if listing becomes slow.
- Sort values are mapped to fixed SQL clauses through a whitelist; request values are never
  inserted into SQL.
- If tables grow very large or clients page through changing data, move to cursor pagination.

## Alternatives considered

- **204 or 404 for empty searches:** forces clients to special-case "no results".
- **Separate list and search endpoints:** two ways to do the same thing.


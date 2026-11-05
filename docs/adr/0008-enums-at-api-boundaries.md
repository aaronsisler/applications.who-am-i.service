# 0008. Enums at API boundaries

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Adding a value to an enum can break callers, depending on which direction the value travels.
Clients that deserialize into a closed enum fail on a value they don't know.

## Decision

- **Values clients send** (request bodies, query parameters): use a real `enum`. Adding values later
  is backward compatible; unknown values are rejected with 400 during deserialization.
- **Values the service returns**, including fields used in both directions: use `type: string` with
  the current values listed in `x-extensible-enum` and a description stating that clients must
  accept unknown values. Generated code then uses `String`.
- When moving to OpenAPI 3.1, `x-extensible-enum` becomes the standard `examples` keyword.
- **Inside the application**, enums are encouraged: switch expressions without `default` make the
  compiler flag every place a new value isn't handled. API enums and domain enums are separate and
  mapped in the adapter.
- Enums are stored by **name**, never by ordinal.

## Consequences

- New response values can be introduced without coordinated client releases.
- Clients must implement fallback behaviour for unknown values.
- Generated clients we write ourselves should use OpenAPI Generator's `enumUnknownDefaultCase=true`.

## Alternatives considered

- **Closed enums everywhere:** precise documentation, but every new response value is a breaking
  change.
- **Plain strings everywhere:** loses free validation of request values.


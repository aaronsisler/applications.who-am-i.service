# 0003. Test strategy by seam

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

In a hexagonal design the risky places are the seams: where an adapter calls a port, where a use
case calls a port, and where Spring wires them together. We need to know, for each seam, which test
proves it works.

## Decision

|              Seam              |                                                 Proven by                                                  |
|--------------------------------|------------------------------------------------------------------------------------------------------------|
| Domain rules                   | Domain scenarios, no Spring, no fakes                                                                      |
| Inbound adapter → inbound port | Web slice (`@WebMvcTest`) with the use case mocked; the interaction *is* the behaviour                     |
| Use case → outbound port       | Acceptance scenarios against in-memory fakes, asserting on **state**, not on calls                         |
| Fake ↔ real outbound adapter   | One storage contract, run against both the fake and the real JDBC adapter on Postgres                      |
| Spring wiring                  | Wiring scenarios on the full application: the right beans, transactional proxies, data reaching the tables |
| Everything together            | A few `@e2e` acceptance scenarios over HTTP against the real application and Postgres                      |

Rules that go with it:

- Prefer **state-based assertions** with working fakes over `verify(...)`. Use mocks or recording
  fakes only where the call itself is the outcome (inbound adapters, event publishing).
- Fakes live in test sources and are never Spring components, so they cannot be wired into
  production.
- Acceptance steps talk to a **driver** interface. The same feature file runs against the core
  (fast, in-memory) and, for `@e2e` scenarios, over HTTP.
- Wiring tests verify by reading the **tables directly**, because reading back through HTTP would
  also pass if the application were accidentally using a fake.

## Consequences

- Most scenarios run in milliseconds; only a handful need Docker.
- The storage contract is what makes the fast tests trustworthy: if a fake drifts from the real
  adapter, the contract fails.
- Few Spring contexts are built (plain, core, web slice, full application, module test), so the
  suite starts quickly. Keep `@MockitoBean` sets consistent and avoid `@DirtiesContext` to keep it
  that way.

## Alternatives considered

- **Mock everything with `verify`:** fast, but tests pass when behaviour is wrong and break on every
  refactoring.
- **Only end-to-end tests:** high confidence, slow, and failures don't point at the broken seam.


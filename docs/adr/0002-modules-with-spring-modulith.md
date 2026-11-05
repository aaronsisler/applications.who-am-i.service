# 0002. Modules with Spring Modulith

- **Status:** Accepted
- **Date:** 2026-11-03

## Context

Organising code by technical layer (`controllers/`, `services/`, `repositories/`) spreads every
feature across the codebase and lets any class use any other. We want features that are cohesive
and whose boundaries are checked automatically, without the cost of separate services.

## Decision

- Every package directly below the application's root package is a **module** (for example
  `appuser`). Inside, each module is a hexagon (0001).
- **Spring Modulith** verifies module boundaries in the build: a module may only use what another
  module explicitly exposes through a named interface (`@NamedInterface`), only the dependencies
  declared in `@ApplicationModule(allowedDependencies = ...)`, and no cycles.
- **ArchUnit** enforces the hexagon rules *inside* each module; Modulith enforces the rules
  *between* modules.
- Modules communicate through **events** published in an `api` package with plain values (UUID,
  String), never domain types. Listeners use `@ApplicationModuleListener` and are idempotent,
  because the event publication registry can redeliver an event.
- Each module owns its tables. There are no foreign keys between modules' tables.

## Consequences

- Module boundaries are decided up front in `package-info.java` and fail the build when crossed,
  instead of eroding silently.
- A module can be tested on its own with `@ApplicationModuleTest`, by publishing the events it
  reacts to.
- Event listeners run asynchronously after the publishing transaction commits, so tests that cross
  modules must wait for the result.
- A module can later be extracted into its own service with little change, because it already
  communicates only through events and owns its data.

## Alternatives considered

- **Packages by layer:** no cohesion, no boundaries.
- **Microservices from the start:** network calls, deployments and data consistency problems before
  we know where the real boundaries are.
- **ArchUnit slice rules only:** possible, but Modulith adds module tests, event infrastructure and
  generated documentation.


# 0002. Modules with Spring Modulith

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-10 (infrastructure modules, no general "shared" module)

## Context

Organising code by technical layer (`controllers/`, `services/`, `repositories/`) spreads every
feature across the codebase and lets any class use any other. We want features that are cohesive
and whose boundaries are checked automatically, without the cost of separate services.

Some code is needed by every module but belongs to none of them, such as web error handling and
common type conversions. A general "shared" or "common" module tends to become a dumping ground
that every module depends on.

## Decision

### Business modules

- Every package directly below the application's root package is a **module** (for example
  `appuser`). Inside, each business module is a hexagon (0001).
- Each module declares itself in its top-level `package-info.java` with `@ApplicationModule`.
- **Spring Modulith** verifies module boundaries in the build: a module may only use what another
  module explicitly exposes through a named interface (`@NamedInterface` on a package's
  `package-info.java`), only the dependencies declared in
  `@ApplicationModule(allowedDependencies = ...)`, and no cycles.
- **ArchUnit** enforces the hexagon rules *inside* each module; Modulith enforces the rules
  *between* modules.
- Modules communicate through **events** published in an `api` package with plain values (UUID,
  String), never domain types. Listeners use `@ApplicationModuleListener` and are idempotent,
  because the event publication registry can redeliver an event.
- Each module owns its tables. There are no foreign keys between modules' tables.

### Infrastructure modules

- Code that every module needs but no module owns goes into an **infrastructure module named after
  its one purpose**. The first is **`web`**: everything that helps the application talk HTTP.

```
web
├─ contracts/   serves the OpenAPI contracts for Swagger UI      (internal)
├─ cors/        cross-origin settings (0018)                     (internal)
├─ mapping/     conversions shared by web mappers (0015)         (named interface "mapping")
└─ problem/     global error handling (0007)                     (internal)
```

- Infrastructure modules declare **`allowedDependencies = {}`**: they may not depend on any other
  module, so business concepts cannot enter them without failing `modules.verify()`.
- Only the packages other modules genuinely use are named interfaces; everything else stays
  internal and is found by Spring's component scanning.
- **There is no general "shared", "common", "core" or "util" module.** When a new kind of plumbing
  appears (messaging, for example), it gets its own small module with its own name.

## Consequences

- Module boundaries are decided up front in `package-info.java` and fail the build when crossed,
  instead of eroding silently.
- A module can be tested on its own with `@ApplicationModuleTest`, by publishing the events it
  reacts to.
- Event listeners run asynchronously after the publishing transaction commits, so tests that cross
  modules must wait for the result.
- A module can later be extracted into its own service with little change, because it already
  communicates only through events and owns its data.
- "Does this help a web adapter talk HTTP?" decides whether code belongs in `web`; anything that
  needs a business type cannot compile into it.

## Alternatives considered

- **Packages by layer:** no cohesion, no boundaries.
- **Microservices from the start:** network calls, deployments and data consistency problems before
  we know where the real boundaries are.
- **ArchUnit slice rules only:** possible, but Modulith adds module tests, event infrastructure and
  generated documentation.
- **An open "shared" module (`Type.OPEN`):** every module may reach everything inside it, which is
  exactly how such modules become dumping grounds.


# 0001. Hexagonal architecture

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-10 (domain objects as records, where Lombok and mapping belong)

## Context

The service needs business rules that stay stable while technology around them changes: the web
framework, the database access library, the way other systems call us. We also want to test those
rules quickly, without starting a web server or a database.

## Decision

Each module is a hexagon (ports and adapters):

- **`domain`**: business objects and rules. Plain Java, no framework code.
- **`application`**: use cases. Inbound ports (`port.in`) define what the outside world may ask;
  outbound ports (`port.out`) define what the use cases need from the outside world. Services
  implement the inbound ports. Plain Java, no framework code.
- **`adapter.in.*`**: translate the outside world into inbound port calls (REST controllers, event
  listeners).
- **`adapter.out.*`**: implement outbound ports with real technology (JDBC, event publishing).
- **`config`**: the only place where Spring wires services to adapters, including making use cases
  transactional.

Adapters depend on ports, never on service implementations. Inbound and outbound adapters never
depend on each other. These rules are enforced by ArchUnit (see the architecture features).

### Domain objects

- Domain objects, value objects, commands and read models are **Java records**. Their compact
  constructors enforce invariants, so an invalid object cannot exist.
- **No Lombok in the domain or application layers.** Setters would let any code change state
  without going through the rules, and records already provide constructors, accessors, `equals`
  and `toString`.
- Domain objects carry **no persistence concerns**: no mapping annotations, no database-only keys,
  no audit timestamps (see 0010 and 0014).
- Lombok is acceptable in adapters and is used on the generated API models (`@lombok.Builder`).

### Mapping

Each adapter translates between its outside shape and the core's shapes; the shapes never meet:

|   Adapter   |          From           |             To             |           How           |
|-------------|-------------------------|----------------------------|-------------------------|
| Web (in)    | generated request model | command                    | MapStruct (0015)        |
| Web (out)   | read model              | generated response model   | MapStruct (0015)        |
| Persistence | database row            | domain object / read model | `JdbcClient` row mapper |

## Consequences

- Business rules can be tested without Spring, and adapters can be swapped (we replaced JPA with
  JDBC by changing only the persistence adapters).
- More types than a layered CRUD app: a request model, a command, a domain object and a stored row
  can all describe the same thing. This duplication is deliberate: each changes for a different
  reason (see 0006).
- Transactions are applied in `config` with a decorator around each use case, so the application
  layer stays free of Spring annotations.

## Alternatives considered

- **Classic layered architecture (controller, service, repository):** less code, but services
  depend directly on Spring and persistence types, and tests need the framework.
- **One class for entity, domain object and API shape** (as in the first version of this service):
  less mapping, but persistence annotations, database keys and mutable setters leak into the
  business rules.
- **No separation for simple CRUD:** fine for throwaway code; this service is expected to grow.


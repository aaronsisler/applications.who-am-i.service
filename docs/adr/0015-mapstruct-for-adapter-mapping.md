# 0015. MapStruct for adapter mapping, with strict policies

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-10 (where shared conversions live)

## Context

Adapters translate between the outside world's shapes and the core's: generated API models to
commands, read models to generated API models. Hand-written mapping is easy to get subtly wrong:
arguments of the same type can be swapped without a compile error (`firstName` into `lastName`),
and builders silently leave unset fields null.

MapStruct generates the mapping code at compile time and maps by property name. Its default
settings, however, only warn about unmapped target properties and silently ignore unmapped source
properties, so a new request field can be dropped without anyone noticing.

## Decision

- Web adapters use **MapStruct** to map between generated API models and the core's commands and
  read models.
- **Strict policies apply to every mapper**, set once as annotation processor options in the
  service's `pom.xml`:
  - `unmappedTargetPolicy=ERROR`: every target property must be mapped or explicitly ignored.
  - `unmappedSourcePolicy=ERROR`: every source property must be used or explicitly ignored.
  - `defaultComponentModel=spring` and `defaultInjectionStrategy=constructor`.
- Mappers are annotated with a plain `@Mapper`. A mapper may relax a policy only with an explicit
  `@Mapping(target = ..., ignore = true)` or `@BeanMapping(ignoreUnmappedSourceProperties = ...)`,
  which documents the exception where it happens.
- **Conversions shared by every module** (`Instant` ↔ `OffsetDateTime`) are `default` methods in
  `CommonWebMappings`, in the `web` module's `mapping` package, its only named interface (0002).
  Mappers extend it:

  ```java
  @Mapper
  interface AppUserWebMapper extends CommonWebMappings {
    CreateAppUserCommand toCommand(CreateAppUserRequest request);
  }
  ```
- **Module-specific conversions** (value objects such as `EmailAddress`) are `default` methods in
  that module's own mapper.
- **MapStruct is used only in adapters.** The persistence adapter maps rows by hand with
  `JdbcClient` row mappers, which MapStruct cannot help with. The domain and application layers
  never use it; `org.mapstruct` is on ArchUnit's list of forbidden packages for those layers.

## Consequences

- Adding a field to a contract fails the build until it is mapped, in both directions.
- Swapped fields of the same type are impossible for name-matched properties.
- Generated mappers are plain Java in `target/generated-sources/annotations` and can be read and
  debugged like any other class.
- Lombok and MapStruct must run in the right order: the compiler's processor list contains Lombok,
  MapStruct and `lombok-mapstruct-binding`.
- The policies live in the `pom.xml`, not in the mapper code; this record and a comment in the pom
  are where readers find them.

## Alternatives considered

- **Hand-written mapping:** fully explicit, and records catch missing constructor arguments, but
  same-type arguments can be swapped and builders can leave fields unset without a compile error.
- **A shared `@MapperConfig` referenced by every mapper:** keeps the policies visible in code, but
  every mapper must remember `config = ...`; forgetting it silently falls back to lenient defaults.
- **An abstract base mapper carrying the settings:** not possible; MapStruct reads `@Mapper`
  settings only from the mapper itself. Inheritance works for `default` conversion methods only.
- **MapStruct's default policies:** less friction, but dropped source fields go unnoticed.


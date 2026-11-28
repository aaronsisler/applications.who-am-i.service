# 0021. Generate API interfaces from the contracts

- **Status:** Accepted
- **Date:** 2026-11-21

## Context

ADR 0006 generated only models and left controllers hand-written, so nothing at compile time
checked that a controller's paths, methods, parameters and response types matched the contract.
Drift was only caught by API scenarios. The generator can also produce one Java interface per tag,
carrying the mappings and the contract's validation constraints.

## Decision

- Generate API interfaces (`generateApis=true`) with `interfaceOnly=true` and
  `skipDefaultInterface=true`. Each module's controller implements its interface; the compiler
  requires every operation.
- Controllers carry only `@RestController`. Mappings, parameter binding and validation come from
  the interface; they are not repeated on the implementation.
- Generate with `useSpringBuiltInValidation=true`, and never annotate controllers with
  `@Validated`. With `@Validated`, Spring MVC skips its built-in method validation and a proxy
  validates instead, throwing `ConstraintViolationException`, which `GlobalProblemHandler` does
  not handle: invalid parameters would return 500 instead of 400. An architecture scenario enforces
  this.
- Enums used as query or path parameters are named schemas in `components/schemas`, with
  `x-enum-varnames` for readable constant names. Inline enums on parameters are generated as
  `String` and would not be checked. A converter in the `web` module binds generated enums by their
  contract value (`fromValue`), not their constant name.
- Parameters that can't be converted (unknown enum values, malformed UUIDs, non-numbers) return
  400 with a `violations` entry using code `INVALID_FORMAT`.
- The Actuator execution keeps `generateApis=false`: Spring implements those endpoints.

## Consequences

- A contract change that affects an endpoint breaks the build until the controller follows.
- The generator decides the controller signatures (`ResponseEntity<T>`, boxed parameter types).
  Upgrading the generator can change them; check the generated interfaces when bumping its version.
- Generated code uses JSpecify's `@Nullable` (`useJspecify`), like the rest of the code base,
  instead of Spring's deprecated `org.springframework.lang.Nullable`.
- Request enums in query parameters are now rejected as ADR 0008 intended.

## Alternatives considered

- **Keep hand-written controllers (ADR 0006):** full control over signatures, but contract drift is
  only found by tests.
- **Keep `@Validated` and handle `ConstraintViolationException` globally:** works, but uses a proxy
  for what Spring MVC does natively, and errors lose the request-parameter context.
- **Validate enum parameters by hand in each adapter:** no converter, but every module repeats the
  check and the contract's enum is not enforced automatically.


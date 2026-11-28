---
paths:
  - "**/*.java"
---

# Java

- Domain and application layers: records, no Lombok, no Spring, no Jackson, no SQL (ADR 0001).
- Every package has a `package-info.java` with `@NullMarked`; mark exceptions `@Nullable`.
- Adapters depend on ports, never on services (ADR 0001).
- Ids are UUIDv7 from `newId()` (ADR 0014); timestamps come from the injected `Clock` (ADR 0010).
- Web mapping uses MapStruct with plain `@Mapper`; shared conversions extend
  `CommonWebMappings` (ADR 0015).
- Controllers implement the generated `*Api` interface with only `@RestController`: don't repeat
  mapping or validation annotations, and never add `@Validated` (ADR 0021).
- Classes annotated with `@Configuration` or `@TestConfiguration` end in `Configuration`, as in
  Spring's own code; choose a name that doesn't clash with a Spring class (`WebCorsConfiguration`,
  not `CorsConfiguration`). `@ConfigurationProperties` classes end in `Properties`.
- Never edit generated code under `target/`.


---

paths:
- "**/contracts/**/*.yaml"
--------------------------

# OpenAPI contracts

- OpenAPI 3.0.3, YAML. Quote values (examples, defaults, versions, response codes, human text);
  never quote keywords or identifiers. Flow lists use spaced brackets: `[ id, name ]` (ADR 0006).
- Schema names describe the API: `CreateAppUserRequest`, `AppUserResponse`, never `Dto` (ADR 0006).
- Shared schemas live in `common/` and are referenced with relative `$ref` (ADR 0006).
- Errors use the shared Problem responses; module-specific reasons go in `code` (ADR 0007).
- Response enums are `type: string` with `x-extensible-enum`; request enums use `enum` (ADR 0008).
- Lists return `{ items, page }`; empty searches return 200 (ADR 0009).
- Resource ids are `id`, UUID, read-only; stored resources use `AuditedResource` (ADR 0009, 0014).


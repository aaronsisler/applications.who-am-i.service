# 0007. Errors as Problem Details

- **Status:** Accepted
- **Date:** 2026-11-03
- **Amended:** 2026-11-04 (where errors are handled, ordering, logging)
- **Amended:** 2026-11-21 (parameters that can't be converted return violations, see 0021)

## Context

A custom error format duplicates what the HTTP status already says, and drifts from what Spring
actually produces. Clients still need a machine-readable reason for specific failures.

Errors also come from two different places: HTTP itself (malformed input, unknown URLs, unsupported
methods, unexpected failures), which is the same for every module, and each module's business
rules, which only that module understands.

## Decision

### Response format

- Errors are **RFC 9457 Problem Details** (`application/problem+json`), produced with Spring's
  `ProblemDetail`.
- Two extension members:
  - **`code`**: a machine-readable reason for cases the status can't express, such as
    `EMAIL_ADDRESS_TAKEN`. Each API documents the codes its operations return.
  - **`violations`**: present only for validation failures (400), each with `field`, `code` and
    `message`.
- Codes that repeat the status (`NOT_FOUND`, `METHOD_NOT_ALLOWED`, ...) are not used.
- Field-level codes describe the problem from the client's side, not the validation technology:

|                          Bean Validation constraint                          |           Code           |
|------------------------------------------------------------------------------|--------------------------|
| `@NotNull`, `@NotBlank`, `@NotEmpty`                                         | `REQUIRED`               |
| `@Size` (above the maximum / below the minimum)                              | `TOO_LONG` / `TOO_SHORT` |
| `@Email`, `@Pattern`                                                         | `INVALID_FORMAT`         |
| `@Min`, `@Max`, `@DecimalMin`, `@DecimalMax`, `@Positive`, `@PositiveOrZero` | `OUT_OF_RANGE`           |
| Anything else                                                                | `INVALID`                |

- Query parameters and path variables that can't be converted to their type (an unknown enum
  value, a malformed UUID, a non-number) use `INVALID_FORMAT` (see 0021).
- `code` is a string, not a shared enum (see 0008).
- Reusable responses (`InvalidRequest`, `NotFound`) are referenced from every contract.

### Where errors are handled

- **Protocol errors are handled once, globally,** by `GlobalProblemHandler` in the `web`
  infrastructure module. It extends Spring's `ResponseEntityExceptionHandler`, which already turns
  every standard MVC exception (unknown URL, wrong method, unsupported media type, type mismatch,
  missing parameter) into a Problem Details response with the right status. We only override:
  - request body validation (`MethodArgumentNotValidException`) and parameter validation
    (`HandlerMethodValidationException`), to add `violations`;
  - type mismatches (`TypeMismatchException`), to add `violations` like any other invalid input;
  - unreadable bodies (`HttpMessageNotReadableException`), to give a clear, fixed message;
  - a catch-all for `Exception`, returning 500.
- **Business errors are handled per module,** by a handler in the module's web adapter (for example
  `AppUserProblemHandler`), scoped to that module's controllers with
  `@RestControllerAdvice(assignableTypes = ...)`. Only the module knows what its exceptions mean,
  which status they map to, and which `code` they carry.
- **The `web` module cannot know about business exceptions.** It declares
  `allowedDependencies = {}`, so Spring Modulith fails the build if the global handler references
  any module's types.

### Ordering

The global handler's catch-all matches every exception, and Spring consults advice in order. The
global handler is therefore always last (`@Order(Ordered.LOWEST_PRECEDENCE)`), and every module
handler declares a lower order value (`@Order(0)`). Without this, a business error such as "email
address taken" could be answered with a 500.

### What clients see, and what we log

- Response texts are **fixed and safe**. Exception messages are never copied into `detail`,
  because they can contain SQL, table names or personal data.
- `detail` never contains personal data, such as email addresses.
- **Unexpected errors (500) are logged** with their full stack trace, since the response
  deliberately reveals nothing. Client errors (4xx) are not logged as errors.
- An unavailable database needs no handler of its own: it is an unexpected error, handled by the
  catch-all.

## Consequences

- The contract documents exactly what Spring sends.
- Modules add their own codes and handlers without editing shared code.
- Standard HTTP errors need no code of ours; upgrading Spring brings handling for new MVC
  exceptions automatically.
- A new module must remember its handler's `@Order`; a module handler without it may be consulted
  after the global catch-all.
- Logged 500s are the only place internal error details appear, so logs must be available to
  whoever investigates incidents.

## Alternatives considered

- **A custom `ErrorDto` with a global `ErrorCode` enum:** every module edits one shared file, and
  adding a code breaks clients that deserialize strictly.
- **One global handler for every exception, business ones included:** it must depend on every
  module, which breaks module boundaries, and generic exceptions like "duplicate data" lose the
  information about *what* was duplicated.
- **Handling every MVC exception ourselves:** more code, and easy to miss cases. Since Spring Boot
  3.2, for example, unknown URLs raise `NoResourceFoundException` instead of
  `NoHandlerFoundException`; extending `ResponseEntityExceptionHandler` keeps such changes
  Spring's concern.


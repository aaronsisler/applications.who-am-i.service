---

paths:
- "**/*.feature"
- "**/src/test/**/*.java"
-------------------------

# Tests

- Every test is a Cucumber scenario; domain features are written for non-technical readers, grouped
  with `Rule:` (ADR 0004).
- Pick the level by seam: domain rules without Spring; web adapters in the web slice with the use
  case mocked; use cases against in-memory fakes, asserting state; storage contracts against both
  fake and Postgres; wiring and `@e2e` scenarios on the full application (ADR 0003).
- Fakes live in test sources and are never Spring components (ADR 0003).
- Steps with state reset it in a `@Before` hook (ADR 0004).
- Control time with the `TestClock` ("Given the time is ..."), never `Instant.now()` (ADR 0010).
- Wiring scenarios verify through the tables, not by reading back over HTTP (ADR 0003).


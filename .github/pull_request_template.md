## What and why

<!-- One or two sentences. The PR title becomes the release note, so write it for someone reading the release history. -->

## Checklist

<!-- Delete items that don't apply. -->
- [ ] PR title contains `#minor` or `#major` if this isn't a patch release
- [ ] Contract created/updated (`src/main/resources/contracts/inbound`)
- [ ] Cucumber features/scenarios added or updated
- [ ] Schema changes are in a **new** Flyway migration (existing migrations untouched)
- [ ] Dependencies added to the pom(s) have a comment explaining their usage
- [ ] Hexagonal layers and naming conventions followed
- [ ] ADR added to `docs/adr` and the ADR index, if this makes an architectural decision
- [ ] API collection (Bruno) updated and committed to the api-client repository
- [ ] AGENTS.md / `.claude/rules` updated if conventions changed


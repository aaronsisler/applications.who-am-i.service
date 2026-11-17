---

paths:
- "docs/adr/**"
---------------

# Architecture Decision Records

- One decision per record, numbered in order: `NNNN-short-title.md`, with Status, Date, Context,
  Decision, Consequences and Alternatives considered (ADR 0000).
- Add every new record to the index in `docs/adr/README.md`.
- **Amend** when adding detail that doesn't contradict the decision: update the record and add an
  "Amended" line with the date and what changed.
- **Supersede** when reversing or replacing a decision: write a new record and set the old one's
  status to "Superseded by NNNN". Never rewrite a decision in place.
- If the decision changes how code is written, update the matching `.claude/rules/` file in the
  same change (ADR 0019).


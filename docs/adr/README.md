# Architecture Decision Records

Each file records one decision: the problem at the time, what we chose, what it costs, and what we
rejected. Read them before changing something that looks odd; write a new one when you make a
decision someone will later ask "why?" about.

## Index

| No.  |                                          Decision                                          |  Status  |
|------|--------------------------------------------------------------------------------------------|----------|
| 0000 | [Record architecture decisions](0000-record-architecture-decisions.md)                     | Accepted |
| 0001 | [Hexagonal architecture](0001-hexagonal-architecture.md)                                   | Accepted |
| 0002 | [Modules with Spring Modulith](0002-modules-with-spring-modulith.md)                       | Accepted |
| 0003 | [Test strategy by seam](0003-test-strategy-by-seam.md)                                     | Accepted |
| 0004 | [Cucumber for all tests](0004-cucumber-for-all-tests.md)                                   | Accepted |
| 0005 | [Spring JDBC instead of JPA](0005-jdbc-instead-of-jpa.md)                                  | Accepted |
| 0006 | [Contract-first APIs with OpenAPI](0006-contract-first-openapi.md)                         | Accepted |
| 0007 | [Errors as Problem Details](0007-errors-as-problem-details.md)                             | Accepted |
| 0008 | [Enums at API boundaries](0008-enums-at-api-boundaries.md)                                 | Accepted |
| 0009 | [Resources and collections](0009-resources-and-collections.md)                             | Accepted |
| 0010 | [Timestamps and time types](0010-timestamps-and-time-types.md)                             | Proposed |
| 0011 | [Formatting enforced by the build](0011-formatting-enforced-by-the-build.md)               | Accepted |
| 0012 | [IDE formatting through Spotless Applier](0012-ide-formatting-with-spotless-applier.md)    | Accepted |
| 0013 | [Dependency versions follow their owners](0013-dependency-versions-follow-their-owners.md) | Accepted |
| 0014 | [Time-ordered UUIDs as identifiers](0014-time-ordered-uuids-as-identifiers.md)             | Accepted |
| 0015 | [MapStruct for adapter mapping](0015-mapstruct-for-adapter-mapping.md)                     | Accepted |

## Writing a new record

Copy the structure of any existing record, give it the next number, and add it to the index.
A record is never edited to reverse a decision: write a new one that supersedes it, and set the
old one's status to "Superseded by NNNN".

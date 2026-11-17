---

paths:
- "**/pom.xml"
--------------

# Maven

- Versions and shared plugin configuration live only in the parent pom (ADR 0017).
- Version properties are named `<artifactId>.version`.
- Every dependency and plugin entry has a comment naming its version source, e.g.
  `<!-- Version: Spring Parent -->`.
- Modules never redeclare a parent property; extended lists use `combine.children="append"`.
- Check version compatibility with the owning project before upgrading (ADR 0013).


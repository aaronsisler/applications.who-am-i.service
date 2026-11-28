# 20. Use Amazon Corretto as the JDK

## Status

Accepted

## Context

The service targets Java 25 (see the Maven Enforcer rule in the parent pom). Java 25 is an LTS
release, but "Java 25" alone doesn't say which JDK *distribution* we build, test and run on.
Distributions differ in licensing, how long they're supported, how often they're patched, and
which container images they ship.

We want:

- **No licensing cost or compliance risk.** Anyone on the team, and the pipeline, must be able to
  use the JDK freely in development and production.
- **Long-term support** for the LTS line, with regular security and bug-fix updates.
- **The same JDK everywhere:** developer machines (Windows and macOS/Apple Silicon), CI, and the
  runtime container image, so "works on my machine" isn't a JDK problem.
- **Easy installation** with tools we already use (SDKMAN on macOS/Linux/Git Bash, container base
  images for runtime).

Options considered:

|        Option         |                                                                 Notes                                                                  |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| Oracle JDK            | Production use falls under Oracle's licence terms, which have changed several times. Needs legal review we'd rather avoid.             |
| Oracle OpenJDK builds | Free, but only updated for six months per release. No LTS updates.                                                                     |
| Eclipse Temurin       | Free, TCK-certified, LTS, widely used. A strong alternative.                                                                           |
| Amazon Corretto       | Free, TCK-certified, LTS with quarterly updates, multi-platform (incl. aarch64 macOS), official container images, available in SDKMAN. |
| BellSoft Liberica     | Free, TCK-certified, LTS, good small images. Less familiar to the team.                                                                |

Temurin and Corretto are close on technical merit. What tips it to Corretto is that the team
already uses it locally through SDKMAN. Its official images also make it easy to keep the runtime
the same as development.

## Decision

We use **Amazon Corretto 25** as the JDK for development, CI and runtime.

- **Local development:** install with SDKMAN (`sdk install java 25.x.x-amzn`, current patch
  version), or with the Corretto installer on Windows if SDKMAN isn't available.
- **CI:** the pipeline sets up Corretto 25, e.g. `actions/setup-java` with `distribution: corretto`.
- **Runtime:** container images are built on an official `amazoncorretto:25` base image (or a
  variant of it).
- **Maven Enforcer:** keeps enforcing the Java *version* (`requireJavaVersion`). It doesn't
  enforce the vendor. Using the same vendor everywhere is a team convention, written down here and
  in the README/AGENTS.md.

We take Corretto patch releases (quarterly, in line with the OpenJDK update schedule) as part of
normal dependency maintenance.

## Consequences

### Positive

- No licensing cost and no licence-compliance review needed.
- LTS updates for Java 25, published on a predictable quarterly schedule.
- The same distribution on every developer machine, in CI and in production, which cuts out a
  whole class of environment differences.
- Native builds for Apple Silicon and x86_64, so the MacBook and Windows setups behave the same.

### Negative

- We depend on Amazon to keep maintaining Corretto. If that changes, switching to another
  TCK-certified distribution (e.g. Temurin) should be cheap: build tooling, images and the
  pipeline step would change, not the code.
- The build doesn't enforce the vendor, so a developer could build with a different JDK 25 and
  not notice. The risk is low because all certified distributions pass the same TCK, but it can
  still cause small differences (default GC tuning, bundled certificates, fonts in images).
- Someone has to bump Corretto patch versions in SDKMAN pins, CI and the base image when a
  quarterly release comes out. Dependency-update tooling should cover the base image tag.

## Related

- ADR on the Maven Enforcer replacing the Maven Wrapper (Maven/Java version enforcement).


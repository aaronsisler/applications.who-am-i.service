# Changelog

> **After v0.5.0, release notes are published as
> [GitHub Releases](https://github.com/aaronsisler/applications.who-am-i.service/releases).**
> Every merge to `main` is released automatically, and its release notes are generated from the
> pull request title. See [Versioning and releases](./CONTRIBUTING.md#versioning-and-releases).
>
> The entries below are kept as history from before releases were automated.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this
project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.5.0] - 2026-05-03

App User: Create – acceptance tests

### Added

- Acceptance tests for App User creation

### Changed

- Integration tests restructured to mirror the overall project structure
- POM files restructured: plugin versions and configuration in the parent's `pluginManagement`,
  dependencies in the service module

## [0.4.0] - 2026-02-28

App User: Create – integration tests

### Changed

- Integration tests restructured
- User creation test cases extended for better coverage and reliability

## [0.3.0] - 2026-01-15

App User: Create

### Added

- Create User functionality
- Negative tests for Create User

## [0.2.0] - 2025-11-15

Code quality

### Added

- Sonar analysis and JaCoCo code coverage in the build and pipeline

### Changed

- Sonar configuration moved from `sonar-project.properties` into the POM files

## [0.1.0] - 2025-11-09

Base repository

### Added

- Health actuator endpoint
- Info actuator endpoint
- Checkstyle
- Integration tests for the health and info endpoints

[0.5.0]: https://github.com/aaronsisler/applications.who-am-i.service/compare/v0.4.0...v0.5.0
[0.4.0]: https://github.com/aaronsisler/applications.who-am-i.service/compare/v0.3.0...v0.4.0
[0.3.0]: https://github.com/aaronsisler/applications.who-am-i.service/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/aaronsisler/applications.who-am-i.service/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/aaronsisler/applications.who-am-i.service/releases/tag/v0.1.0


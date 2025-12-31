# Versioning

SpotX follows semantic versioning (MAJOR.MINOR.PATCH).

- Current version: 1.1.1
- Changelog:
  - **1.1.1**: Removed default spotlight borders (borderless by default); fixed footer layout to avoid overlapping card.
  - **1.1.0**: UI refinements and accessibility improvements.
  - **1.0.x**: Initial stable release series.

## Publishing to Maven Central

Releases are published via GitHub Actions workflow using OSSRH.

Required secrets:
- `OSSRH_USERNAME`, `OSSRH_PASSWORD`
- `SIGNING_KEY`, `SIGNING_PASSWORD` (ASCII-armored PGP) 
# Versioning

SpotX follows semantic versioning (MAJOR.MINOR.PATCH).

- Current version: 0.1.0
- Changelog will be tracked in GitHub Releases.

## Publishing to Maven Central

Releases are published via GitHub Actions workflow using OSSRH.

Required secrets:
- `OSSRH_USERNAME`, `OSSRH_PASSWORD`
- `SIGNING_KEY`, `SIGNING_PASSWORD` (ASCII-armored PGP) 
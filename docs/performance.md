---
layout: default
title: Performance
---

# Performance

SpotX is designed to be lightweight for both runtime and build time.

## Runtime performance (Compose facts + tips)

- Compose updates only what changed (selective recomposition). Keep target data immutable and stable to enable compiler and runtime optimizations.
- Use `remember` and `derivedStateOf` to avoid recomputing geometry.
- Avoid heavy work in `draw` lambdas; precompute constants outside composition where possible.
- Prefer immutable `SpotXTarget` lists; use `remember(targets)` when constructing lists.
- Keep overlays near the root to minimize overdraw and z-index complexity.

## Build and compile time (facts + tips)

- The Compose compiler plugin analyzes composable functions at compile time; stable models help it generate efficient code paths.
- Incremental Kotlin compilation and Gradle build cache are supported and on by default in this project.
- Tips to keep builds snappy:
  - Enable the Gradle configuration cache for local builds: `org.gradle.configuration-cache=true` (optional).
  - Prefer KSP over KAPT for any processors you add; SpotX itself requires none.
  - Avoid unnecessary annotation processing; SpotX uses plain Kotlin/Compose.
  - Keep your IDE on JDK 17+ and align toolchains with the project.

## Measuring

- Use `adb shell dumpsys gfxinfo` and Android Studio Profiler to check frame times.
- Enable Compose compiler metrics locally to analyze recomposition hotspots if needed.

```properties
# local gradle.properties (developer machine)
kotlin.compose.compiler.report=true
kotlin.compose.compiler.metrics=true
```

## Known characteristics

- Compose’s first build after clean can be slower due to compiler analysis; subsequent incremental builds are typically fast.
- Runtime rendering is GPU-accelerated; spotlight draws are simple vector ops and do not require bitmaps. 
---
layout: default
title: SpotX
---

# SpotX

SpotX is a modern onboarding tours SDK for Jetpack Compose. Spotlight any composable with elegant, Material 3‑styled overlays—built the "Compose way": declarative, fast, and a joy to use.

> Kotlin-first. Compose-native. Minimal API, maximum clarity.

- SDK: `spotx` library module
- Sample app: `sample` module

## Installation

```kotlin
implementation("io.github.harry-kuria:spotx:1.0.6")
```

## Quick usage

```kotlin
val controller = remember { SpotXController() }

Button(
    modifier = Modifier.spotxAnchor("btn.primary", controller),
    onClick = { }
) { Text("Primary Action") }

LaunchedEffect(Unit) {
    controller.start(
        listOf(
            SpotXTarget(
                id = "btn.primary",
                title = "Primary Button",
                description = "Tap to perform the main action.",
                ringColor = Color(0xFF22C55E)
            )
        )
    )
}

SpotXOverlay(controller = controller)
```

<p>
  <a class="btn" href="./overview.html">Why SpotX</a>
  <a class="btn" href="./usage.html" style="margin-left:8px">Usage</a>
  <a class="btn" href="./api/" style="margin-left:8px">API</a>
</p>

## Features

- Highlight UI elements with customizable shapes
- Material 3 styling
- Simple, composable API

## Facts about Kotlin and Compose

- Kotlin’s null‑safety, coroutines, and data classes yield robust, concise code.
- Jetpack Compose updates only what changes (selective recomposition) and is GPU‑accelerated.
- The Compose compiler performs stability inference and slot table optimizations for performance.

## Links

- GitHub: https://github.com/harry-kuria/SpotX
- Contact: ./contact.html
- API Reference: ./api/ 
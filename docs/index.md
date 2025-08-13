---
layout: default
title: SpotX
---

# SpotX

Jetpack Compose onboarding tours SDK inspired by TapTargetView. Draw spotlight overlays with customizable highlight circles, rings, and descriptions.

- SDK: `spotx`
- Sample: `sample`
- API Reference: see API docs built with Dokka under `api/`

## Installation

After publishing to Maven Central:

```kotlin
dependencies {
    implementation("io.github.harrykuria:spotx:0.1.0")
}
```

## Quick start

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

## Customization

- Circle or rounded rectangle shape
- Custom ring color/width and overlay dim color
- Padding around target radius

See more in [usage](./usage.md). 
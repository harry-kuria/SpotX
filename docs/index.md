---
layout: default
title: SpotX
---

# SpotX

SpotX is a Jetpack Compose onboarding tours SDK inspired by TapTargetView. It provides spotlight overlays that guide users through your UI.

- SDK: `spotx` library module
- Sample app: `sample` module

## Installation

Add the dependency after publishing to Maven Central:

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
  <a class="btn" href="./usage.html">Read the full Usage guide</a>
  <a class="btn" href="./api/" style="margin-left:8px">API Reference</a>
</p>

## Features

- Highlight UI elements with customizable shapes
- Material 3 styling
- Simple, composable API

## Status

Early preview. APIs may change.

## Links

- GitHub: https://github.com/harry-kuria/SpotX
- Contact: ./contact.html
- API Reference: ./api/ 
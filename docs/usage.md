---
layout: default
title: Usage
---

# Usage

Add dependency:

```kotlin
implementation("io.github.harry-kuria:spotx:0.1.0")
```

Basic example:

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

## Targets and overlay

```kotlin
controller.start(
    targets = listOf(
        SpotXTarget(
            id = "btn.primary",
            title = "Primary Button",
            description = "Tap to perform the main action.",
            ringColor = Color(0xFF22C55E) // customize ring color
        )
    )
)

SpotXOverlay(
    controller = controller,
    defaultOverlayColor = Color(0xCC000000),
)
```

## Custom colors and shapes

```kotlin
SpotXTarget(
    id = "settings.icon",
    title = "Settings",
    description = "Open app preferences",
    shape = SpotXShape.RoundedRect,
    cornerRadiusDp = 16f,
    ringColor = Color(0xFF3B82F6),
    ringWidthDp = 4f,
    radiusPaddingDp = 8f,
    overlayColor = Color(0xAA000000)
)
```

## Programmatic control

- `controller.next()` advances the tour
- `controller.previous()` goes back
- `controller.stop()` ends the tour 
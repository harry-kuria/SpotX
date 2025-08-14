---
layout: default
title: Architecture
---

# Architecture

SpotX follows a simple, predictable architecture tailored for Compose.

## Core concepts

- `SpotXController`: Holds tour state (targets list, current step, running/stopped) and exposes operations (`start`, `next`, `previous`, `stop`). Provide this via `remember { SpotXController() }`.
- `spotxAnchor(tag, controller)`: Modifier that registers a UI element’s bounds under a unique tag with the controller for targeting.
- `SpotXTarget`: Immutable description of a step: `id`, `title`, `description`, shape, paddings, and colors.
- `SpotXOverlay(controller)`: A composable drawn near the root that reads state from the controller and renders the dimmed overlay, ring highlight, and callouts.

## Data flow

1. Anchors register their layout coordinates with the controller keyed by `id`.
2. `controller.start(targets)` sets the scenario; current index begins at 0.
3. `SpotXOverlay` observes controller state; it computes geometry and draws the spotlight and UI chrome.
4. User actions or programmatic calls (`next`, `previous`, `stop`) update state; overlay recomposes efficiently.

## Composition and performance

- State is stored in a single controller; Compose `remember` avoids re-allocations.
- Geometry is derived using `derivedStateOf` where appropriate.
- Rendering paths avoid allocations in `draw` lambdas.

## Accessibility

- Titles and descriptions are provided for screen readers.
- Focus order remains predictable; overlay traps input only when the step is active.

## Integrating

```kotlin
val controller = remember { SpotXController() }

Column(modifier = Modifier.fillMaxSize()) {
    Button(
        modifier = Modifier.spotxAnchor("primary", controller),
        onClick = { }
    ) { Text("Primary") }
}

LaunchedEffect(Unit) {
    controller.start(
        listOf(
            SpotXTarget(
                id = "primary",
                title = "Primary Action",
                description = "Triggers the main flow"
            )
        )
    )
}

SpotXOverlay(controller)
``` 
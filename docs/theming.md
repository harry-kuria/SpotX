---
layout: default
title: Theming
---

# Theming and customization

SpotX embraces Material 3 and can be styled to match your brand or Android Developers color palette.

## Quick knobs

- Ring: `ringColor`, `ringWidthDp`
- Overlay: `overlayColor`, `defaultOverlayColor` on `SpotXOverlay`
- Shape: `SpotXShape.Circle`, `SpotXShape.RoundedRect` with `cornerRadiusDp`
- Padding: `radiusPaddingDp`

## Examples

```kotlin
SpotXTarget(
    id = "cta",
    title = "Call to action",
    description = "Tap to continue",
    ringColor = Color(0xFF3DDC84), // Android green
    ringWidthDp = 4f,
    shape = SpotXShape.RoundedRect,
    cornerRadiusDp = 16f,
)
```

```kotlin
SpotXOverlay(
    controller = controller,
    defaultOverlayColor = Color(0xCC000000) // semi-transparent backdrop
)
```

## Accessibility

- Ensure contrast for ring and text in callouts.
- Provide succinct titles and actionable descriptions.

## Design guidance

- Use Android blue `#073042` for headings and Android green `#3DDC84` for accent to mirror developer.android.com aesthetics. 
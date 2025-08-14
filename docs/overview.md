---
layout: default
title: Overview
---

# Why SpotX

SpotX is a modern, Kotlin-first onboarding tours SDK for Jetpack Compose that lets you spotlight any composable with elegant, Material 3‑styled overlays. It is designed to feel native to Compose: declarative, succinct, and powerful.

> Built for developers who value DX and performance. Minimal API surface, maximal clarity.

## Highlights

- Declarative API that composes naturally with your UI tree
- Material 3 styling and accessibility in mind
- Zero View inflation, no Fragments, no compatibility shims
- Small surface area: a `SpotXController`, `spotxAnchor(...)`, `SpotXTarget`, and `SpotXOverlay`
- Customizable shapes, colors, paddings, and bring‑your‑own animations

## What makes it great (facts and praise)

- Written in **Kotlin**: expressive, null‑safe, with coroutines for clean async flows.
- Built for **Jetpack Compose**: a declarative UI toolkit that reduces boilerplate and improves readability.
- **Recomposition efficiency**: SpotX keeps state in a single controller and uses `remember`, `derivedStateOf`, and stable data to minimize unnecessary recompositions.
- **No reflection or heavy annotation processing**: keeps build times predictable.
- **Interoperability**: drop into existing Compose screens without refactors; works with Material 3.

## Kotlin facts relevant to SDKs

- Kotlin’s null‑safety eliminates an entire class of NPE runtime bugs.
- Coroutines provide structured concurrency that is lightweight and cancellation‑aware.
- Inline/value classes and data classes enable efficient, immutable modeling of UI state.
- Multiplatform-ready language foundations keep the codebase forward‑compatible.

## Jetpack Compose facts

- Compose uses a single‑source-of-truth state model; recomposition selectively updates only the parts of the UI that changed.
- The Compose compiler plugin performs optimizations like slot table diffing and stability inference.
- Preview tooling dramatically shortens iteration time vs. imperative XML inflations.
- Compose rendering is GPU‑accelerated and backed by modern graphics stacks on Android.

## When to use SpotX

- Onboarding new users with guided tours
- Announcing new features with contextual highlights
- Educating users on complex flows with step‑by‑step focus

## Next steps

- Read the Getting Started in the [Usage guide]({{ site.baseurl }}/usage.html)
- Explore the [Architecture]({{ site.baseurl }}/architecture.html)
- See [Performance]({{ site.baseurl }}/performance.html) best practices
- Customize appearance in [Theming]({{ site.baseurl }}/theming.html) 
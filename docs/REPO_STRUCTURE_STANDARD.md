# Nexora TV — Repo Structure Standard

Status: ACTIVE
Updated: 2026-05-21

## Purpose

Keep the Android TV client clean, readable, and safe to extend without file bloat, duplicate code, or patch stacking.

## Source-of-Truth Docs

Read order for this repo:

1. `docs/MILESTONE_STATUS.md`
2. `docs/NEXT_TASK.md`
3. `docs/PROJECT_MEMORY.md`
4. `docs/HANDOFF.md`
5. `docs/CODE_HYGIENE_DIRECTIVE.md`
6. `docs/REPO_STRUCTURE_STANDARD.md`
7. `docs/PROTECTED_SYSTEMS.md`

## App Source Layout

Preferred Android client structure:

```text
app/src/main/java/com/nexora/tv/
  MainActivity.kt
  navigation/
  data/
    app/
    content/
    device/
    live/
    network/
    profile/
  ui/
    components/
    home/
    detail/
    intro/
    screens/
    theme/
```

## Screen Rules

`ui/screens/` is only for route-level screens.

A screen file may contain:

- route entry composable
- local screen state
- route-level navigation calls
- small screen-only helpers

A screen file should not contain:

- full reusable design system components
- network loading implementation
- parsing logic
- persistence mapping
- large repeated card/button layouts
- many unrelated panels

## Component Extraction Rules

Extract when a section becomes reusable, large, or separately understandable.

Preferred destinations:

- Home-specific UI: `ui/home/`
- Detail-specific UI: `ui/detail/`
- Shared TV components: `ui/components/`
- Theme tokens: `ui/theme/`
- Network helpers: `data/network/`
- Source/provider logic: `data/live/`

## File Size Targets

Soft limits:

- Route screen: under 300 lines
- Feature UI component file: under 250 lines
- Shared component file: under 200 lines
- Service/loader file: under 250 lines

If a file exceeds the soft limit, Builder must either split it or explain why it must stay together.

## No Patch Stacking Rule

When behavior changes:

- remove replaced code
- do not leave old helpers behind
- do not duplicate helpers with new names unless there is a temporary compatibility reason
- do not fix a bug by adding another layer around broken code without cleaning the broken layer

## Protected Systems

Playback core, TV navigation, auth/device activation, platform API contracts, and premium cinematic UX must not be rewritten casually.

Protected systems can be cleaned only through scoped, minimal, testable patches.

## Current Cleanup Priority

1. Align docs and source-of-truth files.
2. Split oversized `HomeScreen.kt`.
3. Split oversized `ContentDetailScreen.kt`.
4. Centralize repeated UI tokens/components.
5. Extract repeated HTTP setup into a safe helper.
6. Review PlayerScreen only after UI cleanup, without playback rewrite.

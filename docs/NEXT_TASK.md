# Nexora TV — NEXT_TASK

## Current Status

Developer implementation returned, but Director pre-QA review found a routing blocker.

Return to Developer for a minimal fix before QA.

## Current Active Milestone

`M6 Playlist Profile & Legal Source Input Foundation`

Status: `ACTIVE`

## Current Active Task

`M6-TASK-001 Playlist Profile Model & Legal Input Shell`

Status: `NEEDS DEVELOPER FIX BEFORE QA`

## Director Pre-QA Finding

Developer added:

- `app/src/main/java/com/nexora/tv/data/playlist/PlaylistProfile.kt`
- `app/src/main/java/com/nexora/tv/ui/screens/PlaylistProfileScreen.kt`

Issue:

- `PlaylistProfileScreen` exists but is not reachable from app navigation.
- `AppDestinations.kt` has no playlist/profile destination.
- `NexoraNavHost.kt` has no composable route for `PlaylistProfileScreen`.

Why this blocks QA:

- M6-TASK-001 expected the user to reach a profile/source input shell.
- QA cannot validate runtime access if the screen is not wired.

## Required Developer Fix

Add minimal additive navigation wiring only.

Allowed:

- Add a new route in `AppDestinations.kt`, for example `PlaylistProfile`.
- Add a `composable` entry in `NexoraNavHost.kt` for `PlaylistProfileScreen(navController)`.
- Add one minimal entry point from an existing safe screen only if required to reach the shell.

Not allowed:

- No navigation system rewrite
- No playback/auth/backend changes
- No provider/API integration
- No real playlist fetch
- No bundled content
- No design system rewrite

## Required Developer Return

Return to Director with:

```text
Changed Files:
- ...

Implementation Summary:
- ...

Test Notes:
- ...

Risk:
- ...

Next Recommended Agent:
QA_TESTER or DIRECTOR
```

# Nexora TV — NEXT_TASK

## Current Status

Runtime retest failed.

## Active Milestone

`M5 Content Library & Navigation Expansion`

## Active Task

`M5-TASK-005 Player Safe Shell Fallback`

## Objective

Make the Player route open a safe visible shell first, without starting playback during initial render.

## Runtime Evidence

User retest on Android Studio emulator:

- Detail screen playback button opens Player: NO
- Player route marker visible: NO
- App closes after pressing the button
- Back button works before the close

## Previous Task Result

`M5-TASK-004 Player Launch Flow Fix`

Result:

- Player route marker and full screen background added
- Marker did not become visible during retest

## Scope IN

- Remove or delay ExoPlayer startup from initial PlayerScreen render
- Show a plain safe Player shell first
- Include visible text confirming Player route entry
- Keep navigation route unchanged
- Preserve mock stream constant if needed, but do not auto-start playback in initial shell

## Scope OUT

- No playback architecture rewrite
- No provider/API integration
- No auth/backend changes
- No UI overhaul
- No milestone lock

## Allowed Files

- `app/src/main/java/com/nexora/tv/ui/screens/PlayerScreen.kt`
- `docs/` only if fix notes are required

## Required Return To Director

```text
Result:
DONE / PARTIAL / BLOCKED

Preflight:
- active task confirmed / blocked reason

Changed Files:
- file/path

Fix Summary:
- short item

Build Verification:
- CI/local build passed / failed / not run

Runtime Test:
- passed / failed / not run

Risk:
- none / short risk

Return To Director:
- next recommended agent/action
```

# Nexora TV — NEXT_TASK

## Current Status

Runtime retest failed.

## Active Milestone

`M5 Content Library & Navigation Expansion`

## Active Task

`M5-TASK-004 Player Launch Flow Fix`

## Objective

Fix the flow where the Detail screen playback button does not visibly open the Player screen.

## Runtime Evidence

User retest on Android Studio emulator:

- Detail screen playback button opens Player: NO
- Back button after the attempt works: YES

## Previous Task Result

`M5-TASK-003 Player Runtime Crash Fix`

Result:

- Player lifecycle cleanup added
- Player is still not visibly opening from Detail

## Scope IN

- Inspect Detail to Player navigation path
- Inspect PlayerScreen launch/render behavior
- Minimal patch so Player screen is visibly opened
- Add simple Player screen background/status text if needed to confirm route entry
- Preserve existing mock stream if possible

## Scope OUT

- No playback architecture rewrite
- No provider/API integration
- No auth/backend changes
- No UI overhaul
- No milestone lock

## Allowed Files

- `app/src/main/java/com/nexora/tv/ui/screens/ContentDetailScreen.kt`
- `app/src/main/java/com/nexora/tv/ui/screens/PlayerScreen.kt`
- `app/src/main/java/com/nexora/tv/navigation/` only if route wiring issue is found
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

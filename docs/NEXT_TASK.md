# Nexora TV — NEXT_TASK

## Current Status

Runtime smoke test failed.

## Active Milestone

`M5 Content Library & Navigation Expansion`

## Active Task

`M5-TASK-003 Player Runtime Crash Fix`

## Objective

Fix the emulator runtime crash that happens when Detail screen `Play Mock` navigates to Player.

## Failure Evidence

User runtime smoke test on Android Studio emulator:

Passed:

- Splash → Login → Activation
- `demo123` activation
- Home menu sections
- Detail screen
- Back navigation outside Player

Failed:

- Play Mock → Player

Issue:

- App closed after pressing Play Mock

## Likely Risk Area

`PlayerScreen.kt` currently creates an ExoPlayer instance directly inside the composable without lifecycle-safe release handling.

Developer must inspect and fix only what is required.

## Scope IN

- Minimal player runtime stability fix
- Keep existing test stream behavior if possible
- Make PlayerScreen lifecycle-safe if needed
- Add basic fallback/error-safe UI only if required to prevent crash
- Preserve existing routes and M5 content library behavior

## Scope OUT

- No playback engine rewrite
- No provider/API integration
- No illegal stream/source handling
- No auth changes
- No backend changes
- No UI overhaul
- No new player feature set
- No milestone lock

## Allowed Files / Areas

Developer may work only where needed in these areas:

- `app/src/main/java/com/nexora/tv/ui/screens/PlayerScreen.kt`
- Build files only if compile fix is required
- Docs only if noting test/fix details is required

## Protected Systems Permission

Limited permission granted only for minimal PlayerScreen runtime stability fix.

Not allowed:

- Playback architecture rewrite
- Stream switching system change
- Recovery system change
- Auth/navigation redesign

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

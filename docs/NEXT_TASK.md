# Nexora TV — NEXT_TASK

## Current Status

Ready for QA Tester.

## Current Active Milestone

`M6 Playlist Profile & Legal Source Input Foundation`

Status: `ACTIVE`

## Current Active Task

`M6-TASK-001 Playlist Profile Model & Legal Input Shell`

Status: `READY FOR QA`

## Director Pre-QA Result

PASSED FOR QA.

Verified changed files:

- `app/src/main/java/com/nexora/tv/data/playlist/PlaylistProfile.kt`
- `app/src/main/java/com/nexora/tv/ui/screens/PlaylistProfileScreen.kt`
- `app/src/main/java/com/nexora/tv/navigation/AppDestinations.kt`
- `app/src/main/java/com/nexora/tv/navigation/NexoraNavHost.kt`

Result:

- Profile model exists.
- Profile screen exists.
- App route exists.
- NavHost route opens the profile screen.
- Legal ownership notice is present.
- Flow remains local/mock-safe.
- No live integration is approved in this task.

## QA Scope

QA Tester should verify:

- Build status
- App route compiles
- Profile screen renders
- Legal ownership notice is visible
- Supported source type selection works
- Empty input state works
- Invalid input state works
- Saved local shell state works
- Back/Home navigation remains safe
- Existing Splash/Login/Activation/Home/Detail/Player routes are not broken
- No protected system rewrite is present
- No prohibited content/source behavior is introduced

## QA Return Required

Return to Director with:

```text
QA Result:
PASS / FAIL

Checked Files:
- ...

Runtime / Build Notes:
- ...

Blockers:
- ...

Regression Risk:
- ...

Recommendation:
DOCUMENTATION_MEMORY or DEVELOPER
```

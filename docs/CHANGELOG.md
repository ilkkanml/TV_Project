# Nexora TV — CHANGELOG

## Completed Milestones

### M1 Foundation
- User Test: PASSED
- QA: PASSED
- Director LOCKED: YES

### M2 Playback Expansion
- User Test: PASSED
- QA: PASSED
- Director LOCKED: YES

### M3 Premium UI Expansion
- Final Android TV Runtime Test: PASSED
- QA: PASSED
- Director LOCKED: YES
- Protected systems untouched
- Legal/compliance risk: none detected

### M4 Auth & Device Activation Foundation
- User Test: PASSED
- QA: PASSED
- Director LOCKED: YES
- Protected systems stable
- Legal/compliance risk: none detected

### M5 Content Library & Navigation Expansion
- User Runtime Test: PASSED
- QA: PASSED
- Documentation Memory: PASSED
- Director LOCKED: YES
- M5-TASK-001 Content Library Model & Navigation Foundation: LOCKED
- M5-TASK-002 Build Verification Infrastructure: QA PASSED
- M5-TASK-005 Player Safe Shell Fallback: USER RUNTIME PASSED / QA ACCEPTED
- Protected systems stable
- Legal/compliance risk: none detected

### M6 Playlist Profile & Legal Source Input Foundation
- Build Evidence: PASSED
- User Runtime Test: PASSED
- QA: PASSED
- Documentation Memory: PASSED
- Director LOCKED: YES
- M6-TASK-001 Playlist Profile Model & Legal Input Shell: LOCKED
- Scope delivered:
  - Playlist profile model foundation
  - Playlist profile screen shell
  - Legal ownership notice
  - Supported local input direction shell
  - Empty / invalid / saved shell states
  - Additive navigation route
  - Mock/local-safe flow only
- Changed files:
  - `app/src/main/java/com/nexora/tv/data/playlist/PlaylistProfile.kt`
  - `app/src/main/java/com/nexora/tv/ui/screens/PlaylistProfileScreen.kt`
  - `app/src/main/java/com/nexora/tv/navigation/AppDestinations.kt`
  - `app/src/main/java/com/nexora/tv/navigation/NexoraNavHost.kt`
- Protected systems stable
- Legal/compliance risk: none detected

## Active Milestone

### M7 Local Profile Persistence Foundation

Status: ACTIVE

Opened by Director after user request: `Yeni milestone aç`

Milestone doc:

- `docs/milestones/M7_LOCAL_PROFILE_PERSISTENCE_FOUNDATION.md`

Active task:

- `M7-TASK-001 Local Profile Repository & Saved Profiles Shell`

Task status:

- READY FOR DEVELOPER

Scope direction:

- Saved local profile shell foundation
- Saved profiles list shell
- Active/selected profile state shell
- Minimal add/edit/delete shell behavior
- Preserve M6 profile input shell
- Local/mock-safe flow only
- No production connection
- No unsafe sensitive-data persistence
- No protected system rewrite

Safe Code Engine active: Developer must return build/runtime evidence before QA.

## Process Decisions Added

### Safe Code Engine
- Added: `docs/SAFE_CODE_ENGINE.md`
- Recorded in: `docs/DECISION_LOG.md`
- Applies to:
  - Director
  - Developer
  - QA Tester
  - Documentation Memory
- Rule:
  - No code task can move forward without required build/runtime evidence.

### Window Transition Rule
- New chat transition is currently cancelled.
- Continue M7 in the current window.
- After every future milestone lock, Director must tell user: `Yeni pencereye geçmek güvenli.`
- Each new future milestone should start in a new chat window unless user cancels it.

## Architecture Decisions Added

### App / Backend Integration Direction
- Added: `docs/APP_BACKEND_INTEGRATION.md`
- Decision log added: `docs/DECISION_LOG.md`
- Backend responsibility direction:
  - account
  - subscription/license state
  - device activation
  - payment/reseller state
  - version check
  - force update
  - remote config
- App responsibility direction:
  - generated device ID
  - license checks
  - playlist/profile management
  - encrypted local playlist profile storage
  - multi-profile switching
- Playlist profile source direction:
  - default source of truth is encrypted local device storage
  - web panel may optionally transfer profile data to device
  - longer cloud sync requires explicit user consent

## Next Step

Send `M7-TASK-001` to Developer.

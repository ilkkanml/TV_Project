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

### M7 Local Profile Persistence Foundation
- Build Evidence: PASSED
- User Runtime Test: PASSED
- QA: PASSED
- Documentation Memory: PASSED
- Director LOCKED: YES
- PR #9 merged to main
- Scope delivered:
  - Local profile repository shell
  - Saved profiles list shell
  - Active/selected profile state shell
  - Minimal local add/edit/delete shell behavior
  - M6 profile input shell preserved
  - Session-local/mock-safe profile flow
  - Sensitive values not stored
- Changed files:
  - `app/src/main/java/com/nexora/tv/data/playlist/LocalProfileRepository.kt`
  - `app/src/main/java/com/nexora/tv/ui/screens/PlaylistProfileScreen.kt`
- Protected systems stable
- Legal/compliance risk: none detected

### M8 TV Navigation & Access Polish
- Status: LOCKED
- Current task: M8-TASK-001 Profile Access, Backstack & Login Field Safety Polish
- Status: PASSED / COMPLETED
- Scope: navigation/access polish only, additive, TV-friendly
- Safe Code Engine required
- Build Evidence: Android Build Verification #109 success
- Runtime Evidence: accepted
- QA Result: PASS
- No core system or protected system rewrite
- No playback/auth/backend/storage changes
- No feature expansion
- No UI redesign
- Protected systems clear
- Legal/compliance clear
- Splash cleanup excluded from delivered scope
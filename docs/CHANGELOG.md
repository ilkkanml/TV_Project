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
- User Test: PASSED
- Final Android TV Runtime Test: PASSED
- QA: PASSED
- Sync/Re-compare: DONE
- Main merge: DONE
- Director LOCKED: YES
- Protected systems untouched
- Legal/compliance risk: none detected

### M4 Auth & Device Activation Foundation
- User Test: PASSED
- QA: PASSED
- Director LOCKED: YES
- Changed files:
  - `app/src/main/java/com/nexora/tv/ui/screens/DeviceActivationScreen.kt`
  - `app/src/main/java/com/nexora/tv/navigation/AppDestinations.kt`
  - `app/src/main/java/com/nexora/tv/navigation/NexoraNavHost.kt`
  - `app/src/main/java/com/nexora/tv/ui/screens/LoginScreen.kt`
- Locked scope:
  - Device identity placeholder
  - Activation password screen polish
  - Mock auth validation
  - Active/inactive device state placeholder
  - Subscription expiration placeholder
  - Safe loading/empty/error states
  - Login to Activation route wiring
  - Activation to Home continue path
- Protected systems stable
- Legal/compliance risk: none detected

## Active Milestone

### M5 Content Library & Navigation Expansion
- Status: ACTIVE
- Current stage: Documentation Memory review
- Director LOCKED: NO
- M5-TASK-001 Content Library Model & Navigation Foundation: QA PASSED
- M5-TASK-002 Build Verification Infrastructure: QA PASSED
- M5-TASK-005 Player Safe Shell Fallback: USER RUNTIME PASSED / QA ACCEPTED
- Historical runtime tasks:
  - M5-TASK-003 Player Runtime Crash Fix: PARTIAL
  - M5-TASK-004 Player Launch Flow Fix: RUNTIME FAILED
- Scope delivered:
  - Safe mock/local content library foundation
  - Live / Movies / Series navigation clarity
  - Home to Detail route foundation
  - Detail to Player safe shell route
  - GitHub Actions Android build verification workflow
- Runtime result:
  - User retest PASSED
  - Detail to Player safe shell opens
  - Back navigation works
- Guardrails preserved:
  - Mock/local data only
  - No backend integration implemented
  - No provider/API integration implemented
  - No payment implementation
  - No production auth changes
  - No protected system rewrite
  - Legal/compliance risk: none detected

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

Documentation Memory should rerun final consistency review.

If Documentation Memory passes, Director may decide whether to lock M5.

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
- QA: PASSED
- Final Android TV Runtime Test: PASSED
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
- Active task: `M5-TASK-001 Content Library Model & Navigation Foundation`
- Opened by Director after user command.
- Scope:
  - Safe mock content library foundation
  - Live / Movies / Series navigation clarity
  - Category rows
  - Detail-screen foundation if needed
  - Content-selected Player route foundation if needed
- Guardrails:
  - Mock/local data only
  - No real backend
  - No provider/API integration
  - No payment
  - No production auth changes
  - No protected system rewrite

## Next Step

Developer should implement `M5-TASK-001` from `docs/NEXT_TASK.md`.

# Nexora TV — NEXT_TASK

## Current Status

Active task is QA blocked.

## Active Milestone

`M5 Content Library & Navigation Expansion`

## Active Task

`M5-TASK-001 Content Library Model & Navigation Foundation`

## Current Task Status

`QA BLOCKED`

Blocker:

`BUILD_RUNTIME_TEST_EVIDENCE_MISSING`

## Objective

Create a safe mock-data content library foundation and improve content navigation clarity without touching protected systems.

## Implementation Status

Developer implementation exists on `main`.

Static repo review:

- Mock/local content library added
- Home content rows updated
- Detail placeholder added
- Additive detail route wiring added
- Existing Player route reused
- No protected system rewrite detected
- No legal/compliance risk detected

## Blocking Issue

Actual Android build/runtime smoke test evidence is missing.

Known constraints:

- Current execution environment cannot clone/build/run Android app
- No usable Android SDK / Gradle runtime confirmed in current environment
- No emulator/device access available
- No GitHub Actions Android build workflow found
- Gradle wrapper is not present in repo

## Required Evidence To Clear Blocker

One of the following is required:

1. User runs Android Studio local build/runtime test and reports result
2. Developer runs valid Android build/runtime test and reports result
3. Approved CI/build workflow is added, run, and passes

Required smoke flow:

- Build app
- Launch app
- Splash → Login → Activation
- Enter `demo123`
- Continue to Home
- Switch Home / Live TV / Movies / Series / Settings
- Select playable mock content
- Confirm Detail screen opens
- Press Play Mock
- Confirm Player opens
- Test Back navigation

## Scope OUT

- No real backend
- No real provider/API integration
- No payment
- No production auth changes
- No illegal IPTV playlist
- No unauthorized streams
- No DRM bypass
- No token/cookie handling
- No protected system rewrite
- No UI overhaul
- No large architecture rewrite

## Next Required Action

Provide actual build/runtime smoke test evidence, or approve a separate build infrastructure task.

## Return To Director With

```text
Runtime Test Result:
PASSED / FAILED

Build:
PASSED / FAILED

Device/Emulator/CI:
<short info>

Issues:
- none / issue list

Notes:
- short notes
```

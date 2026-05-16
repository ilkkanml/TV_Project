# Nexora TV — NEXT_TASK

## Current Status

Runtime smoke test evidence required.

## Active Milestone

`M5 Content Library & Navigation Expansion`

## Active Task

`M5-TASK-001 Content Library Model & Navigation Foundation`

## Current Task Status

`RUNTIME SMOKE TEST PENDING`

## Completed Support Task

`M5-TASK-002 Build Verification Infrastructure`

Status: `QA PASSED`

Result:

- GitHub Actions Android build workflow added
- Build verification documentation added
- CI green screenshot evidence accepted by QA
- Runtime smoke test remains separate

## M5-TASK-001 Implementation Status

Developer implementation exists on `main`.

Static repo review:

- Mock/local content library added
- Home content rows updated
- Detail placeholder added
- Additive detail route wiring added
- Existing Player route reused
- No protected system rewrite detected
- No legal/compliance risk detected

## Remaining Blocker

`RUNTIME_SMOKE_TEST_EVIDENCE_MISSING`

Required smoke flow:

1. Build/install app from current main/debug APK.
2. Launch app.
3. Splash → Login → Activation.
4. Enter `demo123`.
5. Continue to Home.
6. Switch Home / Live TV / Movies / Series / Settings.
7. Select playable mock content.
8. Confirm Detail screen opens.
9. Press Play Mock.
10. Confirm Player opens.
11. Test Back navigation.

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

## Return To Director With

```text
Runtime Smoke Test:
PASSED / FAILED

Device/Emulator:
<short info>

Flow Checked:
- Splash → Login → Activation
- demo123 activation
- Home menu sections
- Detail screen
- Play Mock → Player
- Back navigation

Issues:
- none / issue list

Notes:
- short notes
```

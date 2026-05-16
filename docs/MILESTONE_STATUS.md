# Nexora TV — Milestone Status

## Current Active Milestone

`M4 Auth & Device Activation Foundation`

Status: `ACTIVE / USER TEST PASSED / QA PENDING`

M4 goal:

Build the safe foundation for device identity plus password activation without production backend dependency.

M4 allowed scope:

- Device identity placeholder
- Activation password screen polish
- Mock auth validation
- Active/inactive device state placeholder
- Subscription expiration placeholder
- Safe loading/empty/error states
- Documentation-backed test checklist

M4 not allowed:

- Production backend implementation
- Stripe/payment production flow
- Admin panel
- Real IPTV provider integration
- Playback Core rewrite
- Auth system rewrite beyond approved foundation placeholder
- Illegal IPTV source logic
- DRM bypass
- Token/cookie theft
- Unauthorized stream scraping

## Last Locked Milestone

`M3 Premium UI Expansion`

Status: `LOCKED`

M3 locked the safe runtime premium Home UI baseline:

- HomeScreen safe runtime build
- Premium TV layout polish
- Category menu flow
- Poster row interaction
- Dynamic background direction
- Runtime checklist validation
- User Test: PASSED
- QA Tester: PASSED
- Final Android TV Runtime Test: PASSED
- Sync/Re-compare: DONE
- Main merge: DONE

## Completed / Locked

### M1 Foundation

Status: `LOCKED`

### M2 Playback Expansion

Status: `LOCKED`

### M3 Premium UI Expansion

Status: `LOCKED`

## Active Task

`M4-TASK-001 Auth & Device Activation Shell`

Assigned Agent: `QA_TESTER`

User Test: `PASSED`

Validated by user:

- Splash → Login → Connect → Device Activation screen: PASS
- Device ID visible: PASS
- Blank Activate → error state: PASS
- Wrong password → error state: PASS
- inactive → inactive state: PASS
- expired → expired state: PASS
- demo123 → active state: PASS
- Active → Continue → Home: PASS
- Crash: none reported

## Protection / Compliance Record

Protected systems must remain stable:

- Playback Core
- Backend API
- Hidden Backend API

Auth work is limited to safe foundation placeholder unless Director explicitly expands scope.

Legal/compliance rule remains active.

No illegal IPTV source logic, DRM bypass, token/cookie theft, or unauthorized stream scraping may be added.
# Nexora TV — FRONTEND FREEZE 01

Status: FREEZE CANDIDATE
Updated: 2026-05-21

## Purpose

Freeze the current frontend direction before early APK testing so new code does not stack on top of unstable or untested areas.

This is not a milestone lock.

This file creates a working checkpoint for the first Downloader APK smoke test.

## Current Distribution Target

Primary target:

- Direct APK install
- Downloader code distribution
- Free early access preview
- Android TV / Fire TV first

Not current target:

- Google Play release
- Store compliance polish
- Payment/subscription enforcement
- Production backend release

## Freeze Rule

Until the first APK smoke test is green:

- No new feature work
- No visual redesign
- No large refactor
- No component split unless required to fix build
- No playback rewrite
- No provider expansion
- No backend expansion
- No store/release pipeline work

Allowed work:

- Build fix
- Crash fix
- Broken route fix
- Broken focus fix
- Manifest/resource fix
- Icon/resource replacement
- Small copy correction
- User data/profile flow restoration

## Code Hygiene Rule

Every patch must be small and targeted.

Do not:

- create duplicate components
- leave replaced files active
- keep stale refactor leftovers
- add wrappers around broken code
- change many systems in one commit

If a file is replaced, the old file must be removed or neutralized.

## Protected Areas

Do not rewrite these unless build is blocked and Director approves:

- PlayerScreen / Media3 lifecycle
- navigation routes
- profile store
- loaded catalog session
- cinematic backdrop
- home menu structure

## Current Known Recent Fixes

- Manifest simplified for direct APK distribution.
- MediaSetupComponents.kt neutralized as stale refactor leftover.
- MediaSourceSetupScreen.kt restored with a simple source/profile form.
- Player remote key behavior was improved for TV input.

## Required Local Test Order

1. Git pull latest main.
2. Sync Gradle.
3. Clean Project.
4. Rebuild Project.
5. Run on Television emulator or Android TV / Fire TV device.
6. Test frontend route flow.
7. Report exact failing screen if any.

## Frontend Smoke Test Checklist

Use this exact order:

```text
Splash
Language
Activation
Profiles
Add User / Media Source Setup
Home
Movies
Series
Live
Settings
Detail
Player
Back navigation
```

Expected result:

- No crash
- No black screen lock
- No route dead-end
- No focus trap that blocks navigation
- Player screen opens when a playable item is selected

## Test Result Template

```text
Build: OK / FAIL
Install: OK / FAIL
Splash: OK / FAIL
Language: OK / FAIL
Activation: OK / FAIL
Profiles: OK / FAIL
Source Setup: OK / FAIL
Home: OK / FAIL
Detail: OK / FAIL
Player: OK / FAIL
Back: OK / FAIL
Notes:
```

## Freeze Status Rules

`FREEZE CANDIDATE` means code is ready for local smoke test.

`USER TEST PASSED` means user tested locally and confirmed the checklist.

`QA PASSED` means QA review found no blocker.

`LOCKED` requires:

- User Test Passed
- QA Passed
- Director approval

## Current Status

Status remains:

`FREEZE CANDIDATE — NOT LOCKED`

Reason:

- Build evidence is not confirmed yet.
- User smoke test is not completed yet.
- QA has not reviewed yet.

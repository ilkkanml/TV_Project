# Nexora TV — NEXT_TASK

Status: Runtime next-action entrypoint
Updated: 2026-05-21

## Current Status

Current active checkpoint:

`NEXORA_FRONTEND_FREEZE_01` — FREEZE CANDIDATE

This is not LOCKED.

No new milestone is open.

## Current Distribution Target

Primary target:

- Direct APK install
- Downloader code distribution
- Free early access preview
- Android TV / Fire TV first

Not current target:

- Google Play release
- Store compliance polish
- Payment enforcement
- Production backend release

## Immediate Required Work

Do not open M15 yet.

First complete the freeze smoke test:

1. Git pull latest main.
2. Sync Gradle.
3. Clean Project.
4. Rebuild Project.
5. Run on Television emulator or Android TV / Fire TV device.
6. Test frontend route flow.
7. Report exact failing screen if any.

## Frontend Smoke Test Order

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

## Allowed Work During Freeze

Allowed only if required:

- Build fix
- Crash fix
- Broken route fix
- Broken focus fix
- Manifest/resource fix
- Icon/resource replacement
- Small copy correction
- User data/profile flow restoration

## Not Allowed During Freeze

- New feature work
- Visual redesign
- Large refactor
- Provider expansion
- Backend expansion
- Payment/subscription enforcement
- Store release work
- Playback rewrite
- Component splitting unless required to fix build

## Current Build Policy

Every patch must be small and targeted.

If a file is replaced, the old file must be removed or neutralized.

No duplicated component trees.

No stale refactor leftovers.

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

## Current Operating Rule

Do not claim PASSED until user test/build evidence exists.

Do not claim LOCKED without QA and Director approval.

## Next Step

User should pull latest main and run the frontend smoke test.

If build fails, fix only the first blocking build error.

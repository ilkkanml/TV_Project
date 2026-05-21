# Nexora TV — HANDOFF

Status: ACTIVE HANDOFF
Updated: 2026-05-21

## Current Checkpoint

`NEXORA_FRONTEND_FREEZE_01` — FREEZE CANDIDATE

This is not LOCKED.

No active milestone is open.

## Current Target

Primary target:

- Direct APK install
- Downloader code distribution
- Free early access preview
- Android TV / Fire TV first

Not current target:

- Store release
- Payment enforcement
- Production backend release

## Current Director Decision

Freeze method is active.

Do not add new features until the first frontend smoke test is completed.

Do not open M15 yet.

## Immediate Next Action

User should pull latest `main` and run:

1. Sync Gradle.
2. Clean Project.
3. Rebuild Project.
4. Run on Television emulator or Android TV / Fire TV device.
5. Report the first blocking error if any.

## Smoke Test Order

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

## Allowed During Freeze

- Build fix
- Crash fix
- Broken route fix
- Broken focus fix
- Manifest/resource fix
- Icon/resource replacement
- Small copy correction
- Profile flow restoration

## Not Allowed During Freeze

- New feature work
- Visual redesign
- Large refactor
- Backend expansion
- Store release work
- Playback rewrite
- Component splitting unless required to fix build

## Recent Freeze Notes

- `docs/NEXORA_FRONTEND_FREEZE_01.md` created.
- `docs/NEXT_TASK.md` aligned to frontend freeze smoke test.
- `docs/PROJECT_MEMORY.md` aligned to frontend freeze checkpoint.
- `MediaSetupComponents.kt` neutralized as stale refactor leftover.
- `MediaSourceSetupScreen.kt` restored with simple profile/source form.
- Manifest simplified for direct APK distribution.
- Player remote key behavior improved for TV input.

## Icon Note

Use the user-provided icon image.

Do not use the generated alternate icon.

Prepared icon assets should be copied into `app/src/main/res` before re-enabling manifest icon references:

```text
android:icon="@mipmap/ic_launcher"
android:roundIcon="@mipmap/ic_launcher_round"
```

## Lock Rules

Do not say PASSED until user test/build evidence exists.

Do not say LOCKED without QA and Director approval.

## Next Session Read Order

1. `docs/NEXORA_FRONTEND_FREEZE_01.md`
2. `docs/NEXT_TASK.md`
3. `docs/PROJECT_MEMORY.md`
4. `docs/HANDOFF.md`
5. `docs/CODE_HYGIENE_DIRECTIVE.md`
6. `docs/REPO_STRUCTURE_STANDARD.md`
7. `docs/PROTECTED_SYSTEMS.md`

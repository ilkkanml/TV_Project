# Nexora TV — Project Memory

Status: ACTIVE MEMORY
Updated: 2026-05-21

## Purpose

Compact memory source for continuing Nexora TV development without relying on chat history.

Repository docs are the source of truth.

## Project Name

Nexora TV

## Repository

https://github.com/ilkkanml/TV_Project.git

## Product Type

Legal Android TV / Fire TV media player ecosystem.

## Current Active Checkpoint

`NEXORA_FRONTEND_FREEZE_01` — FREEZE CANDIDATE

This is not LOCKED.

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

## Current Rule

Do not open M15 yet.

Do not add new features until the first frontend smoke test is completed.

Allowed only if required:

- Build fix
- Crash fix
- Broken route fix
- Broken focus fix
- Manifest/resource fix
- Icon/resource replacement
- Small copy correction
- User data/profile flow restoration

Not allowed during freeze:

- New feature work
- Visual redesign
- Large refactor
- Provider expansion
- Backend expansion
- Payment/subscription enforcement
- Store release work
- Playback rewrite
- Component splitting unless required to fix build

## Required Next Action

User should pull latest `main` and run the frontend smoke test.

Test order:

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

Report using:

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

## Current Code Reality

Recent freeze-related fixes:

- `docs/NEXORA_FRONTEND_FREEZE_01.md` created.
- `docs/NEXT_TASK.md` points to frontend freeze smoke test.
- `MediaSetupComponents.kt` neutralized as stale refactor leftover.
- `MediaSourceSetupScreen.kt` restored with a simple profile/source form.
- Manifest simplified for direct APK distribution.
- Player remote key behavior improved for TV input.

Important:

- User-provided app icon must be used.
- Generated alternative icon must not replace the user-provided image.
- Icon assets were prepared separately from the user's image and should be copied into `app/src/main/res` before re-enabling manifest icon references.

## Legal Boundary

Nexora TV is a legal media player platform.

It must not provide, sell, host, relay, scrape, bypass, or bundle unauthorized content.

Allowed:

- Mock data
- Permissioned test streams
- Public demo streams
- User-owned or legally authorized sources
- Future legal provider/API integrations only with explicit approval

## Protected Systems

Do not rewrite impulsively:

- Playback core / Media3 lifecycle
- TV navigation system
- Profile/source persistence
- Loaded catalog session
- Cinematic backdrop
- Home menu structure

## Source-of-Truth Read Order

1. `docs/NEXORA_FRONTEND_FREEZE_01.md`
2. `docs/NEXT_TASK.md`
3. `docs/PROJECT_MEMORY.md`
4. `docs/CODE_HYGIENE_DIRECTIVE.md`
5. `docs/REPO_STRUCTURE_STANDARD.md`
6. `docs/DEPARTMENT_MODEL_STANDARD.md`
7. `docs/PROTECTED_SYSTEMS.md`

## User Preference

- Turkish responses
- Minimal explanations
- Direct instructions
- Continuous progress
- Freeze method during unstable build/test stage
- No code bloat
- Do not say PASSED/LOCKED without required evidence

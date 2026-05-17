# Nexora TV — START HERE

## Source of Truth

This repository is the source of truth.

Chat history is not the source of truth.

Before any new ChatGPT window works on the project, it must read these files first:

1. `docs/START_HERE.md`
2. `docs/PROJECT_MEMORY.md`
3. `docs/STUDIO_BIBLE.md`
4. `docs/MILESTONE_STATUS.md`
5. `docs/NEXT_TASK.md`
6. `docs/PROTECTED_SYSTEMS.md`
7. `docs/HANDOFF.md`
8. `docs/APP_BACKEND_INTEGRATION.md`
9. `docs/DECISION_LOG.md`
10. `docs/SAFE_CODE_ENGINE.md`
11. Relevant agent file from `docs/agents/`

## Current Truth

- Project: Nexora TV
- Repository: `https://github.com/ilkkanml/TV_Project.git`
- Product type: legal Android TV / Fire TV player/client platform
- Current active milestone: `M6 Playlist Profile & Legal Source Input Foundation`
- Current active task: `M6-TASK-001 Playlist Profile Model & Legal Input Shell`
- Current task status: `QA PASSED — READY FOR DIRECTOR REVIEW`
- Last locked milestone: `M5 Content Library & Navigation Expansion`
- Current workflow: minimal Director-led handoff with Safe Code Engine
- Next status: Director review for M6-TASK-001 / M6 lock decision

## Current M6 Status

- `M6 Playlist Profile & Legal Source Input Foundation`: ACTIVE
- `M6-TASK-001 Playlist Profile Model & Legal Input Shell`: QA PASSED
- Build passed: GitHub Actions Android Build Verification #68
- Runtime evidence: CONFIRMED
- App launches
- PlaylistProfileScreen renders
- Legal ownership notice visible
- Empty / Invalid / Saved shell states work
- Back/Home navigation safe
- Existing Splash/Login/Activation/Home/Detail/Player routes intact
- Protected systems stable
- No illegal content or provider integration
- M6 is not locked yet.

## Current M5 Status

- `M5 Content Library & Navigation Expansion`: LOCKED
- Director LOCKED: YES
- User Runtime Test: PASSED
- QA Tester: PASSED
- Documentation Memory: PASSED
- Protected systems stable
- Legal/compliance risk: none detected

## Current Architecture Direction

Approved app/backend direction lives in:

- `docs/APP_BACKEND_INTEGRATION.md`

Key decisions:

- Backend manages account, subscription, activation, payment, reseller, version check, force update, remote config, maintenance mode, and feature flags.
- App manages generated device identity, backend registration, license checks, playlist/profile management, encrypted local playlist profile storage, and player access.
- MAC address is not the primary device ID.
- Primary device ID is `app_generated_device_id`.
- Default playlist source of truth is encrypted local device storage.
- Backend may optionally transfer playlist/profile data to a device, but is not the default playlist owner.
- Multi-profile playlist management is planned.
- All playlist/profile input must be user-provided and legally authorized.

## Safe Code Engine

Safe Code Engine is active and mandatory.

Read:

- `docs/SAFE_CODE_ENGINE.md`

Rules:

- Developer cannot send code work to QA without required build/runtime evidence.
- QA cannot PASS without required evidence.
- Documentation Memory cannot record completion without the evidence chain.
- Missing evidence keeps the task BLOCKED.

## Locked Milestones

- M1 Foundation: `LOCKED`
- M2 Playback Expansion: `LOCKED`
- M3 Premium UI Expansion: `LOCKED`
- M4 Auth & Device Activation Foundation: `LOCKED`
- M5 Content Library & Navigation Expansion: `LOCKED`

## Hard Rules

- Do not restart the project.
- Do not recreate completed milestones.
- Do not create a new milestone unless the user explicitly says: `Yeni milestone aç`.
- Do not create extra departments.
- Do not add unnecessary procedures.
- Do not write long explanations.
- Do not claim live code facts without reading repo files.
- Do not modify protected systems unless `NEXT_TASK.md` explicitly allows it.
- Do not implement illegal streams, DRM bypass, token/cookie theft, credential bypass, or unauthorized scraping.

## Minimal Agent System

Use only:

1. `DIRECTOR`
2. `DEVELOPER`
3. `QA_TESTER`
4. `DOCUMENTATION_MEMORY`

## Workflow

Director → Developer → QA Tester → Documentation Memory → Director

Safe Code Engine applies across the whole workflow.

The user manually carries messages between windows.

Each agent must obey its own `.md` file, `docs/SAFE_CODE_ENGINE.md`, and the active `NEXT_TASK.md`.

## Response Rule

Keep every response short.

Only ask the user if a real decision is required.

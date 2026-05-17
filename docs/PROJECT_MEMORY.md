# Nexora TV — Project Memory

## Purpose

This document is the compact memory source for new ChatGPT windows.

Chat history is disposable. Repository docs are the source of truth.

## Project Name

Nexora TV

## Repository

https://github.com/ilkkanml/TV_Project.git

## Product Type

Premium subscription-based Android TV / Fire TV player application.

## Core Rule

Nexora TV is a legal player platform.

It must not bundle pirated channels, illegal streams, unauthorized playlists, DRM bypass logic, token/cookie theft, credential bypass, or unauthorized scraping.

Allowed development sources:

- Mock data
- Test streams with permission
- Public demo streams
- User-owned licensed streams
- Legal provider/API integrations

## App / Backend Integration Direction

Approved direction document:

- `docs/APP_BACKEND_INTEGRATION.md`

Core decisions:

- Backend owns user account, subscription status, device activation, payment status, reseller system, version check, force update, remote config, maintenance mode, and feature flags.
- App owns device identity creation, backend registration, license/subscription check, playlist/profile management, encrypted local playlist profile storage, and player access when license is valid.
- MAC address is not the primary device ID.
- Primary ID is `app_generated_device_id`.
- Default playlist source of truth is device local encrypted storage.
- Backend is not the default source of truth for user playlist profiles.
- Web panel may optionally transfer a playlist/profile to a selected device.
- Longer cloud sync requires explicit user consent.
- App should support multi-profile playlist management.
- Initial source direction: M3U URL and Xtream Codes; Portal and Local/JSON later if approved.

## Safe Code Engine

Status: ACTIVE

Document:

- `docs/SAFE_CODE_ENGINE.md`

Rule:

- Developer cannot send code work to QA without required build/runtime evidence.
- QA cannot PASS without required evidence.
- Documentation Memory must record build/runtime evidence state.
- Missing evidence keeps the task BLOCKED.

## Window Transition Rule

Current window transition rule:

- Do not move to a new chat window right now.
- Continue M7 in the current window.
- After every future milestone is fully locked, Director must tell the user: `Yeni pencereye geçmek güvenli.`
- Each new milestone after that should start in a new chat window unless the user cancels it.

## Current Milestone Truth

### M1 Foundation

Status: LOCKED

### M2 Playback Expansion

Status: LOCKED

### M3 Premium UI Expansion

Status: LOCKED

### M4 Auth & Device Activation Foundation

Status: LOCKED

### M5 Content Library & Navigation Expansion

Status: LOCKED

### M6 Playlist Profile & Legal Source Input Foundation

Status: LOCKED

Lock evidence:

- Build Evidence: PASSED
- User Runtime Test: PASSED
- QA Tester: PASSED
- Documentation Memory: PASSED
- Protected systems stable
- Legal/compliance risk: none detected

### M7 Local Profile Persistence Foundation

Status: ACTIVE

Current task:

`M7-TASK-001 Local Profile Repository & Saved Profiles Shell`

Status:

`READY FOR DEVELOPER`

M7 purpose:

- safe local saved profile shell foundation
- saved profiles list shell
- active/selected profile state shell
- minimal add/edit/delete shell behavior
- preserve M6 profile input shell
- local/mock-safe flow only
- no production connection
- no unsafe sensitive-data persistence

## Current Active Work

Active milestone:

`M7 Local Profile Persistence Foundation`

Active task:

`M7-TASK-001 Local Profile Repository & Saved Profiles Shell`

Current task status:

`READY FOR DEVELOPER`

## Current Code Reality

Current repository contains:

- Android app module
- Kotlin / Jetpack Compose setup
- Android TV launcher manifest
- Splash → Login → Activation → Home → Detail → Player navigation
- Safe runtime HomeScreen build
- DeviceActivationScreen mock/local activation shell
- Mock/local content library foundation
- Detail screen foundation
- Safe Player shell fallback
- GitHub Actions Android build verification workflow
- Nexora color/theme foundation
- Playlist profile model/screen shell from M6
- Playlist profile route wired in app navigation

## Minimal Agent Workflow

Use only:

1. DIRECTOR
2. DEVELOPER
3. QA_TESTER
4. DOCUMENTATION_MEMORY

Safe Code Engine applies to every code task.

No large department structure unless explicitly requested.

## User Preference

- Minimal explanations
- Direct instructions
- Continuous progress
- Ask only critical decision questions
- Do not restart planning
- Do not recreate milestones
- Keep docs updated so new chats can continue
- Notify the user when it is safe to switch to a new chat window after milestone lock
- Start each new future milestone in a new chat window unless user cancels it

## Important Instruction For Future ChatGPT Sessions

Do not restart from zero.

Continue from the active milestone/task in runtime docs.

Before work, read:

- `docs/START_HERE.md`
- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/PROTECTED_SYSTEMS.md`
- `docs/HANDOFF.md`
- `docs/SAFE_CODE_ENGINE.md`
- `docs/APP_BACKEND_INTEGRATION.md`
- `docs/DECISION_LOG.md`
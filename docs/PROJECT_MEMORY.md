# Nexora TV — Project Memory

## Purpose

This document is the compact memory source for new ChatGPT windows.

Chat history is disposable. Repository docs are the source of truth.

## Project Name

Nexora TV

## Repository

https://github.com/ilkkanml/TV_Project.git

## Product Type

Premium subscription-based Android TV / Fire TV IPTV player application.

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
- Android ID, model, platform, app version, and install metadata are secondary signals.
- Default playlist source of truth is device local encrypted storage.
- Backend is not the default source of truth for user playlist profiles.
- Web panel may optionally transfer a playlist/profile to a selected device.
- Longer cloud sync requires explicit user consent.
- App should support multi-profile playlist management.
- Initial source direction: M3U URL and Xtream Codes; Portal and Local/JSON later if approved.
- License checks should happen on app launch, before player access, and during active use at intervals.
- Offline tolerance direction: 6 hours soft grace, 24 hours maximum hard limit after previous valid license check.
- API format direction: JSON responses and Bearer token.

## Primary Platforms

- Android TV
- Fire TV

Future platforms:

- Samsung Tizen
- LG webOS
- Apple tvOS

## Brand Direction

- Futuristic
- Cinematic
- Premium
- Dark metallic
- Deep neon cyan accent
- Electric blue secondary accent

## UX Direction

- Netflix-like familiarity
- IPTV practicality
- Large cinematic posters
- Remote-first navigation
- Dynamic blurred artwork background
- Scale plus glow focus behavior
- Premium cinematic transitions
- Calm, clean, high-end TV experience

## Locked Product Decisions

- Brand: Nexora TV
- Package direction: `com.nexora.tv`
- Platform priority: Android TV / Fire TV
- Homepage hero: auto-sliding featured content
- Hero timing: 8 seconds
- First home row: Continue Watching
- Poster style: large cinematic posters
- Live TV layout: Netflix-like poster rows
- Live TV playback: instant fullscreen
- Movies and Series playback: detail page before playback
- Live TV transition: ultra fast hard cut
- VOD transition: smooth fade
- Player overlay timeout: 5 seconds
- Splash style: cinematic animated
- Homepage background: dynamic blurred artwork
- Focus effect: scale plus glow
- Detail preview: muted autoplay preview

## Safe Code Engine

Status: ACTIVE

Document:

- `docs/SAFE_CODE_ENGINE.md`

Rule:

- Developer cannot send code work to QA without required build/runtime evidence.
- QA cannot PASS without required evidence.
- Documentation Memory must record build/runtime evidence state.
- Missing evidence keeps the task BLOCKED.

## Current Milestone Truth

### M1 Foundation

Status: LOCKED

### M2 Playback Expansion

Status: LOCKED

### M3 Premium UI Expansion

Status: LOCKED

M3 locked the safe runtime HomeScreen premium UI baseline.

### M4 Auth & Device Activation Foundation

Status: LOCKED

M4 locked the safe device activation foundation.

### M5 Content Library & Navigation Expansion

Status: LOCKED

Director LOCKED: YES

Lock evidence:

- User Runtime Test: PASSED
- QA Tester: PASSED
- Documentation Memory: PASSED
- Protected systems stable
- Legal/compliance risk: none detected

### M6 Playlist Profile & Legal Source Input Foundation

Status: ACTIVE

Current task:

`M6-TASK-001 Playlist Profile Model & Legal Input Shell`

Status:

`BLOCKED — BUILD/RUNTIME EVIDENCE REQUIRED`

M6 current blocker:

- Build compile evidence missing
- Runtime render evidence missing
- QA Tester result: FAIL until evidence is provided

Required evidence:

- Build command/result: `./gradlew :app:assembleDebug`
- Runtime confirmation that profile screen renders
- Legal notice visible
- Empty/invalid/saved local shell states checked
- Back/Home navigation remains safe

M6 purpose:

- user-managed playlist profile foundation
- legal source input shell
- M3U URL direction
- Xtream Codes direction
- local/mock-safe data flow
- no bundled content
- no real provider integration yet

## Current Active Work

Active milestone:

`M6 Playlist Profile & Legal Source Input Foundation`

Active task:

`M6-TASK-001 Playlist Profile Model & Legal Input Shell`

Current task status:

`BLOCKED — BUILD/RUNTIME EVIDENCE REQUIRED`

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
- Playlist profile model/screen shell added for M6
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
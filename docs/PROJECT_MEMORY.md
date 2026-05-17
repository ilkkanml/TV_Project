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
- App should support multi-profile playlist management

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

### M4 Auth & Device Activation Foundation

Status: LOCKED

### M5 Content Library & Navigation Expansion

Status: LOCKED

### M6 Playlist Profile & Legal Source Input Foundation

Status: LOCKED

### M7 Local Profile Persistence Foundation

Status: LOCKED

### M8 TV Navigation & Access Polish

Status: LOCKED

Task:

`M8-TASK-001 Profile Access, Backstack & Login Field Safety Polish`

Status: PASSED / COMPLETED

Lock evidence:

- PR #10 merged to main
- Merge commit: `303008e3f38cf9ba94ab7f4e16dd4bbcc3190e81`
- Build Evidence: Android Build Verification #109 success
- Runtime Evidence: accepted
- QA Result: PASS
- Documentation Memory: PASSED
- Director LOCKED: YES
- Splash cleanup excluded from delivered scope
- Protected systems clear
- Legal/compliance clear

### M9 Startup Flow & Session Entry Polish

Status: ACTIVE

Current task:

`M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish`

Status: QA PASSED

Evidence:

- PR #11 exists
- PR #11 merge status: NOT MERGED / awaiting Director decision
- Changed files:
  - `app/src/main/java/com/nexora/tv/ui/screens/SplashScreen.kt`
- Build Evidence: Android Build Verification #135 success
- Runtime Evidence: Developer runtime evidence accepted
- QA Result: PASS
- Blockers: none
- Regression risk: none
- Protected systems clear
- Legal/compliance clear

Current next action:

Return to Director for PR #11 merge / M9 lock decision.

M9 is not Documentation Memory PASSED.

M9 is not LOCKED.

Director LOCKED is not recorded for M9.

## Current Code Reality

- Local profile repo and screens preserved
- Existing navigation patterns reused
- M9 changed file: `app/src/main/java/com/nexora/tv/ui/screens/SplashScreen.kt`
- Minimal additive startup/navigation entry polish
- No playback/provider/backend/storage systems touched by M9
- Safe Code Engine required and evidence accepted for M9-TASK-001

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
- Keep docs updated so new chats can continue
- Notify the user when it is safe to switch to a new chat window after milestone lock

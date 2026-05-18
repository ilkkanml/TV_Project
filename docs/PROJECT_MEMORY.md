# Nexora TV — Project Memory

## Purpose

This document is the compact memory source for new ChatGPT windows.

Chat history is disposable. Repository docs are the source of truth.

## Project Name

Nexora TV

## Repository

https://github.com/ilkkanml/TV_Project.git

## Product Type

Legal Core Media Player Ecosystem.

- `TV_Project` = Android TV / Fire TV first client
- `TV_Project_Platform` = Core Account / Device / License / Admin / Remote Config / App Version / Profile Transfer center

## Current Runtime Truth

- Current active milestone: M10 Ecosystem Alignment & Client Integration Contract
- Current active task: M10-TASK-001 Core Ecosystem Contract & Cross-Repo Roadmap Alignment
- M10 status: ACTIVE
- M10-TASK-001 status: READY FOR ECOSYSTEM INTEGRATION / SYSTEMS ARCHITECT
- Last locked milestone: M9 Startup Flow & Session Entry Polish
- M9 status: LOCKED
- M10 is not PASSED
- M10 is not LOCKED
- Required next action: Ecosystem Integration / Systems Architect

Runtime truth priority:

1. `docs/MILESTONE_STATUS.md`
2. `docs/NEXT_TASK.md`
3. `docs/HANDOFF.md`
4. `docs/PROJECT_MEMORY.md`
5. `docs/START_HERE.md`
6. Agent files

## Lean Workflow Rule

Avoid unnecessary procedure.

Use the shortest safe workflow:

1. Department report when useful
2. Director decision
3. Builder only if implementation is approved
4. QA only for code/runtime/release risk
5. Documentation only when runtime truth, milestone status, source-of-truth contract, or major decision changes
6. Milestone final docs can be batched

Do not run Documentation Memory for every minor review.

Do not update CHANGELOG or DECISION_LOG after every small department report.

Director may handle simple documentation updates directly in the main Director thread.

## Core Legal Rule

Nexora is a legal player/client platform.

It does not provide content, channels, broadcasts, bundled media, circumvention tools, unauthorized source access, credential sharing support, or restreaming behavior.

Allowed development sources:

- Mock data
- Test streams with permission
- Public demo streams
- User-owned licensed streams
- Legal provider/API integrations

## M10 Ecosystem Direction

M10 exists to align Android client and Platform backend before database/API/backend bridge work begins.

M10 contract docs:

- `docs/ECOSYSTEM_CONTRACT.md`
- `docs/CLIENT_INTEGRATION_CONTRACT.md`
- `docs/DEVICE_IDENTITY_POLICY.md`
- `docs/LEGAL_PUBLIC_LANGUAGE.md`
- `docs/PLATFORM_ANDROID_ROADMAP.md`
- `docs/milestones/M10_ECOSYSTEM_ALIGNMENT_CLIENT_INTEGRATION_CONTRACT.md`

Core M10 rules:

- Platform backend is source of truth for account/device/license/config/app version.
- Android app is the first client.
- Real hardware MAC address is not the primary device ID.
- Use app-generated device/install GUID and backend-assigned `platform_device_id`.
- First app remains free until final release level.
- Payment/subscription enforcement is inactive during early/free launch.
- Contract comes before database/API/Android bridge implementation.

## M10 Contract Hardening Record

`docs/CLIENT_INTEGRATION_CONTRACT.md` has been hardened for M10 with:

- Android ↔ Platform responsibility table
- Endpoint contract draft
- Request/response examples
- Error state matrix
- Session/token behavior placeholders
- Remote config schema boundary
- Profile transfer MVP flow
- Free early launch behavior
- Legal media player boundary

This is documentation-only.

No app code, backend code, database implementation, payment enforcement, provider integration, or protected-system change is approved by this record.

M10 remains ACTIVE.

M10 is not PASSED.

M10 is not LOCKED.

Next recommended review:

- Backend Engineer / Database Architect feasibility report only

## Department Boot Protocol

Department boot docs:

- `docs/DEPARTMENT_BOOT_PROTOCOL.md`
- `docs/DEPARTMENT_ROLE_CARDS.md`

Every department must read boot protocol, role cards, runtime docs, protected systems, Safe Code Engine, and its own agent file before reporting.

Departments report only. Director decides.

Ecosystem Integration owns cross-repo alignment.

## Safe Code Engine

Status: ACTIVE

Document:

- `docs/SAFE_CODE_ENGINE.md`

Rule:

- Developer cannot send code work to QA without required build/runtime evidence.
- QA cannot PASS without required evidence.
- Documentation Memory must record build/runtime evidence state.
- Missing evidence keeps the task BLOCKED.

## Locked Milestones

- M1 Foundation: LOCKED
- M2 Playback Expansion: LOCKED
- M3 Premium UI Expansion: LOCKED
- M4 Auth & Device Activation Foundation: LOCKED
- M5 Content Library & Navigation Expansion: LOCKED
- M6 Playlist Profile & Legal Source Input Foundation: LOCKED
- M7 Local Profile Persistence Foundation: LOCKED
- M8 TV Navigation & Access Polish: LOCKED
- M9 Startup Flow & Session Entry Polish: LOCKED

M9 lock evidence:

- PR #11 merged to main
- Merge commit: `3ebb0e2d7426c5695af86547c7f195a734c28c6a`
- Changed file: `app/src/main/java/com/nexora/tv/ui/screens/SplashScreen.kt`
- Build Evidence: Android Build Verification #144 success
- Runtime Evidence: accepted
- QA Result: PASS
- Documentation Memory: PASSED
- Director LOCKED: YES
- Protected systems clear
- Legal/compliance clear

## Current Code Reality

- Local profile repo and screens preserved
- Existing navigation patterns reused
- M9 changed only Splash startup/backstack behavior
- No playback/provider/backend/storage systems touched by M9
- M10 is documentation/contract alignment; app code must remain unchanged unless a future task explicitly allows implementation

## User Preference

- Turkish responses
- Minimal explanations
- Direct instructions
- Continuous progress
- Keep docs updated so new chats can continue
- Do not say PASSED/LOCKED without required evidence
- Avoid unnecessary procedure and repeated documentation loops

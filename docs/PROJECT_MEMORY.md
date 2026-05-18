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

- Current active milestone: None
- Current active task: None
- Last locked milestone: M10 Ecosystem Alignment & Client Integration Contract
- M10 status: LOCKED
- M10-TASK-001 status: PASSED / COMPLETED
- Previous locked milestone: M9 Startup Flow & Session Entry Polish
- M9 status: LOCKED
- Required next action: Director may open next milestone candidate: M11 Platform Source-of-Truth Audit

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

## M10 Lock Summary

M10 Ecosystem Alignment & Client Integration Contract is LOCKED.

Completed scope:

- Ecosystem alignment
- Client integration contract
- Contract hardening
- Security & Privacy review
- Legal Compliance review
- Backend/Database feasibility review
- Final documentation consistency check

M10 contract docs:

- `docs/ECOSYSTEM_CONTRACT.md`
- `docs/CLIENT_INTEGRATION_CONTRACT.md`
- `docs/DEVICE_IDENTITY_POLICY.md`
- `docs/LEGAL_PUBLIC_LANGUAGE.md`
- `docs/PLATFORM_ANDROID_ROADMAP.md`
- `docs/milestones/M10_ECOSYSTEM_ALIGNMENT_CLIENT_INTEGRATION_CONTRACT.md`
- `docs/reviews/M10_SECURITY_PRIVACY_REVIEW.md`
- `docs/reviews/M10_LEGAL_COMPLIANCE_REVIEW.md`

M10 guardrails preserved:

- App code unchanged
- Backend code unchanged
- Database implementation not approved
- Payment enforcement not approved
- Provider integration not approved
- Protected systems preserved
- Legal/compliance boundary preserved

## M11 Candidate

Next candidate milestone:

`M11 Platform Source-of-Truth Audit`

M11 should audit the Platform repo before implementation.

M11 should not start database/API/client implementation until Director opens the milestone and task explicitly.

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
- M10 Ecosystem Alignment & Client Integration Contract: LOCKED

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
- M10 was documentation/contract alignment only
- M10 did not change app code

## User Preference

- Turkish responses
- Minimal explanations
- Direct instructions
- Continuous progress
- Keep docs updated so new chats can continue
- Do not say PASSED/LOCKED without required evidence
- Avoid unnecessary procedure and repeated documentation loops

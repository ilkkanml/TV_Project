# Nexora TV — Decision Log

## Purpose

This file records approved Director-level project decisions.

Chat history is disposable. Repository docs are the source of truth.

## Decision Format

Each decision should include:

- Decision ID
- Status
- Summary
- Impact
- Related docs

---

## DECISION-001 — Product Legal Boundary

Status: `APPROVED`

Summary:

Nexora TV is a legal player/client platform. The app does not provide content. Users may use only playlist/provider access they are legally allowed to use.

Impact:

- App remains player/client focused
- No bundled content sources
- No backend content-provider role
- Compliance boundary remains protected

Related docs:

- `docs/PROJECT_MEMORY.md`
- `docs/APP_BACKEND_INTEGRATION.md`
- `docs/PROTECTED_SYSTEMS.md`

---

## DECISION-002 — App / Backend Responsibility Split

Status: `APPROVED`

Summary:

Backend manages account, subscription status, device activation, payment status, reseller system, version check, force update, remote config, maintenance mode, and feature flags.

App manages generated device identity, backend registration, license checks, playlist/profile management, encrypted local playlist profile storage, and player access.

Impact:

- Backend controls business/license/config state
- App controls local client/player behavior
- Integration implementation requires a future approved milestone/task

Related docs:

- `docs/APP_BACKEND_INTEGRATION.md`
- `docs/PROJECT_MEMORY.md`
- `docs/START_HERE.md`

---

## DECISION-003 — Device Identity

Status: `APPROVED`

Summary:

MAC address is not the primary device ID. The app should generate `app_generated_device_id` on first launch.

Secondary signals may include Android ID, device model, platform, app version, and install metadata.

Impact:

- Android TV and Fire TV use the same identity model
- `platform` separates Android TV / Fire TV where needed
- Device activation foundation can evolve safely in a later milestone

Related docs:

- `docs/APP_BACKEND_INTEGRATION.md`

---

## DECISION-004 — Playlist Profile Ownership

Status: `APPROVED`

Summary:

Default playlist source of truth is encrypted local device storage. Backend is not the default source of truth for user playlist profiles.

Web panel may optionally transfer a playlist/profile to a selected user device. Longer cloud sync requires explicit user consent.

Impact:

- App must support local encrypted profile storage in a future milestone
- Multi-profile management is planned
- Web playlist push is optional transfer helper, not default ownership

Related docs:

- `docs/APP_BACKEND_INTEGRATION.md`
- `docs/PROJECT_MEMORY.md`

---

## DECISION-005 — M5 QA Status

Status: `SUPERSEDED BY DECISION-006`

Summary:

M5-TASK-001 reached QA PASS after support tasks M5-TASK-002 and M5-TASK-005.

M5 moved to Documentation Memory review.

Related docs:

- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/CHANGELOG.md`

---

## DECISION-006 — M5 Lock Decision

Status: `APPROVED / LOCKED`

Summary:

M5 Content Library & Navigation Expansion is locked by Director decision.

Lock evidence:

- User Runtime Test: PASSED
- QA Tester: PASSED
- Documentation Memory: PASSED
- Protected systems stable
- Legal/compliance risk: none detected

Impact:

- M5 is complete and locked
- No active milestone remains
- No active task remains
- Next work requires Director to open a new milestone/task

Related docs:

- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/PROJECT_MEMORY.md`
- `docs/START_HERE.md`
- `docs/CHANGELOG.md`

---

## DECISION-007 — Safe Code Engine Process Rule

Status: `APPROVED`

Summary:

Safe Code Engine is now mandatory for all code-related work.

It is not a new agent or department. It is a required quality gate used by Director, Developer, QA Tester, and Documentation Memory.

Impact:

- Developer cannot return code work as DONE without build evidence or an exact blocker reason.
- Developer cannot recommend QA when required build/runtime evidence is missing.
- QA cannot PASS code work without required build/runtime evidence.
- Documentation Memory must record build/runtime evidence state and preserve BLOCKED status when evidence is missing.
- Tasks affecting screens, routes, navigation, focus, player UI, or visible user flows require runtime evidence.

Related docs:

- `docs/SAFE_CODE_ENGINE.md`
- `docs/agents/DIRECTATION_MEMORY.md`
- `docs/START_HERE.md`
- `docs/PROJECT_MEMORY.md`
- `docs/HANDOFF.md`
- `docs/CHANGELOG.md`

---

## DECISION-008 — M7 Lock Decision

Status: `APPROVED / LOCKED`

Summary:

M7 Local Profile Persistence Foundation milestone is now locked by Director.

Lock evidence:

- PR #9 merged to main
- Build Evidence: PASSED
- Runtime Evidence: CONFIRMED
- QA Tester: PASSED
- Documentation Memory: PASSED
- Protected systems stable
- Legal/compliance risk: none detected

Impact:

- No active milestone remains
- No active task remains
- Safe to switch to new milestone/chat window

Related docs:

- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/PROJECT_MEMORY.md`
- `docs/CHANGELOG.md`
- `docs/milestones/M7_LOCAL_PROFILE_PERSISTENCE_FOUNDATION.md`

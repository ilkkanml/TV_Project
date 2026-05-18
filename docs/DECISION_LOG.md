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

Status: `SUPERSEDED BY DECISION-013`

Summary:

Earlier app/backend responsibility split is superseded by M10 Core Media Player Ecosystem direction and Client Integration Contract.

Impact:

- Use M10 contract docs as source of truth for Android ↔ Platform split.

Related docs:

- `docs/CLIENT_INTEGRATION_CONTRACT.md`
- `docs/ECOSYSTEM_CONTRACT.md`

---

## DECISION-003 — Device Identity

Status: `APPROVED`

Summary:

MAC address is not the primary device ID. The app should generate `app_generated_device_id` on first launch. Backend assigns `platform_device_id` after activation.

Impact:

- Android TV and Fire TV use the same identity model.
- Real hardware MAC address must not be primary license identity.
- Device activation evolves under M10 contract direction.

Related docs:

- `docs/DEVICE_IDENTITY_POLICY.md`
- `docs/CLIENT_INTEGRATION_CONTRACT.md`

---

## DECISION-004 — Playlist Profile Ownership

Status: `APPROVED`

Summary:

Default playlist/profile source of truth is local device storage. Backend is not the default source of truth for user playlist profiles. Web/platform may provide temporary profile transfer.

Impact:

- Web profile transfer is helper flow, not permanent backend ownership.
- Longer cloud sync requires explicit future approval.

Related docs:

- `docs/CLIENT_INTEGRATION_CONTRACT.md`
- `docs/PROJECT_MEMORY.md`

---

## DECISION-005 — M5 QA Status

Status: `SUPERSEDED BY DECISION-006`

Summary:

M5-TASK-001 reached QA PASS after support tasks M5-TASK-002 and M5-TASK-005.

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

Safe Code Engine is mandatory for all code-related work.

Impact:

- Developer cannot return code work as DONE without build evidence or exact blocker reason.
- QA cannot PASS code work without required build/runtime evidence.
- Documentation Memory must record build/runtime evidence status.
- Visible flow changes require runtime evidence.

Related docs:

- `docs/SAFE_CODE_ENGINE.md`
- `docs/agents/DOCUMENTATION_MEMORY.md`
- `docs/START_HERE.md`
- `docs/PROJECT_MEMORY.md`
- `docs/HANDOFF.md`
- `docs/CHANGELOG.md`

---

## DECISION-008 — M7 Lock Decision

Status: `APPROVED / LOCKED`

Summary:

M7 Local Profile Persistence Foundation milestone is locked by Director.

Related docs:

- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/PROJECT_MEMORY.md`
- `docs/CHANGELOG.md`
- `docs/milestones/M7_LOCAL_PROFILE_PERSISTENCE_FOUNDATION.md`

---

## DECISION-009 — M8 Opened

Status: `SUPERSEDED BY DECISION-010`

Summary:

M8 TV Navigation & Access Polish milestone opened by Director.

Related docs:

- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/PROJECT_MEMORY.md`
- `docs/CHANGELOG.md`
- `docs/milestones/M8_TV_NAVIGATION_ACCESS_POLISH.md`

---

## DECISION-010 — M8 Lock Decision

Status: `APPROVED / LOCKED`

Summary:

M8 TV Navigation & Access Polish milestone is LOCKED.

Lock evidence:

- PR #10 merged to main
- Merge commit: `303008e3f38cf9ba94ab7f4e16dd4bbcc3190e81`
- Build Evidence: Android Build Verification #109 success
- Runtime Evidence: accepted
- QA Result: PASS
- Documentation Memory: PASSED
- Protected systems clear
- Legal/compliance clear
- Splash cleanup excluded from delivered scope

Related docs:

- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/PROJECT_MEMORY.md`
- `docs/CHANGELOG.md`
- `docs/milestones/M8_TV_NAVIGATION_ACCESS_POLISH.md`

---

## DECISION-011 — M9 Opened

Status: `SUPERSEDED BY DECISION-012`

Summary:

M9 Startup Flow & Session Entry Polish milestone opened by Director.

Related docs:

- `docs/START_HERE.md`
- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/PROJECT_MEMORY.md`
- `docs/CHANGELOG.md`
- `docs/milestones/M9_STARTUP_FLOW_SESSION_ENTRY_POLISH.md`

---

## DECISION-012 — M9 Lock Decision

Status: `APPROVED / LOCKED`

Summary:

M9 Startup Flow & Session Entry Polish is locked by Director.

Lock evidence:

- PR #11 merged to main
- Merge commit: `3ebb0e2d7426c5695af86547c7f195a734c28c6a`
- Changed file:
  - `app/src/main/java/com/nexora/tv/ui/screens/SplashScreen.kt`
- Build Evidence: Android Build Verification #144 success
- Runtime Evidence: accepted
- QA Result: PASS
- Documentation Memory: PASSED
- Director LOCKED: YES
- Protected systems clear
- Legal/compliance clear

Impact:

- M9 is complete and locked.
- Last locked milestone is M9.

Related docs:

- `docs/START_HERE.md`
- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`
- `docs/PROJECT_MEMORY.md`
- `docs/CHANGELOG.md`
- `docs/milestones/M9_STARTUP_FLOW_SESSION_ENTRY_POLISH.md`

---

## DECISION-013 — M10 Ecosystem Direction

Status: `APPROVED / ACTIVE`

Summary:

Nexora is aligned as a legal Core Media Player Ecosystem.

- `TV_Project` is the Android TV / Fire TV first client.
- `TV_Project_Platform` is the Core Account / Device / License / Admin / Remote Config / App Version / Profile Transfer center.
- M10 is an ecosystem alignment and client integration contract milestone.

Impact:

- Current active milestone: M10 Ecosystem Alignment & Client Integration Contract
- Current active task: M10-TASK-001 Core Ecosystem Contract & Cross-Repo Roadmap Alignment
- Required next action: Ecosystem Integration / Systems Architect
- Database/API/Android bridge work must wait for contract alignment
- M10 is not PASSED
- M10 is not LOCKED

Related docs:

- `docs/milestones/M10_ECOSYSTEM_ALIGNMENT_CLIENT_INTEGRATION_CONTRACT.md`
- `docs/ECOSYSTEM_CONTRACT.md`
- `docs/CLIENT_INTEGRATION_CONTRACT.md`
- `docs/DEVICE_IDENTITY_POLICY.md`
- `docs/LEGAL_PUBLIC_LANGUAGE.md`
- `docs/PLATFORM_ANDROID_ROADMAP.md`
- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`

---

## DECISION-014 — Department Boot Protocol

Status: `APPROVED`

Summary:

Department Boot Protocol and Department Role Cards are approved so every department understands its role from the first chat window.

Impact:

- Every department must start from repo runtime truth
- Departments report only
- Director decides
- Ecosystem Integration owns cross-repo alignment
- Agent files define role, owns, does-not-do, stop conditions, output format, and legal boundary reminders
- Runtime docs override chat memory and stale role files
- Documentation Memory must not return DONE after partial file updates

Related docs:

- `docs/DEPARTMENT_BOOT_PROTOCOL.md`
- `docs/DEPARTMENT_ROLE_CARDS.md`
- `docs/agents/DIRECTOR.md`
- `docs/agents/ECOSYSTEM_INTEGRATION.md`
- `docs/agents/SYSTEMS_ARCHITECT.md`
- `docs/agents/PRODUCT_DIRECTOR.md`
- `docs/agents/PLATFORM_WEB_LEAD.md`
- `docs/agents/BACKEND_ENGINEER.md`
- `docs/agents/DATABASE_ARCHITECT.md`
- `docs/agents/ANDROID_APP_BUILDER.md`
- `docs/agents/PLAYBACK_ENGINEER.md`
- `docs/agents/TV_UX_REMOTE_NAVIGATION.md`
- `docs/agents/SECURITY_PRIVACY.md`
- `docs/agents/LEGAL_COMPLIANCE.md`
- `docs/agents/QA_TESTER.md`
- `docs/agents/DOCUMENTATION_MEMORY.md`
- `docs/agents/RELEASE_MANAGER.md`

---

## DECISION-015 — M10 Client Integration Contract Hardening

Status: `APPROVED / DOCUMENTATION-ONLY`

Summary:

`docs/CLIENT_INTEGRATION_CONTRACT.md` is hardened for M10 before any M11 database/API/backend bridge implementation.

Hardening includes:

- Android ↔ Platform responsibility table
- Endpoint contract draft
- Request/response examples
- Error state matrix
- Session/token behavior placeholders
- Remote config schema boundary
- Profile transfer MVP flow
- Free early launch behavior
- Legal media player boundary

Impact:

- Contract is stronger for Security & Privacy and Legal Compliance review.
- Backend Engineer and Database Architect may use the document for feasibility reporting only.
- This does not approve implementation.
- This does not approve app code changes.
- This does not approve backend code changes.
- This does not approve database implementation.
- This does not approve payment enforcement.
- M10 remains ACTIVE.
- M10 is not PASSED.
- M10 is not LOCKED.

Related docs:

- `docs/CLIENT_INTEGRATION_CONTRACT.md`
- `docs/PROJECT_MEMORY.md`
- `docs/CHANGELOG.md`
- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`

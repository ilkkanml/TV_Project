# Nexora TV — START HERE

## Source of Truth

This repository is the source of truth.

Chat history is not the source of truth.

## Read First

1. `docs/START_HERE.md`
2. `docs/PROJECT_MEMORY.md`
3. `docs/MILESTONE_STATUS.md`
4. `docs/NEXT_TASK.md`
5. `docs/HANDOFF.md`
6. `docs/DEPARTMENT_BOOT_PROTOCOL.md`
7. `docs/DEPARTMENT_ROLE_CARDS.md`
8. `docs/STUDIO_BIBLE.md`
9. `docs/PROTECTED_SYSTEMS.md`
10. `docs/SAFE_CODE_ENGINE.md`
11. `docs/APP_BACKEND_INTEGRATION.md`
12. `docs/DECISION_LOG.md`
13. Relevant agent file from `docs/agents/`

## Current Truth

- Project: Nexora TV
- Repository: `https://github.com/ilkkanml/TV_Project.git`
- Product type: legal Core Media Player Ecosystem / Android TV + Fire TV first client
- Platform center: `TV_Project_Platform`
- Android client: `TV_Project`
- Current active milestone: M10 Ecosystem Alignment & Client Integration Contract
- Current active task: M10-TASK-001 Core Ecosystem Contract & Cross-Repo Roadmap Alignment
- M10 status: ACTIVE
- M10-TASK-001 status: READY FOR ECOSYSTEM INTEGRATION / SYSTEMS ARCHITECT
- Last locked milestone: `M9 Startup Flow & Session Entry Polish`
- M9 status: LOCKED
- M9-TASK-001 status: PASSED / COMPLETED
- Required next action: Ecosystem Integration / Systems Architect
- M10 is not PASSED
- M10 is not LOCKED

## Lean Workflow Rule

Use the shortest safe workflow.

- Department report only when useful
- Director decides
- Builder only when implementation is approved
- QA only for code/runtime/release risk
- Documentation only for runtime truth, milestone status, major decision, source-of-truth contract, or milestone finalization
- Minor reviews do not require CHANGELOG/DECISION_LOG updates
- Director may handle simple documentation updates in the main Director thread

## Department Boot Rule

Every department must start by reading:

- `docs/DEPARTMENT_BOOT_PROTOCOL.md`
- `docs/DEPARTMENT_ROLE_CARDS.md`
- Its own file in `docs/agents/`

Departments report only. Director decides.

If runtime docs conflict, stop and report `DOCUMENTATION_CONFLICT`.

## M9 Lock Evidence

- PR #11 merged to main
- Merge commit: `3ebb0e2d7426c5695af86547c7f195a734c28c6a`
- Changed file:
  - `app/src/main/java/com/nexora/tv/ui/screens/SplashScreen.kt`
- Build Evidence: Android Build Verification #144 success
- Runtime Evidence: accepted
- QA Result: PASS
- Documentation Memory: PASSED
- Director LOCKED: YES
- Blockers: none
- Regression risk: none
- Protected systems clear
- Legal/compliance clear

## M9 Scope Summary

M9 cleaned startup/navigation entry behavior after M8, especially the previously excluded Splash cleanup, while keeping auth/session behavior mock-safe and additive.

M9 did not approve real auth/session implementation, backend integration, provider connection, playlist fetching/parsing, playback changes, payment/subscription work, protected system rewrite, or UI redesign.

## Required Next Action

Ecosystem Integration / Systems Architect
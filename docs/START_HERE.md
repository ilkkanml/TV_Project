# Nexora TV — START HERE

## Source of Truth

This repository is the source of truth.

Chat history is not the source of truth.

Read first:

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
- Current active milestone: None
- Current active task: None
- Last locked milestone: `M9 Startup Flow & Session Entry Polish`
- M9 status: LOCKED
- M9-TASK-001 status: PASSED / COMPLETED
- PR #11 merged to main
- Merge commit: `3ebb0e2d7426c5695af86547c7f195a734c28c6a`
- Changed files:
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
- Previous locked milestone: `M8 TV Navigation & Access Polish`
- M8 status: LOCKED

## M9 Scope Summary

M9 cleaned startup/navigation entry behavior after M8, especially the previously excluded Splash cleanup, while keeping auth/session behavior mock-safe and additive.

M9 did not approve real auth/session implementation, backend integration, provider connection, playlist fetching/parsing, playback changes, payment/subscription work, protected system rewrite, or UI redesign.

## Required Next Action

No active task.

Do not open M10 unless Director receives explicit user instruction.

# Nexora TV — HANDOFF

## Current Handoff

`M9 Startup Flow & Session Entry Polish`

Status: ACTIVE

## Current Task

`M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish`

Status: READY FOR DEVELOPER

## Required Next Action

Send `M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish` to Developer.

## Developer Scope

- Patch Splash → Login navigation so Splash is removed from back stack
- Confirm Login → Home behavior remains safe
- Confirm Home back behavior does not expose Splash
- Keep changes minimal/additive
- Do not touch playback/provider/backend/storage systems
- Build must pass
- Runtime evidence required

## Last Locked Milestone

`M8 TV Navigation & Access Polish`

Status: LOCKED

Director LOCKED: YES

Lock evidence:

- PR #10 merged to main
- Merge commit: `303008e3f38cf9ba94ab7f4e16dd4bbcc3190e81`
- Build Evidence: Android Build Verification #109 success
- Runtime Evidence: accepted
- QA Result: PASS
- Documentation Memory: PASSED
- Blockers: none
- Regression risk: none
- Protected systems clear
- Legal/compliance clear
- Splash cleanup: excluded from delivered scope

## M9 Status Boundaries

- M9 is ACTIVE
- M9-TASK-001 is READY FOR DEVELOPER
- M9 is not QA PASSED
- M9 is not Documentation Memory PASSED
- M9 is not LOCKED

## Guardrails

- Protected systems rewrite forbidden
- Legal/compliance boundary preserved
- No auth system rewrite
- No session persistence expansion
- No secure storage implementation
- No backend integration
- No provider connection
- No playlist parsing/fetching
- No playback core rewrite
- No UI redesign
- No payment/subscription
- No illegal source support

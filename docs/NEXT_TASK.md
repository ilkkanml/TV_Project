# Nexora TV — NEXT_TASK

## Current Status

Active task ready for Developer

## Current Active Milestone

`M9 Startup Flow & Session Entry Polish`

Status: ACTIVE

## Current Active Task

`M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish`

Status: READY FOR DEVELOPER

## Last Locked Milestone

`M8 TV Navigation & Access Polish`

Status: LOCKED

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

## Guardrails

- M8 remains LOCKED
- M9 is not QA PASSED
- M9 is not Documentation Memory PASSED
- M9 is not LOCKED
- Protected systems rewrite remains forbidden
- Legal/compliance boundary must remain preserved
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

## Evidence Required

- Safe Code Engine recorded
- Build evidence recorded
- Runtime evidence recorded

# Nexora TV — M9 Startup Flow & Session Entry Polish

## Status

ACTIVE

## Current Project State

- Current active milestone: `M9 Startup Flow & Session Entry Polish`
- Current active task: `M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish`
- Current task status: QA PASSED
- Last locked milestone: `M8 TV Navigation & Access Polish`

## Purpose

Clean up app startup/navigation entry behavior after M8, especially the previously excluded Splash cleanup, while keeping auth/session behavior mock-safe and additive.

M9 does not approve real auth/session implementation, backend integration, provider connection, playlist fetching, playback changes, payment/subscription work, or UI redesign.

## Task

`M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish`

Status: QA PASSED

## QA PASS Evidence

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

## Scope IN

- Splash → Login backstack cleanup
- Startup entry flow review
- Basic back behavior safety from Splash/Login/Home
- Keep Login route behavior stable
- Prevent Splash from remaining in navigation stack after entry transition
- Minimal additive navigation polish
- Existing visual style preserved
- No real auth/session implementation
- No backend requirement
- Safe Code Engine required
- Build evidence required
- Runtime evidence required

## Scope OUT

- No auth system rewrite
- No session persistence expansion
- No secure storage implementation
- No backend integration
- No provider connection
- No playlist parsing/fetching
- No playback core rewrite
- No UI redesign
- No payment/subscription
- No protected system rewrite
- No illegal source support

## Safe Code Engine Requirements

Recorded:

- Files changed
- Scope boundary confirmation
- Protected systems confirmation
- Legal/compliance confirmation
- Build evidence accepted
- Runtime evidence accepted
- QA PASS

## Protected Systems Boundary

Protected systems remain clear.

No Playback Core rewrite.
No Auth System rewrite.
No Hidden Backend API integration.
No TV Navigation System rewrite.
No Compose TV Design System rewrite.
No Premium Cinematic UX rewrite.

## Legal / Compliance Boundary

M9 remains app startup/navigation polish only.

No content source, playlist provider, illegal stream support, unauthorized scraping, DRM bypass, credential sharing, or paywall bypass is allowed.

## Current State

M9 is ACTIVE.

M9-TASK-001 is QA PASSED.

PR #11 is not recorded as merged.

M9 is not Documentation Memory PASSED.

M9 is not locked.

Director LOCKED is not recorded for M9.

## Required Next Action

Return to Director for PR #11 merge / M9 lock decision.

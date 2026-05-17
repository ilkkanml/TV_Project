# Nexora TV — M9 Startup Flow & Session Entry Polish

## Status

ACTIVE

## Current Project State

- Current active milestone: `M9 Startup Flow & Session Entry Polish`
- Current active task: `M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish`
- Last locked milestone: `M8 TV Navigation & Access Polish`

## Purpose

Clean up app startup/navigation entry behavior after M8, especially the previously excluded Splash cleanup, while keeping auth/session behavior mock-safe and additive.

M9 does not approve real auth/session implementation, backend integration, provider connection, playlist fetching, playback changes, payment/subscription work, or UI redesign.

## First Task

`M9-TASK-001 Splash Backstack Cleanup & Startup Entry Safety Polish`

Status: READY FOR DEVELOPER

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

## Expected Developer Scope

- Patch Splash → Login navigation so Splash is removed from back stack.
- Confirm Login → Home behavior remains safe.
- Confirm Home back behavior does not expose Splash.
- Keep changes minimal/additive.
- Do not touch playback/provider/backend/storage systems.
- Build must pass.
- Runtime evidence required.

## Safe Code Engine Requirements

Developer must record:

- Files changed
- Scope boundary confirmation
- Protected systems confirmation
- Legal/compliance confirmation
- Build evidence
- Runtime evidence

QA cannot PASS without required evidence.

Documentation Memory cannot mark M9 passed or locked without QA PASS and Director instruction.

## Protected Systems Boundary

Protected systems remain preserved.

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

M9-TASK-001 is READY FOR DEVELOPER.

M9 is not passed.

M9 is not locked.

# Nexora TV — M8 TV Navigation & Access Polish

## Status

ACTIVE

## Purpose

Polish existing TV navigation, screen access, back behavior, and remote-friendly usability across the current app shell.

M8 does not approve new product feature expansion.

## First Task

`M8-TASK-001 Profile Access, Backstack & Login Field Safety Polish`

Status: READY FOR DEVELOPER

## Scope IN

- Clear access path to PlaylistProfile screen
- Settings/Profile/Account placeholder route access
- Splash to Login backstack cleanup
- Login password field visual masking
- Home / Detail / Player / PlaylistProfile back behavior review
- Minimal TV remote focus/access polish
- Existing navigation pattern reuse
- Existing visual style preserved
- Additive-only changes
- Safe Code Engine required

## Scope OUT

- No playback core rewrite
- No auth system rewrite
- No backend integration
- No secure storage implementation
- No profile persistence expansion
- No real playlist fetch/parsing
- No provider connection
- No payment/subscription implementation
- No UI redesign
- No protected system rewrite
- No prohibited source support

## Protected Systems Boundary

M8 may touch only minimal additive access/navigation wiring.

No TV Navigation System rewrite.
No Compose TV Design System rewrite.
No Premium Cinematic UX rewrite.
No Playback Core change.

## Required Evidence

Developer must return:

- Changed files
- Scope confirmation
- Build command
- Build result
- Runtime evidence because screens/navigation are affected
- Risk statement
- Next recommended agent

## Exit Condition

M8 cannot become LOCKED until implementation, build evidence, runtime evidence, QA Tester, Documentation Memory, and Director decision are complete.

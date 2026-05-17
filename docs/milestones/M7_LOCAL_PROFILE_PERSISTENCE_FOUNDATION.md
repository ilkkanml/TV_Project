# Nexora TV — M7 Local Profile Persistence Foundation

## Status

ACTIVE

## Purpose

M7 opens the foundation for saved local playlist/profile handling after the M6 profile input shell.

The goal is to move from a one-screen input shell toward a safe local profile management foundation.

Nexora TV remains a legal player/client platform.

The app does not provide channels, playlists, streams, accounts, hidden sources, or bundled content.

## Scope IN

- Inspect current M6 profile model/screen/navigation before changes
- Add safe local profile repository direction
- Add saved profiles list shell
- Add selected/active profile state shell
- Add edit/delete profile shell behavior where minimal
- Keep profile data local/mock-safe
- Preserve legal ownership notice
- Preserve M6 input shell behavior
- Keep changes additive and minimal
- Follow Safe Code Engine

## Scope OUT

- No bundled playlist/source
- No real provider connection
- No playlist fetch/parsing
- No production backend integration
- No cloud sync
- No payment changes
- No player core changes
- No auth system changes
- No hidden API work
- No protected system rewrite
- No production release behavior

## Sensitive Data Rule

Do not introduce unsafe plain-text persistence for sensitive profile fields.

If secure local storage support already exists, Developer may report whether it can be safely reused.

If secure storage is not already available, Developer must keep sensitive values mock/in-memory or return a Director decision request before persistence.

## Protected Systems Rule

Protected systems remain stable.

Allowed only if strictly required:

- Minimal additive route/screen entry
- Existing component/style reuse
- Existing navigation pattern reuse

Not allowed:

- Playback Core structural changes
- Auth System structural changes
- Hidden Backend API changes
- TV Navigation System rewrite
- Compose TV Design System rewrite
- Premium Cinematic UX rewrite

## Legal Rule

All profile input must be user-provided and legally authorized.

Any request to provide prohibited sources or bypass protected content is HOLD.

## First Task

`M7-TASK-001 Local Profile Repository & Saved Profiles Shell`

Goal:

Create a safe foundation for saved local profile shell management without connecting to external services.

Expected result:

- Existing M6 profile shell remains working
- Saved profile list shell exists
- Active profile shell/state direction exists
- Add/edit/delete shell behavior is minimal and local/mock-safe
- No real playlist fetch/parsing is implemented
- No unsafe sensitive-data persistence is introduced

## Safe Code Engine Requirement

Developer must return:

- Changed files
- Scope confirmation
- Build command
- Build result
- Runtime evidence if UI/route behavior changes
- Risk statement
- Next recommended agent

## Exit Condition

M7 can only move toward QA after:

- Developer returns Safe Code Engine evidence
- Build evidence is confirmed
- User/runtime evidence is confirmed if UI/route changed
- QA Tester verifies blocker/regression status
- Documentation Memory records status
- Director approves lock

# Nexora TV — M6 Playlist Profile & Legal Source Input Foundation

## Status

ACTIVE

## Purpose

M6 opens the foundation for user-managed playlist profiles and legal source input.

This milestone must keep Nexora TV as a legal player/client platform.

The app does not provide channels, playlists, streams, credentials, or bundled content.

## Scope IN

- Playlist profile domain model foundation
- Local playlist/profile management direction
- Legal source input screen foundation
- M3U URL input shell
- Xtream Codes input shell
- Profile name / type / status basics
- Safe validation states
- Empty / loading / invalid / saved states
- User-facing legal ownership notice
- Local mock/demo-only flow where needed
- Minimal navigation entry if required

## Scope OUT

- Bundled IPTV playlists
- Free paid channels
- Unauthorized stream scraping
- DRM bypass
- Token/cookie harvesting
- Credential bypass
- Illegal restreaming
- Backend playlist ownership
- Production payment changes
- Provider/API integration
- Full cloud sync
- Portal integration
- Admin panel
- Playback core rewrite
- Auth system rewrite
- TV navigation engine rewrite
- Compose design system rewrite

## Protected Systems Rule

Protected systems remain stable.

Allowed only if strictly required:

- Minimal additive route/screen entry for profile setup
- Minimal UI integration using existing design patterns

Not allowed:

- Playback Core structural changes
- Auth System structural changes
- Hidden Backend API changes
- TV Navigation System rewrite
- Compose TV Design System rewrite
- Premium Cinematic UX rewrite

## Legal Rule

All playlist/profile input must be user-provided and legally authorized.

Any request to provide illegal IPTV sources or bypass protected content is HOLD.

## First Task

M6-TASK-001 Playlist Profile Model & Legal Input Shell

Goal:

Create a safe foundation for playlist profile creation without connecting to illegal or bundled content.

Expected result:

- User can reach a profile/source input shell
- User can see legal ownership notice
- User can choose M3U URL or Xtream Codes input direction
- Data flow remains local/mock-safe unless explicitly approved later
- No real provider integration is implemented in this task

## Exit Condition

M6 can only move toward QA after:

- Developer returns changed files and test notes
- User performs local/runtime test
- QA Tester verifies blocker/regression status
- Documentation Memory records status
- Director approves lock

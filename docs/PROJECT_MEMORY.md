# Nexora TV — Project Memory

## Purpose

This document is the compact memory source for new ChatGPT windows.

Chat history is disposable. Repository docs are the source of truth.

## Project Name

Nexora TV

## Repository

https://github.com/ilkkanml/TV_Project.git

## Product Type

Premium subscription-based Android TV / Fire TV IPTV player application.

## Core Rule

Nexora TV is a legal player platform.

It must not bundle pirated channels, illegal streams, unauthorized playlists, DRM bypass logic, token/cookie theft, credential bypass, or unauthorized scraping.

Allowed development sources:

- Mock data
- Test streams with permission
- Public demo streams
- User-owned licensed streams
- Legal provider/API integrations

## Primary Platforms

- Android TV
- Fire TV

Future platforms:

- Samsung Tizen
- LG webOS
- Apple tvOS

## Brand Direction

- Futuristic
- Cinematic
- Premium
- Dark metallic
- Deep neon cyan accent
- Electric blue secondary accent

## UX Direction

- Netflix-like familiarity
- IPTV practicality
- Large cinematic posters
- Remote-first navigation
- Dynamic blurred artwork background
- Scale plus glow focus behavior
- Premium cinematic transitions
- Calm, clean, high-end TV experience

## Locked Product Decisions

- Brand: Nexora TV
- Package direction: `com.nexora.tv`
- Platform priority: Android TV / Fire TV
- Homepage hero: auto-sliding featured content
- Hero timing: 8 seconds
- First home row: Continue Watching
- Poster style: large cinematic posters
- Live TV layout: Netflix-like poster rows
- Live TV playback: instant fullscreen
- Movies and Series playback: detail page before playback
- Live TV transition: ultra fast hard cut
- VOD transition: smooth fade
- Player overlay timeout: 5 seconds
- Splash style: cinematic animated
- Homepage background: dynamic blurred artwork
- Focus effect: scale plus glow
- Detail preview: muted autoplay preview

## Current Milestone Truth

### M1 Foundation

Status: LOCKED

M1 locked the product identity, visual direction, UX philosophy, subscription direction, and initial architecture baseline.

### M2 Playback Expansion

Status: LOCKED

M2 locked the playback baseline:

- Media3 / ExoPlayer direction
- Fullscreen playback shell
- Fast live TV switching direction
- Player overlay timeout rule
- Stream recovery direction
- Premium playback continuity rules

### M3 Premium UI Expansion

Status: LOCKED

M3 locked the safe runtime HomeScreen premium UI baseline:

- Branch: `feature/m3-premium-ui-polish`
- HomeScreen safe runtime build
- Premium TV layout polish
- Category menu flow
- Poster row interaction
- Dynamic background direction
- Runtime checklist validation
- User Test: PASSED
- QA Tester: PASSED
- Final Android TV Runtime Test: PASSED
- Sync/Re-compare: DONE
- Main merge: DONE
- Protected systems untouched
- Legal/compliance risk: none detected

## Current Code Reality

Current repository contains:

- Android app module
- Kotlin / Jetpack Compose setup
- Android TV launcher manifest
- Splash → Login → Home → Player navigation
- Safe runtime HomeScreen build
- Basic Media3 ExoPlayer test player shell
- Nexora color/theme foundation
- M3-TASK-001 HomeScreen Premium UI polish passed user runtime test and QA review

## Minimal Agent Workflow

Use only:

1. DIRECTOR
2. DEVELOPER
3. QA_TESTER
4. DOCUMENTATION_MEMORY

No large department structure unless explicitly requested

## User Preference

- Minimal explanations
- Direct instructions
- Continuous progress
- Ask only critical decision questions
- Do not restart planning
- Do not recreate milestones
- Keep docs updated so new chats can continue

## Important Instruction For Future ChatGPT Sessions

Do not restart from zero.

Continue from M3 Premium UI Expansion unless the user explicitly changes milestone.

Before work, read:

- `docs/START_HERE.md`
- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/PROTECTED_SYSTEMS.md`
- `docs/HANDOFF.md`
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

## Subscription Direction

Initial access model:

- MAC/device identity plus password activation

Initial user flow:

1. App generates or reads device identity.
2. User enters activation password.
3. Backend validates device access.
4. Subscription expiration is checked.
5. Active device enters app.

Future subscription infrastructure:

- Device registry
- Subscription expiration
- Active/inactive device state
- Session token
- Admin panel
- Stripe later

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

Status: ACTIVE / USER TEST PASSED / QA PASSED / DIRECTOR LOCK PENDING

M3 is not LOCKED.

M3-TASK-001 Safe Runtime Home Build record:

- Branch: `feature/m3-premium-ui-polish`
- Branch note: ahead 9 / behind 1 versus main; final merge öncesi sync/re-compare önerilir
- Changed file: `app/src/main/java/com/nexora/tv/ui/screens/HomeScreen.kt`
- Patch scope: HomeScreen Premium UI polish + safe runtime stabilization
- M3 Safe Runtime Home Build User Test: PASSED
- QA Tester Result: PASSED
- Blockers: none
- Regression risk: Low
- Regression: none detected in repo review
- Legal risk: none detected

Runtime notes:

- HomeScreen safe runtime build Button tabanlı çalışıyor
- Custom focusable/onFocusChanged geçici olarak kullanılmıyor
- Login → Home crashsiz
- Menü seçimleri çalışıyor: Home, Live TV, Movies, Series, Settings
- Kategoriye göre poster içeriği değişiyor
- Dynamic background `selectedPoster.accent` üzerinden çalışıyor
- Horizontal poster row scroll çalışıyor
- Player route bilinçli olarak devre dışı

Protected systems untouched:

- Playback Core
- Auth / device login
- Backend API
- Protected architecture

No illegal stream / DRM bypass / unauthorized IPTV logic was added.

Primary focus:

- Premium home UI refinement
- TV-first visual hierarchy
- Focus scale/glow polish
- Cinematic poster rows
- Dynamic blurred background direction
- Navigation readability
- App shell polish without touching Playback Core

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

No large department structure unless explicitly requested by user.

## User Preference

The user prefers:

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

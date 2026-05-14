# Nexora TV — Project Memory

## Purpose

This document exists so a new ChatGPT conversation can continue the project without losing context.

## Project Name

Nexora TV

## Repository

https://github.com/ilkkanml/TV_Project.git

## Product Type

Premium subscription-based Android TV / Fire TV IPTV player application.

## Core Rule

Nexora TV is a legal player platform. It must not bundle pirated channels, illegal streams, or unauthorized content packages.

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
- Large cinematic posters
- Remote-first navigation
- Dynamic blurred artwork background
- Scale plus glow focus behavior
- Premium cinematic transitions
- Calm, clean, high-end TV experience

## Locked Product Decisions

- Brand: Nexora TV
- Package direction: com.nexora.tv
- Platform priority: Android TV / Fire TV
- Homepage hero: auto-sliding featured content
- Hero timing: 8 seconds
- First home row: Continue Watching
- Poster style: Large cinematic posters
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

## Milestone Status

### M1 Foundation

Status: Freeze locked and archived in GitHub.

M1 locked the product identity, visual direction, UX philosophy, subscription direction, and initial architecture baseline.

### M2 Playback Expansion

Status: Current active milestone.

Primary focus:

- Playback core stabilization
- Fullscreen player refinement
- Fast live TV channel switching
- Stream recovery
- Player overlay behavior
- Premium playback continuity

## Current Development Style

The user prefers:

- Minimal explanations
- Continuous progress
- Ask only critical decision questions
- Treat project like a real software studio
- Backup to GitHub at milestones
- Preserve handoff knowledge for new chats

## Important Instruction for Future ChatGPT Session

Do not restart planning from zero.

Continue from M2 Playback Expansion unless the user explicitly asks to revisit earlier decisions.

Prioritize repository updates and concrete implementation artifacts.

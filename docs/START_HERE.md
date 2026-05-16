# Nexora TV — START HERE

## Source of Truth

This repository is the source of truth.

Chat history is not the source of truth.

Before any new ChatGPT window works on the project, it must read these files first:

1. `docs/START_HERE.md`
2. `docs/PROJECT_MEMORY.md`
3. `docs/STUDIO_BIBLE.md`
4. `docs/MILESTONE_STATUS.md`
5. `docs/NEXT_TASK.md`
6. `docs/PROTECTED_SYSTEMS.md`
7. `docs/HANDOFF.md`
8. Relevant agent file from `docs/agents/`

## Current Truth

- Project: Nexora TV
- Repository: `https://github.com/ilkkanml/TV_Project.git`
- Current active milestone: `None`
- Last locked milestone: `M4 Auth & Device Activation Foundation`
- Current workflow: minimal Director-led handoff
- Next status: `Awaiting Director decision for next milestone/task`

## Locked Milestones

- M1 Foundation: `LOCKED`
- M2 Playback Expansion: `LOCKED`
- M3 Premium UI Expansion: `LOCKED`
- M4 Auth & Device Activation Foundation: `LOCKED`

## Hard Rules

- Do not restart the project.
- Do not recreate completed milestones.
- Do not create a new milestone unless the user explicitly says: `Yeni milestone aç`.
- Do not create extra departments.
- Do not add unnecessary procedures.
- Do not write long explanations.
- Do not claim live code facts without reading repo files.
- Do not modify protected systems unless `NEXT_TASK.md` explicitly allows it.
- Do not implement pirate IPTV, illegal streams, DRM bypass, token/cookie theft, or unauthorized scraping.

## Minimal Agent System

Use only:

1. `DIRECTOR`
2. `DEVELOPER`
3. `QA_TESTER`
4. `DOCUMENTATION_MEMORY`

## Workflow

Director → Developer → QA Tester → Documentation Memory → Director

The user manually carries messages between windows.

Each agent must obey its own `.md` file and the active `NEXT_TASK.md`.

## Response Rule

Keep every response short.

Only ask the user if a real decision is required.

# Nexora TV — Workflow Guide

## Purpose

This file explains how to continue Nexora TV across new ChatGPT windows without restarting the project or recreating milestones.

## Source of Truth

The GitHub repo docs are the source of truth.

Chat history is not the source of truth.

## Current Status

- Current milestone: `M3 Premium UI Expansion`
- Last locked milestone: `M2 Playback Expansion`
- Active task source: `docs/NEXT_TASK.md`
- Handoff source: `docs/HANDOFF.md`

## Minimal Agent Flow

```text
Director → Developer → QA Tester → Documentation Memory → Director
```

## Daily Use

### 1. Open Director Window

Paste or upload:

- `docs/prompt_templates/DIRECTOR_WINDOW_PROMPT.md`
- `docs/agents/DIRECTOR.md`
- `docs/START_HERE.md`
- `docs/NEXT_TASK.md`
- `docs/HANDOFF.md`

Director gives one next agent instruction.

### 2. Open Developer Window

Paste or upload:

- `docs/prompt_templates/AGENT_WINDOW_PROMPT.md`
- `docs/agents/DEVELOPER.md`
- `docs/NEXT_TASK.md`
- `docs/PROTECTED_SYSTEMS.md`
- `docs/HANDOFF.md`

Developer performs only the active task.

### 3. Return Result To Director

Paste Developer result back to Director.

Director decides next step:

- QA Tester
- Documentation Memory
- Another Developer task
- Critical user decision

### 4. QA Tester Window

Paste or upload:

- `docs/prompt_templates/AGENT_WINDOW_PROMPT.md`
- `docs/agents/QA_TESTER.md`
- `docs/NEXT_TASK.md`
- `docs/PROTECTED_SYSTEMS.md`
- Developer result

QA returns:

```text
PASS / WARNING / BLOCKER
```

### 5. Documentation Memory Window

Paste or upload:

- `docs/prompt_templates/AGENT_WINDOW_PROMPT.md`
- `docs/agents/DOCUMENTATION_MEMORY.md`
- `docs/HANDOFF.md`
- `docs/CHANGELOG.md`
- `docs/MILESTONE_STATUS.md`
- QA result

Documentation updates the project memory files.

## Rules

- Do not create new milestone unless user says: `Yeni milestone aç`.
- Do not restart planning.
- Do not add extra departments.
- Do not touch protected systems unless `NEXT_TASK.md` explicitly allows it.
- Do not implement illegal IPTV, DRM bypass, unauthorized scraping, or credential bypass.
- Keep all outputs short.

## Window Restart Rule

If a chat window becomes slow, open a new window and paste the relevant prompt template plus the relevant agent file.

The new window continues from repo docs, not from memory.

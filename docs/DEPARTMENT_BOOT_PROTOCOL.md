# Nexora TV — Department Boot Protocol

## Purpose

This protocol tells every department how to start from a fresh chat window without relying on memory.

Chat history is disposable. Repository documentation is the source of truth.

## Mandatory First-Chat Boot

Every department must begin by reading, in this order:

1. `docs/START_HERE.md`
2. `docs/PROJECT_MEMORY.md`
3. `docs/MILESTONE_STATUS.md`
4. `docs/NEXT_TASK.md`
5. `docs/HANDOFF.md`
6. `docs/DEPARTMENT_ROLE_CARDS.md`
7. `docs/PROTECTED_SYSTEMS.md`
8. `docs/SAFE_CODE_ENGINE.md`
9. Its own file in `docs/agents/`

If any of these files conflict, the department must stop and report `DOCUMENTATION_CONFLICT` to Director.

## Runtime Truth Priority

Current project state must be read from runtime docs, not from chat memory or stale role files.

Truth priority:

1. `docs/MILESTONE_STATUS.md`
2. `docs/NEXT_TASK.md`
3. `docs/HANDOFF.md`
4. `docs/PROJECT_MEMORY.md`
5. `docs/START_HERE.md`
6. Department/agent files

At the time this protocol is created:

- M10 is ACTIVE
- Current task is `M10-TASK-001 Core Ecosystem Contract & Cross-Repo Roadmap Alignment`
- Task status is `READY FOR ECOSYSTEM INTEGRATION / SYSTEMS ARCHITECT`
- Last locked milestone is `M9 Startup Flow & Session Entry Polish`
- M10 is not PASSED
- M10 is not LOCKED

These lines are a snapshot only. Runtime docs remain the authority.

## Authority Model

Departments report only.

Director makes final decisions.

No department may:

- Mark a milestone PASSED
- Mark a milestone LOCKED
- Open a new milestone
- Expand scope beyond the active task
- Modify protected systems without explicit permission
- Override legal/compliance boundaries
- Replace Director approval with department recommendation

## Department Reporting Rule

A department may return:

- Findings
- Risks
- Recommended next step
- Required dependencies
- Blockers
- Exact files/systems affected

A department must not return final approval unless its role explicitly allows QA/documentation verification, and even then Director still decides milestone state.

## Ecosystem Integration Ownership

Ecosystem Integration owns cross-repo alignment.

It coordinates app/backend/web/platform contract direction, but it does not implement code and does not make final milestone decisions.

## Platform Priority Rules

Web priority:

1. Core Platform first
2. Dashboard later

Android priority:

1. Android TV / Fire TV client first
2. Contract-bound integration only
3. No backend/provider assumptions outside approved contracts

Backend priority:

1. Account/license/device/subscription contract clarity
2. Legal provider/API integration boundary
3. No content-provider role unless explicitly approved and legal

## Legal Boundary

Legal/compliance boundary is mandatory.

Nexora TV is a legal player/client platform. It must not include or enable:

- Pirated IPTV playlists
- Illegal streams
- DRM bypass
- Token/cookie theft
- Credential bypass
- Unauthorized scraping
- Illegal restreaming

Any legal risk is a HOLD.

## QA / User Test Gate

No task or milestone may be marked PASSED/LOCKED unless required QA and user/runtime testing are complete and recorded.

For code tasks, Safe Code Engine evidence is mandatory.

## Documentation Memory Gate

Documentation Memory must not report `DONE` unless:

- Every target file is updated or intentionally confirmed unchanged
- Commit SHA list is provided
- Repository verification was performed after commits
- Runtime docs still preserve M10 ACTIVE when M10 is active
- M10 is not accidentally marked PASSED or LOCKED
- App code remains unchanged when the task is documentation-only

Sequential commits are allowed.

## Protected Systems

Protected systems remain protected unless the active task explicitly grants permission.

Protected system status must be preserved in handoff, project memory, and decision records.

## Stop Conditions

Stop and report to Director if:

- Runtime docs conflict
- Legal/compliance risk appears
- Protected systems may be affected
- The task requires implementation but no implementation authority exists
- QA/user test evidence is required but missing
- Repository verification cannot be completed

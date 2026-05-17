# Nexora TV — HANDOFF

## Current Handoff

Active milestone:

`M7 Local Profile Persistence Foundation`

Active task:

`M7-TASK-001 Local Profile Repository & Saved Profiles Shell`

Status:

`READY FOR DEVELOPER`

## Last Locked Milestone

`M6 Playlist Profile & Legal Source Input Foundation`

Status: `LOCKED`

Director LOCKED: YES

Lock evidence:

- Build Evidence: PASSED
- User Runtime Test: PASSED
- QA Tester: PASSED
- Documentation Memory: PASSED
- Protected systems stable
- Legal/compliance risk: none detected

## Current Developer Handoff

Read first:

- `docs/START_HERE.md`
- `docs/PROJECT_MEMORY.md`
- `docs/MILESTONE_STATUS.md`
- `docs/NEXT_TASK.md`
- `docs/PROTECTED_SYSTEMS.md`
- `docs/SAFE_CODE_ENGINE.md`
- `docs/APP_BACKEND_INTEGRATION.md`
- `docs/milestones/M7_LOCAL_PROFILE_PERSISTENCE_FOUNDATION.md`
- `docs/agents/DEVELOPER.md`

Task:

Build `M7-TASK-001 Local Profile Repository & Saved Profiles Shell`.

Important:

- Preserve M6 profile input shell behavior.
- Keep changes additive and minimal.
- Keep profile flow local/mock-safe.
- No production connection is approved.
- No unsafe sensitive-data persistence is approved.
- No protected system rewrite.
- Follow Safe Code Engine.

## Required Return

```text
Result:
DONE / PARTIAL / BLOCKED

Preflight:
- active task confirmed / blocked reason

Changed Files:
- ...

Scope Confirmation:
- ...

Build Command:
- ...

Build Result:
- CONFIRMED / NOT CONFIRMED + reason

Runtime Evidence:
- REQUIRED + confirmed details / NOT REQUIRED / NOT CONFIRMED + reason

Risk:
- ...

Return To Director:
- QA_TESTER or DIRECTOR
```

## Window Transition Rule

Do not move to a new chat window right now.

Continue M7 in the current window.

After every future milestone is fully locked, Director must tell the user:

`Yeni pencereye geçmek güvenli.`

Each new future milestone should start in a new chat window unless the user cancels it.

## Workflow

Director → Developer → QA Tester → Documentation Memory → Director

Safe Code Engine applies across the whole workflow.

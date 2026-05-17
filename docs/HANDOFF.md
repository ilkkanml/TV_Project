# Nexora TV — HANDOFF

## Current Handoff

Active milestone:

`M6 Playlist Profile & Legal Source Input Foundation`

Active task:

`M6-TASK-001 Playlist Profile Model & Legal Input Shell`

Status:

`BLOCKED — BUILD/RUNTIME EVIDENCE REQUIRED`

## Current Blocker

QA Tester returned FAIL because required evidence is missing:

- Build compile evidence missing
- Profile screen runtime render not confirmed

Developer returned no code changes and no confirmed build/runtime result.

## Required Next Action

Provide real build/runtime evidence before QA can continue.

Required build command:

```bash
./gradlew :app:assembleDebug
```

Required runtime confirmation:

- App launches
- Profile screen opens
- Legal ownership notice is visible
- Empty/invalid/saved local shell states work
- Back/Home navigation remains safe

## Safe Code Engine

Safe Code Engine is active and mandatory.

Read:

- `docs/SAFE_CODE_ENGINE.md`

Rules:

- Developer must not recommend QA without required build/runtime evidence.
- QA must not PASS without required evidence.
- Documentation Memory must preserve blocker state until evidence exists.

## Last Locked Milestone

`M5 Content Library & Navigation Expansion`

Status: `LOCKED`

Director LOCKED: YES

Lock evidence:

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
- `docs/milestones/M6_PLAYLIST_PROFILE_FOUNDATION.md`
- `docs/agents/DEVELOPER.md`

Task:

Provide build/runtime evidence for `M6-TASK-001 Playlist Profile Model & Legal Input Shell`.

No new feature work is approved.

## Required Return

```text
Result:
DONE / PARTIAL / BLOCKED

Changed Files:
- None unless evidence fix required

Build Command:
- ...

Build Result:
- CONFIRMED / NOT CONFIRMED + reason

Runtime Evidence:
- CONFIRMED / NOT CONFIRMED + details

Risk:
- ...

Return To Director:
- QA_TESTER only if required evidence is confirmed
```

## Workflow

Director → Developer → QA Tester → Documentation Memory → Director

Safe Code Engine applies across the whole workflow.

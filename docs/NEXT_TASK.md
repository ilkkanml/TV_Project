# Nexora TV — NEXT_TASK

## Current Status

Active task ready for Developer.

## Current Active Milestone

`M7 Local Profile Persistence Foundation`

Status: `ACTIVE`

## Current Active Task

`M7-TASK-001 Local Profile Repository & Saved Profiles Shell`

Status: `READY FOR DEVELOPER`

## Task Goal

Create a safe foundation for saved local profile shell management without connecting to external services.

## Developer Scope IN

- Read current runtime docs first
- Inspect M6 profile model and screen before changes
- Preserve existing M6 profile input shell behavior
- Add safe local profile repository direction
- Add saved profiles list shell
- Add active/selected profile state shell
- Add minimal add/edit/delete shell behavior
- Keep flow local/mock-safe
- Keep legal ownership notice visible
- Keep changes additive and minimal
- Follow Safe Code Engine

## Developer Scope OUT

- No bundled content/source
- No live provider connection
- No profile fetch/parsing
- No backend integration
- No cloud sync
- No payment changes
- No player core changes
- No auth changes
- No hidden API work
- No protected system rewrite
- No production release behavior

## Sensitive Data Rule

Do not introduce unsafe plain-text persistence for sensitive profile fields.

If secure local storage already exists, report whether it can be reused safely.

If secure storage is not already available, keep sensitive values mock/in-memory or ask Director for a decision before persistence.

## Protected Systems Permission

Protected systems are not approved for structural changes.

Allowed:

- Minimal additive screen/route entry only if required
- Existing component/style reuse
- Existing navigation pattern reuse

Not allowed:

- Playback Core rewrite
- Auth System rewrite
- Hidden Backend API changes
- TV Navigation System rewrite
- Compose TV Design System rewrite
- Premium Cinematic UX rewrite

## Safe Code Engine Requirement

Developer must return:

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

## Required Next Action

Send this task to Developer.

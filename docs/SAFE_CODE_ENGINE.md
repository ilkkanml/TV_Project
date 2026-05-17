# Nexora TV — Safe Code Engine

## Purpose

Safe Code Engine is the mandatory code-safety rule system for all implementation work.

It is not a new agent.
It is not a new department.
It is a required quality gate used by Director, Developer, QA Tester, and Documentation Memory.

## Core Rule

No code task may move to QA unless the Developer return includes enough evidence for QA to verify the work.

## Applies To

- Director task assignment
- Developer implementation
- QA Tester review
- Documentation Memory recording

## Director Rule

Before assigning code work, Director must ensure:

- Active task exists
- Scope is clear
- Protected system permission is clear
- Legal/compliance boundary is clear
- Expected build/runtime evidence is clear

## Developer Preflight Rule

Before changing files, Developer must confirm:

- Runtime docs were read
- Active task matches Director instruction
- Intended files/systems are identified
- Protected systems are not touched unless explicitly allowed
- Legal/compliance risk is not present
- Patch can stay minimal and additive

If any item fails, Developer must return BLOCKED.

## Developer Return Rule

Developer must not return DONE without:

- Changed files
- Scope confirmation
- Build command
- Build result, or exact build blocker reason
- Runtime evidence when a user-visible screen, route, navigation path, or player behavior is affected
- Risk statement
- Next recommended agent

## Build Evidence Rule

For Android app tasks, expected build command is normally:

```bash
./gradlew :app:assembleDebug
```

If Developer cannot run the command, the return must say:

- Build Result: NOT CONFIRMED
- Exact blocker reason
- Error log
- Recommendation must not be QA_TESTER unless Director explicitly accepts source-only review

## Runtime Evidence Rule

Runtime evidence is required when work affects:

- Screens
- Routes
- Navigation access
- Focus behavior
- Player shell/player UI
- Auth/device activation flow
- Any visible user flow

Runtime evidence must include:

- How the screen/flow was reached
- What was verified
- Whether back/home behavior stayed safe
- Any crash/error log if failed

## Route / Import / Access Rule

When adding a new screen, Developer must verify:

- Screen file exists
- Destination/route exists if needed
- NavHost/composable wiring exists if needed
- Import compiles
- There is a reachable entry path or a clear Director-approved test route

A screen that exists but cannot be reached is not QA-ready.

## QA Rule

QA Tester must fail/block if:

- Build compile evidence is missing
- Runtime evidence is missing for user-visible flow changes
- Route/access wiring is incomplete
- Developer return lacks changed files
- Protected system permission is unclear
- Legal/compliance risk appears

QA may acknowledge low code risk, but cannot PASS without required evidence.

## Documentation Memory Rule

Documentation Memory must record:

- Build evidence status
- Runtime evidence status
- QA blocker status
- Current task state

Documentation Memory must not mark tasks complete or milestones locked without full evidence chain.

## Output Impact

Developer output must include:

```text
Result:
DONE / PARTIAL / BLOCKED

Preflight:
- ...

Changed Files:
- ...

Scope Confirmation:
- ...

Build Command:
- ...

Build Result:
- CONFIRMED / NOT CONFIRMED

Runtime Evidence:
- REQUIRED + confirmed details / NOT REQUIRED / NOT CONFIRMED

Risk:
- ...

Return To Director:
- ...
```

## Stop Condition

If build/runtime evidence is required but missing, the task state must become:

`BLOCKED — BUILD/RUNTIME EVIDENCE REQUIRED`

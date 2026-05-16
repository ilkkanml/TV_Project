# Nexora TV — NEXT_TASK

## Current Status

Active task ready for Developer.

## Active Milestone

`M5 Content Library & Navigation Expansion`

## Active Task

`M5-TASK-002 Build Verification Infrastructure`

## Objective

Add minimal build verification infrastructure so Android build evidence can be produced for M5-TASK-001 and future milestones.

## Background

`M5-TASK-001 Content Library Model & Navigation Foundation` is implemented and static review is clean, but QA is blocked because actual build/runtime evidence is missing.

Known constraints:

- No GitHub Actions Android build workflow found
- Gradle wrapper is not present in repo
- Current execution environment could not clone/build/run Android app
- Runtime emulator/device smoke test still requires user/developer environment

## Scope IN

- Add minimal GitHub Actions Android build workflow if feasible
- Add Gradle wrapper files if feasible and safe
- Add or update build verification documentation if needed
- Validate build command target, preferably debug assemble
- Preserve existing app behavior
- Keep patch infrastructure-only unless build config requires a minimal fix

## Scope OUT

- No product feature changes
- No UI overhaul
- No playback rewrite
- No auth rewrite
- No backend/provider/payment work
- No protected system rewrite
- No illegal stream/source handling
- No milestone lock

## Allowed Files / Areas

Developer may work only where needed in these areas:

- `.github/workflows/`
- `gradle/`
- `gradlew`
- `gradlew.bat`
- build-related Gradle files if a minimal build fix is required
- `docs/` only for build verification notes if needed

## Protected Systems Permission

Protected systems must remain structurally stable.

Allowed:

- Build workflow addition
- Gradle wrapper addition
- Minimal build config correction if required for compilation

Not allowed:

- Playback core rewrite
- Auth flow rewrite
- Hidden backend/API work
- Navigation redesign
- Compose design system rewrite
- Product feature changes

## Required Return To Director

```text
Result:
DONE / PARTIAL / BLOCKED

Preflight:
- active task confirmed / blocked reason

Changed Files:
- file/path

Build Verification:
- local/CI build command
- passed / failed / not run

Summary:
- short item

Risk:
- none / short risk

Test:
- short checklist

Return To Director:
- next recommended agent/action
```

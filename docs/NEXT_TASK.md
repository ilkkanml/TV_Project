# Nexora TV — NEXT_TASK

Status: Runtime next-action entrypoint
Updated: 2026-05-21

## Current Status

No active milestone is open.

Last locked implementation milestone:

`M14 First Working Release / Internal Alpha Smoke Gate` — LOCKED

## Current Required Work Before M15

Perform codebase and documentation cleanup before opening the next milestone.

Reason:

- Some client docs were stale and still referenced M12/M13 as current.
- UI screen files have grown too large.
- Some repeated UI tokens and repeated patterns exist across screens.
- M15 UI/UX polish must not add visual code on top of bloated files.

## Current Cleanup Scope

Allowed:

- Align root/docs source-of-truth files.
- Standardize repo structure documentation.
- Extract large UI files into smaller component files.
- Remove duplicate or stale code after extraction.
- Centralize repeated UI tokens/components when safe.
- Keep behavior unchanged unless a bug is directly caused by the cleanup.

Not allowed:

- Production deploy
- Store release
- Payment enforcement
- Provider integration expansion
- Content hosting/channel selling
- Backend-owned stream/channel catalog
- DRM bypass
- Unauthorized scraping
- Heavy playback rewrite
- Protected system rewrite
- New feature flood

## Recommended Next Milestone Candidate

`M15 Internal Alpha UI/UX Polish Direction & Handoff Readiness`

M15 should open only after cleanup does not leave obvious documentation conflict or bloated screen structure behind.

## Immediate Work Order

1. Align client docs with M14/M15 truth.
2. Add repo structure standard.
3. Clean Home screen file split.
4. Clean repeated UI helper patterns.
5. Run/collect build evidence before calling cleanup passed.
6. Then Director may scope M15.

## Current Operating Rule

Do not claim PASSED until user test/build evidence exists.

Do not claim LOCKED without QA and Director approval.

# Nexora TV — Milestone Status

Status: Source-of-truth aligned
Updated: 2026-05-21

## Current Active Milestone

None — next milestone pending Director scoping.

## Last Locked Milestone

`M14 First Working Release / Internal Alpha Smoke Gate`

## M14 Status

LOCKED

## M14 Result

M14 validated the first working internal alpha smoke readiness across the Android TV / Fire TV client and local Platform API health/readiness checks.

Confirmed scope:

- APK generated: CONFIRMED
- Android app run: CONFIRMED
- Splash: CONFIRMED
- Login: CONFIRMED
- Activation `demo123`: CONFIRMED
- Home shell: CONFIRMED
- Focus/navigation: CONFIRMED
- Detail screen: CONFIRMED
- Player shell: CONFIRMED
- Crash/error: NONE REPORTED
- Platform `/health`: CONFIRMED
- Platform `/ready`: CONFIRMED with `{ "ready": true }`
- QA Result: PASS
- Documentation recorded
- Director lock approved

M14 canonical records are maintained in `TV_Project_Platform`:

- `docs/M14_SCOPE.md`
- `docs/M14_BUILDER_HANDOFF.md`
- `docs/M14_LOCAL_SMOKE_EVIDENCE.md`
- `docs/M14_QA_REVIEW.md`
- `docs/M14_LOCK_REPORT.md`

## Previous Locked Milestone

`M13 Platform API Service Foundation & Environment Contract`

## Current Director Direction

Move toward a cleaner internal alpha handoff only after documentation alignment.

Working target:

`M15 Internal Alpha UI/UX Polish Direction & Handoff Readiness`

Purpose:

- Define minimal TV-first UI/UX polish direction
- Preserve current M14 smoke-passed behavior
- Prepare internal APK handoff/install guidance
- Define repeatable smoke checklist
- Record known limitations
- Keep production/store/payment/provider/content work out of scope

## M14 Lock Boundary

M14 does not approve:

- Production deploy
- Store release
- Live database setup
- Hosting/domain/server work
- Payment enforcement
- Provider integration
- Content hosting/channel selling
- Backend-owned stream/channel catalog
- Android bridge implementation
- Auth/session/token implementation
- Heavy backend implementation
- Heavy Android implementation
- Protected system rewrite
- Illegal IPTV/source behavior
- DRM bypass or unauthorized scraping

## Documentation Alignment Note

Older references to `v0.0`, `M2`, `M12`, or `M13 as next milestone` are legacy/outdated unless explicitly marked as historical context.

Runtime truth priority now follows:

1. `TV_Project_Platform/docs/MILESTONE_STATUS.md`
2. `TV_Project_Platform/docs/NEXT_TASK.md`
3. `TV_Project_Platform/docs/PROJECT_MEMORY.md`
4. `TV_Project/docs/MILESTONE_STATUS.md`
5. `TV_Project/docs/NEXT_TASK.md`

## Recommended Next Action

Director may scope M15 after this source-of-truth cleanup is confirmed.

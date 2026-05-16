# Nexora TV — NEXT_TASK

## Current Status

Active task ready for Developer.

## Active Milestone

`M5 Content Library & Navigation Expansion`

## Active Task

`M5-TASK-001 Content Library Model & Navigation Foundation`

## Objective

Create a safe mock-data content library foundation and improve content navigation clarity without touching protected systems.

## Scope IN

- Add or refine mock content models for Live / Movies / Series
- Organize Home content into clearer category rows
- Prepare a safe detail-screen foundation if needed
- Prepare content-selected Player route foundation if needed
- Keep all data local/mock
- Keep UI TV-friendly and remote-readable
- Preserve current Splash → Login → Activation → Home → Player flow

## Scope OUT

- No real backend
- No real provider/API integration
- No payment
- No production auth changes
- No illegal IPTV playlist
- No unauthorized streams
- No DRM bypass
- No token/cookie handling
- No protected system rewrite
- No UI overhaul
- No large architecture rewrite

## Allowed Files / Areas

Developer may work only where needed in these areas:

- `app/src/main/java/com/nexora/tv/ui/screens/`
- `app/src/main/java/com/nexora/tv/navigation/`
- New local mock data/model files under `app/src/main/java/com/nexora/tv/` if needed

## Protected Systems Permission

Protected systems must remain structurally stable.

Allowed:

- Additive route/detail placeholder wiring if minimal
- Mock content selection foundation

Not allowed:

- Playback core rewrite
- Auth flow rewrite
- Hidden backend/API work
- Navigation system redesign
- Compose design system rewrite

## Return To Director With

```text
Result:
DONE / PARTIAL / BLOCKED

Preflight:
- active task confirmed / blocked reason

Changed Files:
- file/path

Summary:
- short item

Risk:
- none / short risk

Test:
- short checklist

Return To Director:
- next recommended agent/action
```

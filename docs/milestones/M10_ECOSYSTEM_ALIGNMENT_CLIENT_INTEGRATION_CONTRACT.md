# M10 — Ecosystem Alignment & Client Integration Contract

## Status

LOCKED

## Task

`M10-TASK-001 Core Ecosystem Contract & Cross-Repo Roadmap Alignment`

Status: PASSED / COMPLETED

## Purpose

M10 aligned Nexora TV as a legal Core Media Player Ecosystem and defined the integration contract between:

- `TV_Project`: Android TV / Fire TV first client
- `TV_Project_Platform`: Core Account / Device / License / Admin / Remote Config / App Version / Profile Transfer center

## Completed Scope

- Ecosystem alignment
- Android ↔ Platform responsibility split
- Client Integration Contract
- Contract hardening
- Device identity policy alignment
- Legal public language alignment
- Platform ↔ Android roadmap alignment
- Security & Privacy review
- Legal Compliance review
- Backend/Database feasibility review
- Final documentation consistency check

## M10 Contract Files

- `docs/ECOSYSTEM_CONTRACT.md`
- `docs/CLIENT_INTEGRATION_CONTRACT.md`
- `docs/DEVICE_IDENTITY_POLICY.md`
- `docs/LEGAL_PUBLIC_LANGUAGE.md`
- `docs/PLATFORM_ANDROID_ROADMAP.md`
- `docs/reviews/M10_SECURITY_PRIVACY_REVIEW.md`
- `docs/reviews/M10_LEGAL_COMPLIANCE_REVIEW.md`

## Key Decisions

- Platform backend is the source of truth for account/device/license/config/app version.
- Android app is the first client.
- Real hardware MAC address is not primary device identity.
- App-generated install/device GUID and backend-assigned `platform_device_id` are the approved direction.
- First app remains free until final release level.
- Payment/subscription enforcement remains inactive during early/free launch.
- Contract comes before database/API/Android bridge implementation.
- M11 candidate should audit Platform source-of-truth before implementation.

## Legal Boundary

Nexora TV remains a legal media player/client ecosystem.

Not approved:

- Content hosting
- Broadcasting
- Channel selling
- Bundled streams
- Rights-protection bypass or equivalent circumvention
- Unauthorized scraping
- Credential sharing
- Illegal restreaming

Allowed development sources:

- Mock data
- Permitted test media
- Public demo media
- User-authorized legal sources
- Licensed provider/API integrations with explicit future approval

## Lock Guardrails

M10 was documentation/contract only.

- No app code changed
- No backend code changed
- No database implementation approved
- No payment enforcement approved
- No provider integration approved
- Protected systems preserved
- Legal/compliance boundary preserved

## Next Candidate

`M11 Platform Source-of-Truth Audit`

M11 must be explicitly opened by Director before any work begins.

M11 should audit the Platform repo before implementation.

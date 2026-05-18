# Nexora TV — NEXT_TASK

## Current Status

M12 opened by Director for scope and guardrail definition.

## Current Active Milestone

`M12 Platform Database Baseline & Migration Foundation`

## Current Active Task

`M12-TASK-001 Platform Database Baseline Scope & Guardrail Definition`

## M12-TASK-001 Status

OPEN / SCOPING

## Task Owner Flow

Director → Database Architect report → Technical Director/System Architect risk check if needed → Director implementation decision → Builder only if approved → User Test → QA → Documentation → Director lock.

## M12 Initial Scope

Define the safe database foundation before implementation.

IN:

- Prisma migration baseline policy
- Local database setup validation plan
- Migration naming/versioning policy
- Seed policy
- Rollback policy
- Data retention/deletion policy
- Sensitive temporary profile-transfer data lifecycle
- Audit-log baseline expectations
- Database service/connection foundation plan

OUT:

- Production DB deployment
- Running real migrations
- Payment enforcement
- Provider integration
- Content hosting
- Channel selling
- Platform-owned stream catalog
- Android bridge implementation
- Auth/session/token implementation unless separately approved

## Guardrails

- No production deployment without approval
- No payment enforcement
- No provider integration
- No content hosting
- No channel selling
- Legal media player boundary preserved
- Protected systems preserved
- No illegal IPTV list/source handling
- No DRM bypass
- No unauthorized stream scraping

## Required Next Action

Request Database Architect report for M12 baseline/migration foundation.

Do not start code, Prisma migration, DB execution, API implementation, or Android bridge yet.

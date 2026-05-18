# Nexora TV — NEXT_TASK

## Current Status

M12-TASK-003 local-only implementation plan recorded.

## Current Active Milestone

`M12 Platform Database Baseline & Migration Foundation`

## Current Active Task

`M12-TASK-003 Local-only Migration Baseline Implementation Plan`

## M12-TASK-001 Status

REPORT RECORDED / COMPLETED

## M12-TASK-002 Status

POLICY DRAFT RECORDED / COMPLETED

## M12-TASK-003 Status

OPEN / IMPLEMENTATION PLAN RECORDED

## Task Owner Flow

Director → Builder only if implementation is explicitly approved → User Test / local verification → QA → Documentation → Director lock.

## M12-TASK-003 Output

Platform repo planning document:

- `docs/M12_LOCAL_MIGRATION_BASELINE_IMPLEMENTATION_PLAN.md`

Plan areas:

- Local-only target boundary
- Allowed future files/areas
- Required Builder preflight
- Candidate commands after approval
- Required evidence if implemented
- QA requirement if implemented
- Legal database boundary

## Guardrails

- No production deployment without approval
- No Prisma migration execution until a separate Builder task is approved
- No payment enforcement
- No provider integration
- No content hosting
- No channel selling
- Legal media player boundary preserved
- Protected systems preserved
- No unauthorized source handling
- No rights-bypass behavior
- No unapproved stream extraction behavior

## Required Next Action

Director must decide whether to approve Builder for local-only migration baseline implementation.

Do not start code, Prisma migration, DB execution, API implementation, or Android bridge yet.

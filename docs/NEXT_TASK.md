# Nexora TV — NEXT_TASK

## Current Status

M12-TASK-002 policy draft recorded.

## Current Active Milestone

`M12 Platform Database Baseline & Migration Foundation`

## Current Active Task

`M12-TASK-002 Database Migration / Seed / Rollback / Retention Policy Draft`

## M12-TASK-001 Status

REPORT RECORDED / COMPLETED

## M12-TASK-002 Status

OPEN / POLICY DRAFT RECORDED

## Task Owner Flow

Director → Database Architect report → Policy draft → Systems Architect / Security Privacy review if needed → Director implementation decision → Builder only if approved → User Test → QA → Documentation → Director lock.

## M12-TASK-002 Output

Platform repo policy draft:

- `docs/M12_DATABASE_POLICY_DRAFT.md`

Policy areas:

- Migration baseline policy
- Local DB validation policy
- Production migration policy
- Seed policy
- Rollback policy
- Retention/deletion policy
- Sensitive data policy
- Legal database boundary

## Guardrails

- No production deployment without approval
- No Prisma migration execution
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

Director should review the policy draft and decide if Systems Architect / Security Privacy review is required.

Do not start code, Prisma migration, DB execution, API implementation, or Android bridge yet.

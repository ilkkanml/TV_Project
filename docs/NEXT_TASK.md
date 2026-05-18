# Nexora TV — NEXT_TASK

## Current Status

M12-TASK-003 local-only baseline migration files added, but verification evidence is missing.

## Current Active Milestone

`M12 Platform Database Baseline & Migration Foundation`

## Current Active Task

`M12-TASK-003 Local-only Migration Baseline Implementation Plan`

## M12-TASK-001 Status

REPORT RECORDED / COMPLETED

## M12-TASK-002 Status

POLICY DRAFT RECORDED / COMPLETED

## M12-TASK-003 Status

PARTIAL / BUILD-RUNTIME EVIDENCE REQUIRED

## Task Owner Flow

Director → Local verification → QA only after evidence → Documentation → Director lock.

## M12-TASK-003 Changed Files

Platform repo:

- `apps/api/prisma/migrations/migration_lock.toml`
- `apps/api/prisma/migrations/20260518120000_m12_initial_platform_database_baseline/migration.sql`

## Evidence Status

- Build/typecheck: NOT CONFIRMED
- Prisma generate: NOT CONFIRMED
- Local migration apply: NOT CONFIRMED
- Local DB verification: NOT CONFIRMED

Reason:

- Current execution environment could not clone/run repository commands because external GitHub DNS access failed.

## Required Next Action

Run local verification before QA:

```bash
pnpm install
docker compose up -d
pnpm db:generate
pnpm db:migrate
pnpm --filter @tv-platform/api run typecheck
```

## Guardrails

- No production deployment
- No production database mutation
- No payment enforcement
- No provider integration
- No content hosting
- No channel selling
- Legal media player boundary preserved
- Protected systems preserved
- No unauthorized source handling
- No rights-bypass behavior
- No unapproved stream extraction behavior

QA cannot PASS until evidence is provided.

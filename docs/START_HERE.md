# Nexora TV — START HERE

## Source of Truth

This repository is the source of truth.

Chat history is not the source of truth.

## Read First

1. `docs/START_HERE.md`
2. `docs/PROJECT_MEMORY.md`
3. `docs/MILESTONE_STATUS.md`
4. `docs/NEXT_TASK.md`
5. `docs/HANDOFF.md`
6. `docs/DEPARTMENT_BOOT_PROTOCOL.md`
7. `docs/DEPARTMENT_ROLE_CARDS.md`
8. `docs/STUDIO_BIBLE.md`
9. `docs/PROTECTED_SYSTEMS.md`
10. `docs/SAFE_CODE_ENGINE.md`
11. `docs/APP_BACKEND_INTEGRATION.md`
12. `docs/DECISION_LOG.md`
13. Relevant agent file from `docs/agents/`

## Current Truth

- Project: Nexora TV
- Repository: `https://github.com/ilkkanml/TV_Project.git`
- Product type: legal Core Media Player Ecosystem / Android TV + Fire TV first client
- Platform center: `TV_Project_Platform`
- Android client: `TV_Project`
- Current active milestone: `M12 Platform Database Baseline & Migration Foundation`
- M12 status: OPEN / DIRECTOR SCOPING
- Current active task: `M12-TASK-001 Platform Database Baseline Scope & Guardrail Definition`
- M12-TASK-001 status: OPEN / SCOPING
- Last locked milestone: `M11 Platform Source-of-Truth Audit`
- M11 status: LOCKED
- M11-TASK-001 status: PASSED / COMPLETED
- Previous locked milestone: `M10 Ecosystem Alignment & Client Integration Contract`
- M10 status: LOCKED
- M10-TASK-001 status: PASSED / COMPLETED
- Required next action: Database Architect report for M12 baseline/migration foundation

## Lean Workflow Rule

Use the shortest safe workflow.

- Department report only when useful
- Director decides
- Builder only when implementation is approved
- QA only for code/runtime/release risk
- Documentation only for runtime truth, milestone status, major decision, source-of-truth contract, or milestone finalization
- Minor reviews do not require CHANGELOG/DECISION_LOG updates
- Director may handle simple documentation updates in the main Director thread

## Department Boot Rule

Every department must start by reading:

- `docs/DEPARTMENT_BOOT_PROTOCOL.md`
- `docs/DEPARTMENT_ROLE_CARDS.md`
- Its own file in `docs/agents/`

Departments report only. Director decides.

If runtime docs conflict, stop and report `DOCUMENTATION_CONFLICT`.

## M12 Open Summary

M12 is opened as platform database baseline and migration foundation scoping milestone.

Initial M12 scope:

- Prisma migration baseline policy
- Local database setup validation plan
- Migration naming/versioning policy
- Seed policy
- Rollback policy
- Data retention/deletion policy
- Sensitive temporary profile-transfer data lifecycle
- Audit-log baseline expectations
- Database service/connection foundation plan

M12 guardrails:

- No backend implementation approved yet
- No Prisma migration execution approved yet
- No production database deployment approved
- No Android bridge approved
- No payment enforcement approved
- No provider integration approved
- No content hosting/channel selling approved
- Protected systems preserved
- Legal media player boundary preserved

## M11 Lock Summary

M11 locked as Platform source-of-truth audit/documentation milestone.

Completed scope:

- Platform repository audit
- M11 audit report
- Platform README
- Platform START_HERE
- Platform source-of-truth docs
- API contract alignment doc
- Database baseline doc
- Security/session policy doc
- Legal boundary doc

Guardrails preserved:

- No backend implementation approved
- No database migration approved
- No Android bridge approved
- No payment enforcement approved
- No provider integration approved
- Protected systems preserved
- Legal media player boundary preserved

## Required Next Action

Database Architect should report on M12 database baseline and migration foundation before any implementation decision.

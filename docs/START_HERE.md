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
- Current active milestone: `M11 Platform Source-of-Truth Audit`
- Current active task: `M11-TASK-001 Platform Repository Source-of-Truth Audit`
- M11 status: ACTIVE
- M11-TASK-001 status: READY
- Last locked milestone: `M10 Ecosystem Alignment & Client Integration Contract`
- M10 status: LOCKED
- M10-TASK-001 status: PASSED / COMPLETED
- Required next action: Audit `TV_Project_Platform` repository source of truth

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

## M11 Guardrails

M11 is audit-only.

Not approved:

- Backend implementation
- Database implementation
- Android bridge implementation
- Payment enforcement
- Provider integration
- Content hosting
- Channel selling
- Bundled streams
- Rights-protection bypass or equivalent circumvention
- Unauthorized scraping
- Credential sharing
- Protected-system rewrite

## M10 Lock Summary

M10 locked as documentation/contract milestone.

Guardrails preserved:

- App code unchanged
- Backend code unchanged
- Database implementation not approved
- Payment enforcement not approved
- Provider integration not approved
- Protected systems preserved
- Legal/compliance boundary preserved

## Required Next Action

Audit `TV_Project_Platform` repository source of truth.
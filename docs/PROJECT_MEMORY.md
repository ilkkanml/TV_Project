# Nexora TV — Project Memory

## Purpose

This document is the compact memory source for new ChatGPT windows.

Chat history is disposable. Repository docs are the source of truth.

## Project Name

Nexora TV

## Repository

https://github.com/ilkkanml/TV_Project.git

## Product Type

Legal Core Media Player Ecosystem.

- `TV_Project` = Android TV / Fire TV first client
- `TV_Project_Platform` = Core Account / Device / License / Admin / Remote Config / App Version / Profile Transfer center

## Current Runtime Truth

- Current active milestone: None
- Current active task: None
- Last locked milestone: M11 Platform Source-of-Truth Audit
- M11 status: LOCKED
- M11-TASK-001 status: PASSED / COMPLETED
- Previous locked milestone: M10 Ecosystem Alignment & Client Integration Contract
- M10 status: LOCKED
- M10-TASK-001 status: PASSED / COMPLETED
- Required next action: Director may open next milestone candidate: M12 Platform Database Baseline & Migration Foundation

Runtime truth priority:

1. `docs/MILESTONE_STATUS.md`
2. `docs/NEXT_TASK.md`
3. `docs/HANDOFF.md`
4. `docs/PROJECT_MEMORY.md`
5. `docs/START_HERE.md`
6. Agent files

## Lean Workflow Rule

Avoid unnecessary procedure.

Use the shortest safe workflow:

1. Department report when useful
2. Director decision
3. Builder only if implementation is approved
4. QA only for code/runtime/release risk
5. Documentation only when runtime truth, milestone status, source-of-truth contract, or major decision changes
6. Milestone final docs can be batched

Do not run Documentation Memory for every minor review.

Do not update CHANGELOG or DECISION_LOG after every small department report.

Director may handle simple documentation updates directly in the main Director thread.

## Core Legal Rule

Nexora is a legal player/client platform.

It does not provide content, channels, broadcasts, bundled media, circumvention tools, unauthorized source access, credential sharing support, or restreaming behavior.

Allowed development sources:

- Mock data
- Test streams with permission
- Public demo streams
- User-owned licensed streams
- Legal provider/API integrations with explicit future approval

## M11 Lock Summary

M11 Platform Source-of-Truth Audit is LOCKED.

Completed scope:

- Platform repository audit
- Platform repo structure review
- API/database/security/legal gap review
- M11 audit report
- Platform README
- Platform START_HERE
- Platform source-of-truth docs
- API contract alignment doc
- Database baseline doc
- Security/session policy doc
- Legal boundary doc

Platform docs added in `TV_Project_Platform`:

- `README.md`
- `docs/START_HERE.md`
- `docs/PLATFORM_SOURCE_OF_TRUTH.md`
- `docs/API_CONTRACT_ALIGNMENT.md`
- `docs/DATABASE_BASELINE.md`
- `docs/SECURITY_SESSION_POLICY.md`
- `docs/LEGAL_BOUNDARY.md`

M11 guardrails preserved:

- No backend implementation approved
- No database migration approved
- No Android bridge approved
- No payment enforcement approved
- No provider integration approved
- Protected systems preserved
- Legal media player boundary preserved

## M12 Candidate

Next candidate milestone:

`M12 Platform Database Baseline & Migration Foundation`

M12 should address:

- Prisma migration baseline
- database setup validation
- seed policy
- migration/rollback policy
- data retention/deletion policy
- database connection/service foundation planning

M12 must not approve payment enforcement, provider integration, content hosting, or Android bridge unless explicitly scoped by Director.

## M10 Lock Summary

M10 Ecosystem Alignment & Client Integration Contract is LOCKED.

M10 was documentation/contract alignment only.

## Department Boot Protocol

Department boot docs:

- `docs/DEPARTMENT_BOOT_PROTOCOL.md`
- `docs/DEPARTMENT_ROLE_CARDS.md`

Every department must read boot protocol, role cards, runtime docs, protected systems, Safe Code Engine, and its own agent file before reporting.

Departments report only. Director decides.

## Safe Code Engine

Status: ACTIVE

Document:

- `docs/SAFE_CODE_ENGINE.md`

Rule:

- Developer cannot send code work to QA without required build/runtime evidence.
- QA cannot PASS without required evidence.
- Documentation Memory must record build/runtime evidence state.
- Missing evidence keeps the task BLOCKED.

## Locked Milestones

- M1 Foundation: LOCKED
- M2 Playback Expansion: LOCKED
- M3 Premium UI Expansion: LOCKED
- M4 Auth & Device Activation Foundation: LOCKED
- M5 Content Library & Navigation Expansion: LOCKED
- M6 Playlist Profile & Legal Source Input Foundation: LOCKED
- M7 Local Profile Persistence Foundation: LOCKED
- M8 TV Navigation & Access Polish: LOCKED
- M9 Startup Flow & Session Entry Polish: LOCKED
- M10 Ecosystem Alignment & Client Integration Contract: LOCKED
- M11 Platform Source-of-Truth Audit: LOCKED

## Current Code Reality

- Android client code unchanged by M11
- Platform source-of-truth docs added by M11
- M11 did not approve backend/API/database implementation

## User Preference

- Turkish responses
- Minimal explanations
- Direct instructions
- Continuous progress
- Keep docs updated so new chats can continue
- Do not say PASSED/LOCKED without required evidence
- Avoid unnecessary procedure and repeated documentation loops

# Nexora TV — Code Hygiene Directive

Status: ACTIVE
Updated: 2026-05-21

## Purpose

Prevent bloated, duplicated, overlapping, and patch-stacked code during future implementation.

This directive applies before any Builder patch, UI polish pass, playback change, platform bridge work, or refactor.

## Director Rule

Before writing code, Builder must preserve structure first.

Do not add code on top of existing code when the correct action is to extract, reuse, or cleanly replace a narrow section.

## Mandatory Coding Rules

- Keep files small and purpose-specific.
- Do not repeat the same UI block, network logic, parsing logic, navigation action, or state logic in multiple places.
- Do not create patch-over-patch fixes.
- Do not leave old code behind after replacing behavior.
- Do not mix UI rendering, network calls, parsing, persistence, and navigation inside the same function when it can be separated safely.
- Do not rewrite protected systems unless explicitly approved.
- Do not make broad refactors without a scoped plan.
- Do not introduce new feature scope while cleaning code.
- Do not claim PASSED until user test confirms.
- Do not claim LOCKED until QA and Director approval.

## File Size Guidance

Soft limits:

- Screen file: target under 300 lines when possible.
- Component file: target under 200 lines.
- Data/service file: target under 250 lines.
- Functions: prefer short, single-purpose functions.

If a file exceeds the soft limit, Builder must explain why or propose extraction.

## Preferred Structure

UI screens should delegate to:

- reusable components
- small section composables
- state/controller helpers
- service/loaders outside UI files
- shared theme/tokens

Network/service logic should delegate to:

- shared HTTP helper
- parser/mapper helpers
- repository/service layer

Navigation should stay centralized and predictable.

## No-Macaroni Rule

If code becomes hard to understand because logic is layered over old logic, the patch must stop.

Director must request a cleanup plan before further implementation.

## Audit Flags

Mark as risk when found:

- duplicate composables with different names but same layout role
- repeated color/token constants across files
- duplicated HTTP connection setup
- Thread/Handler logic inside composable-heavy screen files
- very long screen files
- many responsibilities inside one file
- dead or stale code after feature changes
- inconsistent source-of-truth documents

## Safe Cleanup Order

1. Identify bloated files.
2. Identify duplication and responsibility mixing.
3. Propose minimal extraction plan.
4. Patch only one area at a time.
5. User test.
6. QA review if runtime behavior changed.

## Current Director Instruction

Before M15 implementation, perform a code hygiene audit.

M15 UI/UX polish must not add visual code on top of bloated screen files without first deciding whether extraction is needed.

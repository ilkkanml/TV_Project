# Nexora TV — Department Model Standard

Status: ACTIVE
Updated: 2026-05-21

## Purpose

Define the official department model for Nexora TV so project progress is reviewed through clear responsibility areas without creating process bloat.

This model creates a quality buffer before major milestones and clean baselines.

## Core Principle

Departments report.

Director decides.

No department can independently mark a milestone as PASSED, QA PASSED, LOCKED, or RELEASE READY.

## Official Model

Nexora uses a 12-Gate Department Model.

Structure:

```text
Director
│
├─ Product / Scope
├─ Technical Architecture
├─ Builder / Implementation
├─ UI / UX / Premium Design
├─ Playback Engineer
├─ Documentation / Release Control
│
├─ Code Hygiene Reviewer
├─ QA Tester
├─ Performance Reviewer
├─ Security / Privacy
├─ Legal / Compliance
└─ Remote Navigation
```

Director is the decision authority above departments.

## Active Core Departments

### 1. Product / Scope

Purpose:

- Feature scope
- Milestone IN / OUT
- Scope drift prevention
- Product priority

Output:

- `SCOPE CLEAR`
- `SCOPE DRIFT`
- `SCOPE HOLD`

### 2. Technical Architecture

Purpose:

- Module structure
- File responsibility
- Protected system impact
- Refactor safety
- Dependency direction

Output:

- `ARCHITECTURE CLEAR`
- `STRUCTURE RISK`
- `REFACTOR HOLD`

### 3. Builder / Implementation

Purpose:

- Code patch
- File cleanup
- Build preparation
- Minimal implementation

Output:

- `PATCH READY`
- `PATCH APPLIED`
- `BUILD EVIDENCE REQUIRED`
- `IMPLEMENTATION BLOCKED`

### 4. UI / UX / Premium Design

Purpose:

- TV-first readability
- Premium streaming feel
- Layout spacing
- Visual hierarchy
- Focus-visible design quality

Output:

- `UX CLEAR`
- `VISUAL POLISH REQUIRED`
- `UI HOLD`

### 5. Playback Engineer

Purpose:

- Media3 / ExoPlayer behavior
- Player overlay
- Fullscreen continuity
- Stream switching
- Recovery and error behavior

Output:

- `PLAYBACK CLEAR`
- `PLAYBACK RISK`
- `PLAYBACK BLOCKER`

### 6. Documentation / Release Control

Purpose:

- Runtime truth
- Handoff records
- Known limitations
- Commit SHA records
- Baseline reports
- Release readiness notes

Output:

- `DOCS CLEAR`
- `DOCS CONFLICT`
- `BASELINE RECORD READY`

## Gate / Control Departments

### 7. Code Hygiene Reviewer

Purpose:

- File bloat detection
- Duplicate code detection
- Patch stacking detection
- Dead/stale code detection
- Responsibility mixing detection

Output:

- `CODE CLEAN`
- `MACARONI RISK`
- `DUPLICATION RISK`
- `CLEANUP REQUIRED`

### 8. QA Tester

Purpose:

- Smoke test
- Regression review
- Runtime bug check
- Navigation sanity check
- Lock gate evidence review

Output:

- `QA PASS`
- `QA BLOCKED`
- `REGRESSION FOUND`

Rule:

QA cannot pass without required evidence.

### 9. Performance Reviewer

Purpose:

- TV runtime performance
- Large list behavior
- Recomposition risk
- Heavy background/drawing risk
- Startup/screen transition responsiveness

Output:

- `PERFORMANCE CLEAR`
- `PERFORMANCE RISK`
- `PERFORMANCE BLOCKER`

### 10. Security / Privacy

Purpose:

- Credential handling
- Token/session privacy
- Local storage risk
- Device identity risk
- Permission review

Output:

- `SECURITY CLEAR`
- `PRIVACY RISK`
- `SECURITY HOLD`

### 11. Legal / Compliance

Purpose:

- Legal media-player boundary
- No content hosting
- No channel selling
- No unauthorized scraping
- No DRM bypass
- No stream relay/proxy/transcoding

Output:

- `LEGAL CLEAR`
- `LEGAL RISK`
- `LEGAL HOLD`

Rule:

Legal HOLD stops implementation until Director resolves scope.

### 12. Remote Navigation

Purpose:

- TV remote focus flow
- Back behavior
- OK/Enter behavior
- Initial focus
- Focus trap prevention

Output:

- `REMOTE NAV CLEAR`
- `FOCUS RISK`
- `REMOTE BLOCKER`

## Usage Modes

### Small Code Patch

Required roles:

```text
Director
Builder / Implementation
Code Hygiene Reviewer
QA Tester if runtime behavior changed
Documentation / Release Control if source-of-truth changed
```

### UI / UX Patch

Required roles:

```text
Director
UI / UX / Premium Design
Builder / Implementation
Remote Navigation
Code Hygiene Reviewer
QA Tester
```

### Playback Patch

Required roles:

```text
Director
Playback Engineer
Technical Architecture
Builder / Implementation
Performance Reviewer
QA Tester
```

### Provider / Source / Credential Patch

Required roles:

```text
Director
Product / Scope
Security / Privacy
Legal / Compliance
Technical Architecture
Builder / Implementation if approved
QA Tester
```

### Milestone Lock

Required roles:

```text
Director
Product / Scope
Technical Architecture
Code Hygiene Reviewer
Build evidence
Runtime smoke evidence
QA Tester
Documentation / Release Control
Legal / Compliance when source/content/auth/payment risk exists
Security / Privacy when credentials/device/session risk exists
Performance Reviewer when runtime/performance risk exists
Remote Navigation when TV UI changed
Playback Engineer when player changed
```

## Clean Baseline Rule

A clean baseline can be created only when required gates report no known blocker.

Approved wording:

```text
This baseline has no known blocker, no known regression, and no known legal/compliance hold based on available evidence.
```

Forbidden wording:

```text
There are no bugs.
Everything is perfect.
Nothing can fail.
```

## No Process Bloat Rule

Do not call every department for every small task.

Use only the departments required by the actual risk area.

Director may skip unnecessary departments for documentation-only or low-risk cleanup work.

## Lock Rule

- User test is required before user-facing behavior is considered passed.
- QA is required before milestone LOCKED.
- Director approval is required for final baseline or milestone lock.

## Current Application

Before opening M15, Nexora should complete cleanup and create a clean baseline review using the relevant departments:

- Director
- Technical Architecture
- Code Hygiene Reviewer
- Builder / Implementation
- QA Tester after build/runtime evidence
- Documentation / Release Control
- UI / UX / Premium Design if visual behavior changed
- Remote Navigation if focus behavior changed

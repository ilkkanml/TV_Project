# Nexora TV — Branch Strategy

## Main Branch

`main`

Purpose:

- stable milestone builds
- production-safe states
- rollback-safe versions

Rules:

- no uncontrolled direct rewrite
- only approved milestone merges
- must remain runnable

---

## Development Branch

`dev`

Purpose:

- active integration branch
- current implementation state
- feature merge target

---

## Feature Branches

Naming pattern:

- feature/player-core
- feature/auth-system
- feature/cinematic-ui
- feature/home-screen

Purpose:

- isolated implementation work
- safe experimentation
- focused development

Feature branches are temporary.

---

# Protected Merge Rule

Before merge:

- build must compile
- playback stability must remain
- architecture compatibility must remain
- UX consistency must remain
- protected systems must remain intact

---

# Milestone Tagging

Major milestones should be tagged.

Examples:

- v0.1-foundation
- v0.2-playback
- v0.3-auth
- v0.4-cinematic-ui

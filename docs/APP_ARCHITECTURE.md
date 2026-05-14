# TV Project — App Architecture

## Platform

Primary target:

- Android TV
- Fire TV

## Architecture Goal

The application should use a clean modular structure so that IPTV logic, player logic, UI, and future subscription logic can evolve safely.

## Main Layers

```text
app
core
data
domain
features
ui
```

## Feature Modules

```text
auth
home
iptv_setup
live_tv
movies
series
player
settings
```

## v0.1 Navigation Flow

```text
Splash
Login
IPTV Setup
Home
Live TV
Player
Settings
```

## Foundation Principle

Milestone 1 should not overbuild the system.

The goal is to make the app open, navigate, connect to IPTV data later, and prepare the player screen safely.

# Nexora TV — App / Backend Integration Direction

## Status

Approved direction.

This document defines the boundary between the TV app and the web/backend system.

## Product Boundary

Nexora TV is a legal player/client platform.

The app does not provide content. Users may use only playlist/provider access they are legally allowed to use.

## Backend / Web Panel Owns

- User account
- Subscription status
- Device activation
- Payment status
- Reseller system
- App version check
- Force update rules
- Remote config
- Maintenance mode
- Feature flags
- Optional secure playlist/profile transfer to device

## App Owns

- Device identity creation
- Device registration with backend
- License/subscription check
- Playlist/profile management
- Encrypted local playlist profile storage
- Player screen access when license is valid
- Multi-profile switching

## Device Identity

MAC address is not the primary ID.

Primary ID:

```text
app_generated_device_id
```

Secondary signals:

```text
android_id
device_model
platform
app_version_code
app_version_name
install metadata
```

Android TV and Fire TV use the same identity model. Only `platform` changes.

Example payload:

```json
{
  "device_id": "app_generated_uuid",
  "android_id": "optional_signal",
  "platform": "android_tv",
  "device_name": "TCL Android TV",
  "app_version_code": 12,
  "app_version_name": "0.1.0"
}
```

## Playlist / Profile Ownership

Default playlist source of truth:

```text
Device local encrypted storage
```

Backend is not the default source of truth for user playlist profiles.

Reason:

- Product remains player-only
- User playlist/profile data stays under user control
- Backend does not need to permanently hold playlist profile data by default
- Compliance and data-safety boundaries stay cleaner

## Multi-Profile Direction

The app should support multiple playlist profiles.

Expected features:

- Create profile
- Name profile
- Select profile type
- Edit profile
- Delete profile
- Select active profile
- Switch profiles quickly
- Test profile connection
- Show invalid/expired profile messages

Expected source type direction:

```text
M3U URL
Xtream Codes
Portal later if approved
Local / JSON profile later if approved
```

All playlist/profile input must be user-provided and legally authorized.

## Web Playlist Push

Web panel may optionally send a playlist/profile to a selected user device.

This is a transfer helper, not default backend ownership.

Expected flow:

```text
User logs into web panel.
User selects own device.
User enters playlist/profile data.
Backend transfers the profile to the app.
App saves it into encrypted local storage.
Temporary backend copy may be removed after transfer.
Longer cloud sync requires explicit user consent.
```

MVP direction:

```text
Default: profiles stored on device.
Optional: web panel can push a profile to device.
Later: encrypted cloud sync only with explicit user consent.
```

## License Check Timing

The app should check license status:

```text
On app launch
Before player access
During active use at intervals
```

Recommended timing:

```text
App launch: required check
Player access: quick check
Active use: soft check every 15-30 minutes
```

## Offline Behavior

If a device previously verified a valid license, short offline tolerance is allowed.

If a device never verified a license, offline access is not allowed.

Recommended tolerance:

```text
6 hours soft grace
24 hours maximum hard limit
```

## Version Check / Force Update

The app should support version checks.

Example response:

```json
{
  "current_version_code": 12,
  "minimum_version_code": 10,
  "force_update": false,
  "apk_url": "https://example.com/app.apk",
  "changelog": "Performance improvements"
}
```

## Remote Config

The app should support remote config.

Example response:

```json
{
  "maintenance_mode": false,
  "maintenance_message": "Service temporarily unavailable.",
  "announcement": "New update available.",
  "minimum_version_code": 10,
  "features": {
    "vod_enabled": true,
    "series_enabled": true,
    "epg_enabled": true,
    "favorites_enabled": true,
    "multi_profile_enabled": true,
    "web_playlist_push_enabled": true
  }
}
```

## API Response Format

API responses should use JSON.

Success response:

```json
{
  "success": true,
  "code": "OK",
  "message": "Success",
  "data": {}
}
```

Error response:

```json
{
  "success": false,
  "code": "SUBSCRIPTION_EXPIRED",
  "message": "Subscription expired.",
  "data": null
}
```

Recommended error codes:

```text
DEVICE_NOT_ACTIVATED
DEVICE_BLOCKED
SUBSCRIPTION_EXPIRED
LICENSE_INVALID
FORCE_UPDATE_REQUIRED
MAINTENANCE_MODE
PLAYLIST_NOT_FOUND
PROFILE_INVALID
PROFILE_CONNECTION_FAILED
UNAUTHORIZED
RATE_LIMITED
SERVER_ERROR
```

Token format:

```text
Bearer token
```

## Security Direction

- Short-lived access token
- Backend-controlled refresh token
- Optional device token
- Encrypted local playlist profile storage
- Temporary web transfer cleanup
- Cloud sync only with explicit user consent

## Implementation Note

This is a direction document only.

Actual app/backend code integration requires a future approved milestone/task.

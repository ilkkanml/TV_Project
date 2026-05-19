# EA0 Device Bootstrap Handoff

Status: Active
Source Project: TV_Project_Platform
Target Project: TV_Project

## 1. Purpose

This handoff defines the early access device identity and licensing behavior that the Android TV / Fire TV app must implement.

The first early access release may be distributed before the full website/customer portal is ready.

The app must be prepared to create platform device records automatically through the backend.

## 2. Final Decision

Approved EA0 identity model:

```txt
Backend-generated Device ID + Backend-generated Activation Key
```

The app must not generate a permanent activation key as the source of truth.

The app must not contain a shared hardcoded activation key inside the APK.

The backend generates:

- `deviceId`
- `activationKey`

The backend stores:

- `deviceId`
- `activationKeyHash`
- `platformDeviceHash`
- platform/app/version/status/license metadata

The backend does not store:

- raw `activationKey`
- playlist/source contents
- provider credentials
- stream URLs

## 3. App Responsibility

The app must:

1. On first startup, create or collect a privacy-safe `platformDeviceHash`.
2. Call `POST /devices/bootstrap`.
3. Receive `deviceId` and `activationKey` from the backend.
4. Store `deviceId` and `activationKey` locally in secure storage.
5. Use `deviceId` and `activationKey` for `POST /license/check`.
6. Preserve local credentials across normal app updates.
7. On reinstall, attempt recovery with `platformDeviceHash`.

## 4. Backend Responsibility

The platform backend must:

1. Generate unique `deviceId`.
2. Generate strong `activationKey`.
3. Store only `activationKeyHash`.
4. Return raw `activationKey` only once during create/recovery.
5. Verify `activationKey` during license checks.
6. Allow revoke/block/disable later through owner dashboard.
7. Support best-effort reinstall recovery through `platformDeviceHash`.

## 5. First Startup Flow

```txt
App starts
↓
App checks local secure storage
↓
No local device credentials found
↓
App creates platformDeviceHash
↓
POST /devices/bootstrap
↓
Backend creates DeviceAccessRecord
↓
Backend returns deviceId + activationKey
↓
App stores deviceId + activationKey securely
↓
App calls POST /license/check
↓
Free launch access allowed if record is active
```

## 6. Normal App Update Behavior

Normal app update should keep local credentials.

Expected behavior:

```txt
Existing deviceId + activationKey remain stored
App calls license/check
Backend recognizes the device
Access continues
```

## 7. Uninstall / Reinstall Behavior

App-private local storage may be removed after uninstall.

Reinstall recovery flow:

```txt
App starts after reinstall
↓
No local deviceId / activationKey found
↓
App creates same/best-effort platformDeviceHash
↓
POST /devices/bootstrap
↓
Backend checks platformDeviceHash
↓
If matched, backend returns existing deviceId and rotates/generates new activationKey
↓
Old activationKey becomes invalid
↓
App stores new activationKey securely
```

Important:

- Reinstall recovery is best-effort.
- Factory reset may change platform identifiers.
- Different Android user/profile may break recognition.
- Signing key changes may break recognition.
- Manual owner recovery/reset can be added later.

## 8. Factory Reset Reality

The app cannot guarantee silent automatic recognition after every factory reset.

For EA0 free launch:

- Automatic bootstrap can continue.
- If the device cannot be recovered, backend may create a new DeviceAccessRecord.

For the future paid phase:

- Paid licensing should attach to the existing Device ID / Activation Key record when available.
- Do not force all EA0 users to receive new Device IDs by default.
- If credentials are lost and recovery fails, customer portal / owner support can reset or reconnect the device record later.

## 9. Required API Calls

### POST /devices/bootstrap

Request:

```json
{
  "platformDeviceHash": "...",
  "platform": "android_tv",
  "appVersion": "0.1.0",
  "existingDeviceId": "optional",
  "existingActivationKey": "optional"
}
```

Response:

```json
{
  "deviceId": "...",
  "activationKey": "returned only on create or recovery rotation",
  "status": "active",
  "licenseState": "free_launch_active",
  "freeLaunch": true,
  "paymentRequired": false,
  "message": "Free launch access is active."
}
```

### POST /license/check

Request:

```json
{
  "deviceId": "...",
  "activationKey": "...",
  "platform": "android_tv",
  "appVersion": "0.1.0"
}
```

Response:

```json
{
  "allowed": true,
  "state": "free_launch_active",
  "freeLaunch": true,
  "paymentRequired": false,
  "deviceId": "...",
  "deviceStatus": "active",
  "message": "Free launch access is active."
}
```

## 10. Secure Local Storage Requirement

The app must not store activation credentials in plain SharedPreferences if avoidable.

Use secure local storage appropriate to Android TV / Fire TV.

Minimum rule:

- Store `deviceId` locally.
- Store `activationKey` securely.
- Never log `activationKey`.
- Never include `activationKey` in crash/error logs.
- Never display `activationKey` in normal UI unless a later support/recovery workflow explicitly allows it.

## 11. Forbidden App Behavior

The app must not:

- Hardcode a universal activation key.
- Generate permanent activation keys as final authority.
- Store activation credentials in plain logs.
- Send playlist/source/provider data during bootstrap/license check.
- Require customer email/name for EA0.
- Block free launch due to missing payment.
- Redefine license states locally.
- Redefine platform API field names locally.
- Treat free launch as permanently free forever.

## 12. Shared State Values

Device statuses:

```txt
active
pending
disabled
blocked
revoked
```

License states:

```txt
free_launch_active
active
expired
suspended
device_revoked
blocked
```

Error categories:

```txt
connection_error
unsupported_app_version
invalid_device_credentials
access_disabled
access_blocked
device_revoked
license_suspended
rate_limited
unauthorized
forbidden
```

## 13. Paid Phase Preparation

The app should be built so paid licensing can be added later without rewriting identity.

Future paid phase rule:

```txt
The same Device ID + Activation Key record can become a paid license record.
```

When payment enforcement is enabled later, the app must react to:

```txt
allowed
state
freeLaunch
paymentRequired
message
```

Expected future unpaid response:

```json
{
  "allowed": false,
  "state": "expired",
  "freeLaunch": false,
  "paymentRequired": true,
  "message": "Please activate your license."
}
```

Expected future paid response:

```json
{
  "allowed": true,
  "state": "active",
  "freeLaunch": false,
  "paymentRequired": false,
  "message": "License active."
}
```

## 14. No-Content Boundary

During bootstrap and license check, the app must not send:

- Stream URLs.
- Playlist contents.
- Provider username/password.
- Channel package data.
- IPTV package data.
- EPG source data.
- Watch history.

EA0 device bootstrap is only for platform access records.

## 15. Acceptance Criteria

TV_Project implementation is aligned when:

- App calls `POST /devices/bootstrap` on first startup when no local credentials exist.
- App stores backend-generated `deviceId` and `activationKey` locally.
- App calls `POST /license/check` using `deviceId` and `activationKey`.
- App supports best-effort reinstall recovery with `platformDeviceHash`.
- App never hardcodes a shared activation key.
- App never sends media/source/provider data to bootstrap/license endpoints.
- Free launch access is not blocked by missing payment.
- Future paid licensing can work through the same Device ID + Activation Key record.

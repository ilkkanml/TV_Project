# Urgent Install-Only Device Registration Handoff

Status: Active Handoff
Source project: TV_Project_Platform
Target project: TV_Project
Mode: Urgent minimal app integration. No license, no activation key, no payment, no customer login.

## 1. Purpose

This handoff replaces the earlier full EA0 bootstrap requirement for the immediate release step.

Immediate goal:

```txt
When Nexora TV is installed/opened on Android TV or Fire TV,
register that device install into the platform database.
```

Nothing else is required for this urgent phase.

## 2. App Identity

Approved display name:

```txt
Nexora TV
```

Approved fixed APK filename:

```txt
nexoratv.apk
```

Fixed APK download URL:

```txt
https://www.thenightssecret.com/dl/nexoratv.apk
```

Recommended package name:

```txt
com.nexoratv.app
```

Critical update rules:

- Keep package name unchanged after public release.
- Keep signing key unchanged after public release.
- Increase versionCode on every update.
- Keep APK filename/path unchanged if Downloader code should remain unchanged.

## 3. Active Backend Endpoint

Use this endpoint:

```txt
POST https://www.thenightssecret.com/api/devices/install/index.php
```

This is a cPanel/PHP endpoint backed by GoDaddy MySQL.

## 4. Request Body

The app should send JSON:

```json
{
  "installId": "optional-existing-install-id",
  "platformDeviceHash": "stable-device-hash-if-available",
  "platform": "android_tv",
  "appVersion": "0.1.0"
}
```

For Fire TV, use:

```json
{
  "installId": "optional-existing-install-id",
  "platformDeviceHash": "stable-device-hash-if-available",
  "platform": "fire_tv",
  "appVersion": "0.1.0"
}
```

## 5. Response Body

Expected success response:

```json
{
  "ok": true,
  "data": {
    "installId": "NX-INST-....",
    "status": "seen",
    "platform": "android_tv",
    "appVersion": "0.1.0",
    "firstSeenAt": "...",
    "lastSeenAt": "...",
    "created": true
  },
  "error": null
}
```

If the same device/install is already recorded, expected response:

```json
{
  "ok": true,
  "data": {
    "installId": "NX-INST-....",
    "status": "seen",
    "platform": "android_tv",
    "appVersion": "0.1.0",
    "firstSeenAt": "...",
    "lastSeenAt": "...",
    "created": false
  },
  "error": null
}
```

## 6. App Startup Behavior

On app first launch:

```txt
1. Read local installId.
2. Create/read platformDeviceHash if possible.
3. Send POST /api/devices/install/index.php.
4. Save returned installId locally.
5. Continue normal app flow.
```

On later launches:

```txt
1. Send saved installId.
2. Send same platformDeviceHash if available.
3. Backend updates lastSeenAt instead of creating duplicate row.
```

## 7. Local Storage Requirement

The app must store:

```txt
installId
```

Recommended:

```txt
platformDeviceHash
```

Rules:

- Preserve installId during normal app updates.
- If app is uninstalled, local installId may be lost.
- If platformDeviceHash remains stable, backend can still recognize the device install.

## 8. Platform Device Hash Rule

`platformDeviceHash` should be:

- Stable for the same device/app install when possible.
- Privacy-safe.
- Not a raw MAC address.
- Not a provider/source/user credential.

If a stable hash is not available yet, the app may still call endpoint without it, but duplicate prevention becomes weaker.

## 9. What Not To Implement Yet

Do not implement for this urgent phase:

```txt
Device ID
Activation Key
License check
Payment required state
Customer login
Reseller flow
Playlist upload to backend
Provider credentials upload
Stream/source URL upload
Watch history tracking
```

## 10. Error Handling

If endpoint fails:

- Do not block the app completely for now.
- Retry later on next app start or after a quiet delay.
- Do not show technical database errors to user.
- Do not spam the endpoint in a tight loop.

Recommended retry behavior:

```txt
Try once on app start.
If failed, retry later with backoff.
```

## 11. Acceptance Criteria

TV_Project is aligned when:

- App display name is Nexora TV.
- APK filename is nexoratv.apk for release upload.
- App sends POST request to the provided endpoint on first startup.
- App stores returned installId locally.
- App sends installId again on later startup.
- Same test device does not create duplicate records.
- App does not send license/payment/provider/source data.
- App can continue running even if install registration temporarily fails.

## 12. Current Verified Backend Test

Manual test result:

```txt
Endpoint accepted test-device-001.
MySQL table created one DeviceInstallRecord.
Duplicate row was not created on repeat test.
A real installed app/device created a database record successfully.
APK download URL worked.
```

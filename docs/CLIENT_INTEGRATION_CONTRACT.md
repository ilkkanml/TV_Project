# CLIENT INTEGRATION CONTRACT

## Purpose

Defines the M10 draft integration contract between:

- `TV_Project`: Android TV / Fire TV first client
- `TV_Project_Platform`: Core Account / Device / License / Admin / Remote Config / App Version / Profile Transfer center

This document is documentation only. It does not approve app code, backend code, database work, payment enforcement, provider integration, or protected-system changes.

## Runtime Guardrails

- M10 remains ACTIVE.
- M10 is not PASSED.
- M10 is not LOCKED.
- M9 remains LOCKED.
- Protected systems remain preserved.
- Legal/compliance boundary remains mandatory.

## Android ↔ Platform Responsibility Table

| Area | Android Client `TV_Project` | Platform `TV_Project_Platform` | Boundary |
| --- | --- | --- | --- |
| Device identity | Generates/stores app install GUID and sends device metadata. | Assigns `platform_device_id` and stores device status. | Real hardware MAC address is not primary identity. |
| Activation session | Shows activation code/QR, starts request, polls status. | Creates activation session and returns pending/approved/failed state. | Draft only in M10. |
| License check | Requests access state when policy requires it. | Returns license/device/app decision. | Early launch remains free unless Director changes policy. |
| App version check | Sends app version/build and applies version result. | Defines minimum/supported versions and maintenance state. | App must not hardcode final release policy. |
| Remote config | Applies only client-safe approved flags/copy/cache values. | Hosts config values and flags. | No media payloads, secrets, or executable behavior. |
| Profile transfer | Consumes one-time transfer and stores profile locally. | Creates temporary transfer and consume result. | Backend is not default profile source of truth. |
| Playback | Plays legally authorized media through approved player stack. | Provides account/device/license/config only. | Legal media player only. |
| Local storage | Stores session/profile/config cache according to app policy. | Stores account/device/license/config state. | Cloud sync requires future approval. |
| Error handling | Shows TV-friendly recovery states. | Returns stable error codes and retry guidance. | Client must not override access/legal failures. |

## Endpoint Contract Draft

Suggested draft base path: `/api/v1`

Common client fields:

- `request_id`
- `platform`: `android_tv` / `fire_tv`
- `app_version`
- `build_number`
- `app_install_id`
- `platform_device_id` after activation
- `locale`
- `device_model`

| Flow | Method | Draft Endpoint | Auth | Owner |
| --- | --- | --- | --- | --- |
| Activation session create | POST | `/activation-sessions` | No user token required | Platform |
| Activation status poll | GET | `/activation-sessions/{activation_session_id}` | Activation scoped | Platform |
| License check | POST | `/license/check` | Session token when available | Platform |
| App version check | POST | `/app-version/check` | No user token required | Platform |
| Remote config fetch | GET | `/remote-config` | Optional/session-aware | Platform |
| Profile transfer create | POST | `/profile-transfers` | Platform user/admin session | Platform |
| Profile transfer consume | POST | `/profile-transfers/{transfer_code}/consume` | Device/session scoped | Platform |

## Request / Response Examples

Examples are field-level drafts. Final names may be changed before implementation.

### Activation Session Create

Request fields:

- `request_id`: `req_001`
- `platform`: `android_tv`
- `app_version`: `0.10.0`
- `build_number`: `10`
- `app_install_id`: `app-guid-123`
- `device_model`: `Google TV`
- `locale`: `en-US`

Response fields:

- `activation_session_id`: `act_abc123`
- `activation_code`: `482913`
- `qr_url`: platform activation URL
- `status`: `pending`
- `poll_after_seconds`: `5`
- `expires_in_seconds`: `900`

### Activation Status Poll

Request:

- GET `/api/v1/activation-sessions/act_abc123`

Pending response:

- `status`: `pending`
- `poll_after_seconds`: `5`
- `expires_in_seconds`: `840`

Approved response:

- `status`: `approved`
- `platform_device_id`: `dev_789`
- `session_token`: placeholder only
- `session_expires_at`: TBD by Platform
- `license_state`: `early_launch_free`
- `next_action`: `enter_app`

### License Check

Request fields:

- `request_id`: `req_002`
- `platform`: `android_tv`
- `app_version`: `0.10.0`
- `platform_device_id`: `dev_789`
- `app_install_id`: `app-guid-123`

Early launch response fields:

- `allowed`: `true`
- `license_state`: `early_launch_free`
- `enforcement_mode`: `inactive_until_final_release`
- `next_check_after_seconds`: `86400`

Future paid/restricted release response draft:

- `allowed`: `false`
- `license_state`: `expired`
- `error_code`: `LICENSE_EXPIRED`
- `next_action`: `show_license_required`

### App Version Check

Request fields:

- `platform`: `android_tv`
- `app_version`: `0.10.0`
- `build_number`: `10`
- `device_model`: `Google TV`

Supported response:

- `supported`: `true`
- `minimum_supported_version`: `0.10.0`
- `latest_version`: `0.10.0`
- `update_required`: `false`
- `maintenance_mode`: `false`

Unsupported response:

- `supported`: `false`
- `minimum_supported_version`: `0.11.0`
- `latest_version`: `0.11.2`
- `update_required`: `true`
- `error_code`: `UNSUPPORTED_APP_VERSION`

### Remote Config Fetch

Request:

- GET `/api/v1/remote-config?platform=android_tv&app_version=0.10.0&build_number=10`

Response fields:

- `config_version`: `m10-draft-001`
- `maintenance_mode`: `false`
- `early_launch_free`: `true`
- `license_enforcement_active`: `false`
- `feature_flags.profile_transfer_mvp`: `true`
- `feature_flags.force_update_block`: `false`
- `cache_ttl_seconds`: `3600`

### Profile Transfer Create

Request fields:

- `request_id`: `req_004`
- `target_platform`: `android_tv`
- `profile_label`: `Living Room TV`
- `profile_payload_type`: `user_authorized_profile`
- `expires_in_seconds`: `900`

Response fields:

- `transfer_id`: `ptr_123`
- `transfer_code`: `731246`
- `qr_url`: platform transfer URL
- `status`: `pending`
- `expires_in_seconds`: `900`

### Profile Transfer Consume

Request fields:

- `request_id`: `req_005`
- `platform`: `android_tv`
- `platform_device_id`: `dev_789`
- `app_install_id`: `app-guid-123`

Response fields:

- `transfer_id`: `ptr_123`
- `status`: `consumed`
- `profile_label`: `Living Room TV`
- `profile_payload_type`: `user_authorized_profile`
- `profile_payload.source_type`: `user_authorized_source`
- `store_locally`: `true`
- `cloud_sync_enabled`: `false`

## Error State Matrix

| State | Draft Error Code | Platform Response | Android Client Behavior | Early Launch Behavior |
| --- | --- | --- | --- | --- |
| Expired license | `LICENSE_EXPIRED` | `allowed:false` after enforcement is active | Show license required / renewal state | Must not block early free usage while `license_enforcement_active=false` |
| Inactive device | `DEVICE_INACTIVE` | Device exists but inactive | Show activation/device inactive state | May require reactivation only if Director approves blocking rule |
| Revoked device | `DEVICE_REVOKED` | Device explicitly revoked | Clear session and show blocked/re-activate path | Can block because revocation is security/admin action |
| Unsupported app version | `UNSUPPORTED_APP_VERSION` | `supported:false`, update required | Show update required screen | Can block if platform marks update as required |
| Maintenance mode | `MAINTENANCE_MODE` | `maintenance_mode:true` | Show maintenance screen and retry guidance | Can block temporarily for platform safety |
| Offline fallback | `OFFLINE_FALLBACK` | No response / cached config used | Use safe cached state if available; do not invent access | Early free cached access may continue until cache expiry |
| Profile transfer failed | `PROFILE_TRANSFER_FAILED` | Transfer missing/expired/invalid/consumed | Show retry/new-code message | Does not block app access |
| Invalid token | `INVALID_TOKEN` | HTTP 401 or equivalent | Clear session token and request fresh activation/session | Must not ignore security; free launch may still allow non-enforced entry if policy allows |

## Session / Token Behavior

Final token design requires Security/Privacy and Backend approval before implementation.

### Token Lifetime Placeholder

- Platform returns `session_expires_at` or equivalent TTL.
- Android must not hardcode final token lifetime.
- Recommended final lifetime is TBD.

### Refresh Behavior Placeholder

- Refresh flow is TBD.
- Platform may later expose a refresh endpoint or require fresh activation/session validation.
- Android treats refresh failure as recoverable and returns to activation/session state.

### Logout / Device Reset Behavior

- Logout clears local session token and account-bound runtime state.
- Device reset clears `platform_device_id` binding and forces a new activation session.
- App-generated install/device GUID handling follows `DEVICE_IDENTITY_POLICY.md`.
- Local user-authorized profile data is not uploaded or synced unless future explicit consent policy exists.

### Invalid Token Response

Draft response fields:

- `allowed`: `false`
- `error_code`: `INVALID_TOKEN`
- `message`: session expired or invalid
- `next_action`: `refresh_or_reactivate`

Android behavior:

- Stop using the invalid token.
- Clear only token/session state unless device reset is required.
- Do not ignore security state.
- Preserve legal/free launch behavior only through approved platform policy.

## Remote Config Schema Boundary

Remote config may contain:

- Maintenance mode state
- Early launch/free access flags
- License enforcement active/inactive flag
- Minimum/supported app version data
- Feature flags for already-approved features
- Client-safe copy text
- Cache TTL
- Non-sensitive endpoint metadata

Remote config must not contain:

- Media source lists
- Bundled channel/media packages
- Provider secrets
- User passwords
- Rights-protection circumvention material
- Remote executable code
- Unauthorized third-party extraction rules
- Payment/subscription enforcement override outside Director policy
- UI rewrite or protected system replacement instructions

## Profile Transfer MVP Flow

1. User opens Platform web/dashboard transfer flow.
2. User provides or selects a legally authorized profile/source.
3. Platform creates a temporary one-time transfer session.
4. Platform displays code/QR.
5. Android client enters or scans code.
6. Android consumes the transfer once.
7. Android stores the profile locally according to local profile storage policy.
8. Transfer expires or is marked consumed.
9. Backend does not become default profile source of truth.
10. Cloud sync remains out of scope unless Director approves a future milestone.

Failure handling:

- Expired code: show new-code required.
- Already consumed: show consumed/invalid message.
- Wrong device/account: show transfer mismatch.
- Network failure: allow retry without partial profile import.
- Invalid or illegal content source: reject and preserve legal boundary.

## Free Early Launch Behavior

- App remains free until final release level.
- Payment/subscription enforcement is inactive during early launch.
- License infrastructure may exist during early launch.
- License infrastructure must not block early free usage unless Director changes policy.
- Platform may still block for security/admin reasons such as revoked device, required update, or maintenance mode.
- Public language must not promise paid content access or bundled content.

## Legal Media Player Boundary

Nexora TV is a legal media player/client ecosystem.

It must not provide or enable:

- Content hosting
- Broadcasting
- Channel selling
- Bundled streams
- Rights-protection bypass or equivalent circumvention
- Unauthorized scraping
- Credential sharing

Additional boundary:

- No illegal restreaming.
- No claims that the app provides paid channels for free.
- Only mock data, permitted test media, public demo media, user-authorized legal sources, or licensed provider/API integrations may be used.

## M11 Readiness Notes

Before M11 implementation work begins, Backend / Security / Android Builder must finalize:

- Final endpoint naming
- Auth/session token design
- Error code naming
- Remote config schema versioning
- Profile payload encryption/redaction rules
- Early launch enforcement flag behavior
- Build/runtime evidence expectations

Documentation Memory may record this contract as hardened, but this does not mark M10 PASSED or LOCKED.

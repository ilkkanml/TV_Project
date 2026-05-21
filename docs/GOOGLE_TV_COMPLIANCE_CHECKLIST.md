# Nexora TV — Google TV / Android TV Compliance Checklist

Status: ACTIVE REVIEW
Updated: 2026-05-21

## Purpose

Track Nexora TV against Google TV / Android TV app quality expectations before internal alpha handoff and later Play Store readiness.

Source reference:

- Android Developers — TV app quality: https://developer.android.com/docs/quality-guidelines/tv-app-quality

## Review Status Legend

- `DONE`: implemented in repo
- `NEEDS BUILD EVIDENCE`: code exists but build not verified in this checkpoint
- `NEEDS DEVICE TEST`: requires Android TV / Fire TV / emulator runtime test
- `NOT IN SCOPE`: not applicable to current alpha
- `HOLD`: must not proceed until fixed

## Launcher / Manifest

### TV-LM / TV-ML — Leanback launcher

Status: `DONE / NEEDS BUILD EVIDENCE`

Repo state:

- `AndroidManifest.xml` includes `android.intent.action.MAIN`
- `AndroidManifest.xml` includes `android.intent.category.LEANBACK_LAUNCHER`

### TV-LB / TV-BN — Banner and icon

Status: `DONE / NEEDS BUILD EVIDENCE / NEEDS VISUAL REVIEW`

Repo state:

- `android:banner="@drawable/tv_banner"` added
- `app/src/main/res/drawable/tv_banner.xml` added
- `android:icon="@mipmap/ic_launcher"` present

Risk:

- Banner is vector-based and functional for build, but final Play listing quality should receive brand/art polish later.

### TV-MT — Touchscreen not required

Status: `DONE / NEEDS BUILD EVIDENCE`

Repo state:

- `android.hardware.touchscreen` declared as `required="false"`
- `android.software.leanback` declared as `required="true"`

## Layout / Visual Presentation

### TV-LO — Landscape orientation

Status: `DONE / NEEDS DEVICE TEST`

Repo state:

- Main activity uses `android:screenOrientation="landscape"`

### TV-TR — Full-screen non-transparent app

Status: `DONE / NEEDS DEVICE TEST`

Repo state:

- MainActivity uses fullscreen immersive flags
- Main app surfaces use cinematic non-transparent backgrounds

### TV-OV — Overscan / edge safety

Status: `NEEDS DEVICE TEST`

Repo state:

- Main TV screens use visible padding.
- Needs physical TV/emulator overscan review.

## Navigation

### TV-DP — Five-way D-pad navigation

Status: `PARTIAL / NEEDS DEVICE TEST`

Repo state:

- Primary buttons are focusable Compose buttons.
- Home sidebar uses focus requesters.
- Player remote keys are handled at `PlayerView` level.

Remaining test:

- Confirm no focus trap on Language, Activation, Profiles, Home, Detail, Player, Media Source Setup.

### TV-DM — Do not depend on Menu button

Status: `DONE / NEEDS DEVICE TEST`

Repo state:

- Settings is accessible from visible on-screen Player Settings button.
- Menu/Settings key is only a shortcut.

### TV-DB — Back behavior

Status: `PARTIAL / NEEDS DEVICE TEST`

Repo state:

- Back closes Player settings panel first.
- Home back shows return-to-profiles hint.
- Other screens use back navigation buttons.

Remaining test:

- Verify Android TV system Back reaches expected previous screen and eventually launcher.

## Media Playback

### TV-PP — Play/Pause key toggles playback

Status: `DONE / NEEDS DEVICE TEST`

Repo state:

- `KEYCODE_MEDIA_PLAY_PAUSE`
- `KEYCODE_MEDIA_PLAY`
- `KEYCODE_MEDIA_PAUSE`
- `KEYCODE_DPAD_CENTER`
- `KEYCODE_ENTER`
- `KEYCODE_SPACE`

all call `togglePlayPause(player)` inside `PlayerScreen.kt`.

### TV-PC — D-pad center pauses/resumes, left/right seek

Status: `DONE / NEEDS DEVICE TEST`

Repo state:

- D-pad center toggles play/pause.
- D-pad left and media rewind call `player.seekBack()`.
- D-pad right and media fast-forward call `player.seekForward()`.

### TV-BU / TV-BY — Ambient Mode behavior

Status: `DONE / NEEDS DEVICE TEST`

Repo state:

- `PlayerView.keepScreenOn = true` during active player creation.
- `PlayerView.keepScreenOn = isPlaying` during update.

Remaining test:

- Confirm paused playback allows screen saver / ambient mode.

### Now Playing card

Status: `NOT IN SCOPE`

Reason:

- App should pause video when backgrounded; background audio continuation is not currently scoped.

## Performance / Memory

### TV-PS — minSdk support

Status: `DONE`

Repo state:

- `minSdk = 24`, which is below Android TV requirement threshold of SDK 31 or lower.

### TV-ME — low RAM memory behavior

Status: `NEEDS DEVICE TEST`

Risk areas:

- Cinematic backdrop canvas drawing
- Home loaded catalog large lists
- Poster rows
- Player overlay

Current mitigation:

- Catalog lists are capped in session loader.
- LazyColumn is used for loaded catalog views.

## Google Play Readiness

### AAB requirement

Status: `NEEDS RELEASE PIPELINE`

Current scope:

- Internal alpha APK handoff only.
- Play Store release is not approved yet.

### Store screenshots / listing

Status: `NOT IN SCOPE`

Current scope:

- No Play Store release.

### 64-bit and 16 KB page size

Status: `NEEDS FUTURE RELEASE REVIEW`

Current scope:

- No native NDK code currently identified in Android app.
- Final Play readiness must confirm build artifacts.

## Legal / Compliance Boundary

Status: `ACTIVE / PRESERVED`

Nexora TV remains a legal media-player client.

Not allowed:

- Content hosting
- Channel selling
- Stream relay/proxy/transcoding
- Unauthorized scraping
- DRM bypass
- Backend-owned stream catalog
- Credential sharing facilitation

Allowed:

- User-owned or legally authorized media source usage
- Mock data
- Permissioned test/demo streams

## Current Director Conclusion

The repo now has the first layer of Android TV compliance work in place.

Do not mark Google TV compliance as passed until:

1. Gradle build passes.
2. App runs on Android TV emulator or real TV device.
3. D-pad navigation is tested across all screens.
4. Player remote keys are tested during actual playback.
5. Overscan/readability is visually reviewed.
6. QA records evidence.

Current status:

`COMPLIANCE IMPLEMENTATION STARTED — NOT QA PASSED`

# Nexora TV — M3 Manual Runtime Test Checklist

## Branch

`feature/m3-premium-ui-polish`

## Build

- [ ] GitHub Actions Build Debug APK green
- [ ] New APK downloaded
- [ ] Old app fully uninstalled
- [ ] New `app-debug.apk` installed

## Device

- [ ] BlueStacks
- [ ] Android TV Emulator
- [ ] Real Android TV / Fire TV

## Startup

- [ ] Splash screen opens
- [ ] Nexora TV text is visible
- [ ] Login screen appears
- [ ] Username field works
- [ ] Password field works
- [ ] Connect button is visible

## Login → Home

- [ ] `test / test` entered and Connect pressed
- [ ] App does not close
- [ ] HomeScreen opens
- [ ] Dark premium background is visible
- [ ] NEXORA menu is visible

## Navigation

- [ ] Home receives focus
- [ ] Live TV receives focus
- [ ] Movies receives focus
- [ ] Series receives focus
- [ ] Settings receives focus
- [ ] Content area changes when menu selection changes
- [ ] Focus state is visible and readable

## Poster Rows

- [ ] Poster cards are visible
- [ ] Left/right keys move between posters
- [ ] Focused poster becomes visually clear
- [ ] Row scrolls to keep partially visible posters on-screen
- [ ] No row overflow or off-screen clipping issue

## Content Logic

- [ ] Home shows general featured content
- [ ] Live TV shows live category content
- [ ] Movies shows movie / continue watching content
- [ ] Series shows series / continue watching content
- [ ] Continue Watching is not a main menu item; it belongs inside Movies / Series content

## Dynamic Background

- [ ] Background color changes when focus changes
- [ ] Background transition does not crash
- [ ] Colors do not hurt TV readability

## Player

- [ ] Open Player button receives focus
- [ ] Pressing Open Player does not close the app
- [ ] PlayerScreen opens
- [ ] Back behavior checked

## Protected Systems

- [ ] Playback Core unchanged
- [ ] Auth / device login unchanged
- [ ] Backend API unchanged
- [ ] No illegal stream / DRM bypass logic

## Result

- [ ] PASSED
- [ ] BLOCKED

## Issues

- none / list

## Current Status

M3 ACTIVE  
QA repo/code review PASSED  
Manual runtime test PENDING  
LOCKED: No

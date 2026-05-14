# M2 Playback Freeze Lock

## Status

M2 Playback Core is locked as the playback baseline for Nexora TV.

## Locked Playback Direction

- Live TV opens instantly in fullscreen.
- Live TV channel switching prioritizes speed.
- Live TV transitions use ultra fast hard cut.
- Movies and Series use cinematic detail pages before playback.
- VOD playback uses smooth fade transitions.
- Player overlay appears on user input and fades after 5 seconds.
- Player UI must remain minimal and cinematic.
- Playback stability is more important than animation complexity.

## Playback Core Priorities

- Fast stream opening
- Stable fullscreen state
- Remote-friendly overlay behavior
- Stream failure recovery
- Smooth return to browsing
- Watch history foundation
- Continue Watching foundation

## Player UX Rules

- No cluttered controls
- No mobile-style player UI
- No aggressive overlay animations
- No slow live TV channel switching
- No heavy effects that hurt playback performance

## Technical Direction

Primary player layer:

- Android TV runtime shell
- Media3 ExoPlayer direction
- Playback state coordinator
- Fullscreen player container
- Stream URL generator
- Player overlay lifecycle

## Next Milestone

M3 Premium UI Expansion.

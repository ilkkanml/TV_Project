# Nexora TV — M2 Stabilization Commit

## Purpose

Dev branch’teki tüm core pipeline, Player/Home UI, Live TV/VOD, token tabanlı auth/session ve protected systems stabilizasyonu için milestone commit.

## Commit Notes

- Player ve Home UI tamamen entegre
- Live TV hızlı kanal geçişi ve VOD playback pipeline stabilize edildi
- Token tabanlı auth/session pipeline doğrulandı ve entegre edildi
- Home screen hero, poster card, focus/glow, overlay ve blurred background sistemleri stabil ve test edilmeye hazır
- Protected systems, session restore ve failover logic finalize edildi
- Gereksiz ara prosedürler atlandı

## Branch
`dev` — milestone commit olarak işlenecek

## Next Steps
- Merge dev → main milestone tag ile
- Canlı test veya yeni feature entegrasyonlarına hazır
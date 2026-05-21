# Nexora TV — Next Director Handoff

Yeni Director sohbeti bu dosyayı okuyarak devam etmeli.

## Aktif durum

- App adı: Nexora TV
- applicationId: com.nexoratv.app
- namespace: com.nexora.tv
- APK adı: nexoratv.apk
- APK URL: https://www.thenightssecret.com/dl/nexoratv.apk
- Install endpoint: https://www.thenightssecret.com/api/devices/install/index.php
- Version: versionCode 4 / versionName 0.1.3

## Sabit kurallar

- VersionCode / versionName otomatik artırılmayacak.
- Sürüm sadece kullanıcı açıkça isterse artırılacak.
- Kullanıcının medya erişim bilgileri cihazda kalacak.
- Backend tarafına sadece install kaydı gönderilecek.
- MAC primary identity olarak kullanılmayacak.
- Şimdilik lisans, ödeme, activation key ve müşteri login sistemi yok.
- Sadece yasal erişim hakkı olan kaynaklarla çalışılacak.
- Yapılmayan iş yapıldı denmeyecek.
- QA olmadan LOCKED/PASSED denmeyecek.

## Güncel sistemler

### Release / Install

- ReleaseConstants.kt merkezi release sabitlerini tutuyor.
- DeviceInstallRegistrar uygulama açılışında çalışıyor.
- installId local storage’da tutuluyor.
- BuildConfig.VERSION_NAME appVersion olarak gönderiliyor.
- buildConfig = true aktif.
- UpdatePolicy.kt var ama pasif:
  - auto download false
  - force update false
  - update popup false

### Manifest

- AndroidManifest label: Nexora TV
- Launcher: LAUNCHER + LEANBACK_LAUNCHER
- applicationId com.nexoratv.app olarak sabit.

### Dil ekranı

- LanguageSelectionScreen.kt güncellendi.
- Türkçe butonu ilk açılışta default focus alıyor.
- Butonlar küçük ve TV standardına uygun.
- Dil seçimi local storage’a kaydediliyor.

### Profil ekranı

- UserProfilesScreen.kt default focus iyileştirildi.
- Profil yoksa focus Kullanıcı Ekle butonunda.
- Profil varsa focus seçili profilde.
- Profil detay panelinde hesap geçerliliği, uygulama geçerliliği, bağlantı bilgileri ve MAC açıklaması var.

### Kaynak giriş ekranı

- MediaSourceSetupScreen.kt restore edildi.
- Keyboard import hatası düzeltildi.
- Doğru importlar:
  - androidx.compose.foundation.text.KeyboardActions
  - androidx.compose.foundation.text.KeyboardOptions
- Provider API / M3U / Local / Single seçenekleri profil adı alanının üstünde.
- URL, kullanıcı adı ve şifre alanlarında whitespace engelleniyor.
- Input alanları focus alabiliyor.
- Edit durumu readOnly = !isActive ile yönetiliyor.
- Enter / Next ile sonraki input’a geçiş mantığı mevcut.

### Home / Catalog

- Home sidebar + content catalog olarak çalışıyor.
- Home altında loaded movies + loaded series karışık son eklenenler gösteriliyor.
- Movies / Series poster row kategori bazlı.
- Live liste formatında.
- Back ilk basışta uyarı, ikinci basışta Profiles ekranına döner.

### Detail / Series / Player

- ContentDetailScreen.kt içinde Provider detail ekranı var.
- Movie detaydan Player açılıyor.
- Series detayda sezon ve bölüm seçimi var.
- Episode listesi LazyColumn oldu.
- Eski 80 bölüm sınırı kaldırıldı.
- Bölüm seçilince Player açılıyor.
- Player’da active source, stream status, Play/Pause, Settings, Back, Home var.
- Player chip doğru gösteriyor: LIVE / MOVIE / EPISODE / SERIES.

## Son kritik commitler

- a18a4c540a6745f370f89999d8a218909f07f8f0 — Language screen Turkish default focus
- 052d7821572dd737a49f36f94b93963e176e47f2 — Profile screen default focus
- 562d71bb37691ee38b7060f395b008ac9d566633 — Series episode LazyColumn
- a3d8147aee185b21801cee0eb372214651258693 — Player media chip label
- bb76e6d59b5b0a373e4c70081172fd2da528ad61 — Restore media source setup components
- bbe681309a1836ce7ed1bc945ba5ca63c4e27108 — ReadOnly input state for setup fields
- 0bc88e8a70fb74334fbfe92142e16b3179b29afe — Fix KeyboardOptions / KeyboardActions import

## Şişmeye başlayan dosyalar

Refactor önerisi ama hemen yapılmak zorunda değil:

- MediaSourceSetupScreen.kt
- ContentDetailScreen.kt
- HomeScreen.kt

Önerilen parçalama:

- MediaSourceSetupComponents.kt
- MediaSourceConnectActions.kt
- SeriesEpisodePanel.kt
- HomeCatalogComponents.kt
- HomeSidebar.kt

## Yeni Director ilk görev

1. Repo state GitHub’dan okunacak.
2. Önce MediaSourceSetupScreen.kt build hatası kontrol edilecek.
3. Android Studio tarafında Sync + Rebuild istenecek.
4. Kullanıcı ekran görüntüsü gönderirse sadece görünen hata düzeltilecek.
5. Version’a dokunulmayacak.

## Kullanıcı test sırası

1. git pull origin main
2. Android Studio Sync
3. Rebuild Project
4. App launch
5. Dil ekranı Türkçe default focus testi
6. Profil oluşturma input/enter testi
7. Profil kayıt sonrası app kapat/aç testi
8. Home son eklenenler testi
9. Series detay / sezon / bölüm testi
10. Player chip / settings / back / home testi

## Yeni Director prompt

```txt
Nexora TV / IPTV App Studio — Director Continuation

GitHub repo: ilkkanml/TV_Project

Önce NEXT_DIRECTOR_HANDOFF.md dosyasını oku. Repo state’i GitHub’dan kontrol et. Varsayım yapma.

Kurallar:
- Türkçe cevap ver.
- Kısa, net, uygulanabilir yaz.
- Yapmadığın şeyi yaptım deme.
- VersionCode / versionName sadece kullanıcı açıkça isterse artırılacak.
- Kullanıcının medya erişim bilgileri cihazda kalacak.
- MAC primary identity olarak kullanılmayacak.
- Şimdilik lisans, ödeme, activation key ve customer login yok.
- QA olmadan LOCKED/PASSED deme.

Aktif durum:
- App adı: Nexora TV
- applicationId: com.nexoratv.app
- namespace: com.nexora.tv
- versionCode: 4
- versionName: 0.1.3
- install endpoint aktif
- update policy pasif

İlk iş:
Android Studio build hatası varsa sadece hatayı düzelt. Yeni feature ekleme. Önce MediaSourceSetupScreen.kt KeyboardOptions / KeyboardActions importlarının doğru olduğunu kontrol et.
```

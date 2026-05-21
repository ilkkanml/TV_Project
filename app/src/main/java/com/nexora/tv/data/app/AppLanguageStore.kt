package com.nexora.tv.data.app

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppLanguageStore {
    enum class Language { TR, EN }

    private const val PREFS = "nexora_app_prefs"
    private const val KEY_LANGUAGE = "language"

    var language by mutableStateOf(Language.EN)
        private set

    var initialized by mutableStateOf(false)
        private set

    var hasChosenLanguage by mutableStateOf(false)
        private set

    fun init(context: Context) {
        if (initialized) return
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val saved = prefs.getString(KEY_LANGUAGE, null)
        hasChosenLanguage = saved != null
        language = if (saved == "TR") Language.TR else Language.EN
        initialized = true
    }

    fun setLanguage(context: Context, value: Language) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANGUAGE, value.name)
            .apply()
        language = value
        hasChosenLanguage = true
        initialized = true
    }

    val isTurkish: Boolean
        get() = language == Language.TR

    fun t(en: String, tr: String): String = if (isTurkish) tr else en

    fun ui(text: String): String {
        if (!isTurkish) return text
        return core[text] ?: extra[text] ?: text
    }

    private val core = mapOf(
        "Home" to "Ana Sayfa",
        "Movies" to "Filmler",
        "Series" to "Diziler",
        "Live" to "Canlı",
        "Live TV" to "Canlı TV",
        "Search" to "Arama",
        "Settings" to "Ayarlar",
        "Movie" to "Film",
        "Setting" to "Ayar",
        "Back" to "Geri",
        "Close" to "Kapat",
        "Clear" to "Temizle",
        "Cancel" to "İptal",
        "Connect" to "Bağlan",
        "Loading" to "Yükleniyor",
        "Open" to "Aç",
        "More Info" to "Detaylar",
        "Play Now" to "Şimdi Oynat",
        "PLAY" to "OYNAT",
        "NOT PLAYABLE" to "OYNATILAMAZ",
        "Open preview" to "Önizlemeyi aç",
        "Category" to "Kategori",
        "Type" to "Tür",
        "Badge" to "Etiket",
        "All" to "Tümü",
        "Filter" to "Filtre",
        "Quality" to "Kalite",
        "Audio" to "Ses",
        "Subtitles" to "Altyazılar",
        "Auto" to "Otomatik",
        "Off" to "Kapalı",
        "Runtime" to "Süre",
        "TV ready" to "TV uyumlu",
        "Licensed" to "Lisanslı",
        "Now Playing" to "Şimdi Oynatılıyor",
        "Video Settings" to "Video Ayarları",
        "Available options depend on the stream." to "Seçenekler yayına göre değişir.",
        "No manual quality option." to "Manuel kalite seçeneği yok.",
        "No language option." to "Dil seçeneği yok.",
        "No subtitle option." to "Altyazı seçeneği yok.",
        "No channel selected" to "Kanal seçilmedi",
        "Go back and select a channel." to "Geri dönüp bir kanal seç.",
        "Go back?" to "Geri dönülsün mü?",
        "Press Back again to return to user profiles." to "Kullanıcı profillerine dönmek için Geri tuşuna tekrar bas.",
        "Content not found" to "İçerik bulunamadı",
        "Video" to "Video",
        "Subtitle" to "Altyazı",
        "Track" to "Parça",
        "User Profiles" to "Kullanıcı Profilleri",
        "ADD USER" to "KULLANICI EKLE",
        "Edit" to "Düzenle",
        "Open Home" to "Ana Sayfayı Aç",
        "No users yet" to "Henüz kullanıcı yok",
        "Source" to "Kaynak",
        "Server" to "Sunucu",
        "User name" to "Kullanıcı adı",
        "Password" to "Şifre",
        "Selected profile" to "Seçili profil",
        "Create Profile" to "Profil Oluştur",
        "Profile name" to "Profil adı",
        "Server URL" to "Sunucu URL",
        "Stream URL" to "Yayın URL",
        "Choose file from device" to "Cihazdan dosya seç",
        "Paste list data" to "Liste verisini yapıştır",
        "Play Stream" to "Yayını Aç",
        "User Profile" to "Kullanıcı Profili",
        "Security & Early Access" to "Güvenlik ve Erken Erişim",
        "Free during early access" to "Erken erişimde ücretsiz",
        "Privacy-first direction" to "Gizlilik öncelikli yaklaşım",
        "No MAC-based identity" to "MAC tabanlı kimlik yok",
        "Legal use only" to "Sadece yasal kullanım"
    )

    private val extra = mapOf(
        "Account" to "Hesap",
        "Language" to "Dil",
        "Open Details" to "Detayları Aç",
        "CATALOG" to "KATALOG",
        "channels" to "kanal",
        "movies" to "film",
        "series" to "dizi",
        "Continue Watching" to "İzlemeye Devam Et",
        "Featured Library" to "Öne Çıkan Kütüphane",
        "Trending Tonight" to "Bu Gece Öne Çıkanlar",
        "Available app controls" to "Kullanılabilir uygulama kontrolleri",
        "Profile & device access" to "Profil ve cihaz erişimi",
        "Turkish / English" to "Türkçe / İngilizce",
        "Resume your latest mock items" to "Son yerel örnek içeriklere devam et",
        "Movies, series and live placeholders" to "Film, dizi ve canlı yayın yer tutucuları",
        "High-energy premium row" to "Premium öne çıkan satır",
        "Cinematic VOD shell" to "Sinematik VOD alanı",
        "Episode-aware series shell" to "Bölüm odaklı dizi alanı",
        "Mock channel groups only" to "Yalnızca yerel kanal grup örnekleri"
    )
}

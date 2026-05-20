package com.nexora.tv.data.content

enum class NexoraContentSection {
    Home,
    Movies,
    Series,
    Live,
    Search,
    Settings
}

enum class NexoraContentType(val label: String) {
    Live("Live TV"),
    Movie("Movie"),
    Series("Series"),
    Setting("Setting")
}

data class NexoraContentItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val description: String,
    val type: NexoraContentType,
    val badge: String,
    val accentColor: Long,
    val isPlayable: Boolean = false
)

data class NexoraContentRow(
    val title: String,
    val subtitle: String,
    val items: List<NexoraContentItem>
)

object MockContentLibrary {
    private val liveNews = NexoraContentItem("live-news", "World News HD", "24/7 newsroom • Live shell", "News", "Mock live TV item for navigation testing. No real channel URL is included.", NexoraContentType.Live, "LIVE", 0xFF7C3AED)
    private val liveSports = NexoraContentItem("live-sports", "Arena Sports", "Game night • Stadium shell", "Sports", "Mock sports category item. Future legal provider integration can map here safely.", NexoraContentType.Live, "LIVE", 0xFF1FB6FF)
    private val liveCinema = NexoraContentItem("live-cinema", "Cinema Linear", "Prime time lineup", "Cinema", "Mock linear TV item used only for local UI and route foundation.", NexoraContentType.Live, "LIVE", 0xFFEC4899)
    private val liveDocumentary = NexoraContentItem("live-documentary", "Documentary Plus", "Knowledge channel group", "Documentary", "Mock documentary channel group for category row readability.", NexoraContentType.Live, "LIVE", 0xFF39FF88)

    private val movieOrbit = NexoraContentItem("movie-orbit-fall", "Orbit Fall", "Cinematic hero title", "Sci-Fi", "A cinematic mock movie used for detail-screen and selected-player route testing.", NexoraContentType.Movie, "MOVIE", 0xFF7C3AED)
    private val movieGlass = NexoraContentItem("movie-glass-tower", "Glass Tower", "Featured thriller shell", "Thriller", "Mock VOD item. Metadata is local and safe; no provider/API data is used.", NexoraContentType.Movie, "MOVIE", 0xFF4CC9FF)
    private val movieCoast = NexoraContentItem("movie-silent-coast", "Silent Coast", "Watch later mood", "Drama", "Mock watch-later movie card for Home and Movies rows.", NexoraContentType.Movie, "MOVIE", 0xFF14B8A6)
    private val movieEcho = NexoraContentItem("movie-neon-echo", "Neon Echo", "Late-night discovery", "Action", "Mock action movie card created to make Home and Movies rows feel fuller and more premium.", NexoraContentType.Movie, "MOVIE", 0xFFF97316)

    private val seriesMidnight = NexoraContentItem("series-midnight-grid", "Midnight Grid", "S01 E04 • 42 min left", "Series", "Mock series item for detail routing and episode-aware row copy.", NexoraContentType.Series, "SERIES", 0xFF7C3AED)
    private val seriesArchive = NexoraContentItem("series-deep-archive", "Deep Archive", "New season placeholder", "Series", "Mock series library card. Future episode catalog can attach here.", NexoraContentType.Series, "SERIES", 0xFF1FB6FF)
    private val seriesSignal = NexoraContentItem("series-signal-room", "Signal Room", "Episode row shell", "Series", "Mock series row item for remote-readable navigation testing.", NexoraContentType.Series, "SERIES", 0xFFEC4899)

    private val searchPlaceholder = NexoraContentItem("search-placeholder", "Search Library", "Search shell placeholder", "Search", "Local search placeholder only. No provider search, scraping, or backend query is included.", NexoraContentType.Setting, "LOCAL", 0xFF7C3AED)
    private val settingAccount = NexoraContentItem("setting-account", "Account", "Profile & device access", "Settings", "Open user profiles and media source setup.", NexoraContentType.Setting, "LOCAL", 0xFF7C3AED)
    private val settingLanguage = NexoraContentItem("setting-language", "Language", "Turkish / English", "Settings", "Change the application language.", NexoraContentType.Setting, "LOCAL", 0xFF39FF88)

    private val allItems = listOf(
        liveNews, liveSports, liveCinema, liveDocumentary,
        movieOrbit, movieGlass, movieCoast, movieEcho,
        seriesMidnight, seriesArchive, seriesSignal,
        searchPlaceholder, settingAccount, settingLanguage
    )

    fun rowsFor(section: NexoraContentSection): List<NexoraContentRow> = when (section) {
        NexoraContentSection.Home -> listOf(
            NexoraContentRow("Continue Watching", "Resume your latest mock items", listOf(seriesMidnight, movieCoast, liveNews, movieEcho)),
            NexoraContentRow("Featured Library", "Movies, series and live placeholders", listOf(movieOrbit, liveCinema, seriesArchive, movieGlass)),
            NexoraContentRow("Trending Tonight", "High-energy premium row", listOf(liveSports, movieEcho, seriesSignal, liveDocumentary))
        )
        NexoraContentSection.Movies -> listOf(NexoraContentRow("Movies", "Cinematic VOD shell", listOf(movieOrbit, movieGlass, movieCoast, movieEcho)))
        NexoraContentSection.Series -> listOf(NexoraContentRow("Series", "Episode-aware series shell", listOf(seriesMidnight, seriesArchive, seriesSignal)))
        NexoraContentSection.Live -> listOf(NexoraContentRow("Live", "Mock channel groups only", listOf(liveNews, liveSports, liveCinema, liveDocumentary)))
        NexoraContentSection.Search -> listOf(NexoraContentRow("Search", "Search is not active yet", listOf(searchPlaceholder)))
        NexoraContentSection.Settings -> listOf(NexoraContentRow("Settings", "Available app controls", listOf(settingAccount, settingLanguage)))
    }

    fun findContent(contentId: String?): NexoraContentItem? {
        if (contentId.isNullOrBlank()) return null
        return allItems.firstOrNull { it.id == contentId }
    }

    fun firstItemFor(section: NexoraContentSection): NexoraContentItem? {
        return rowsFor(section).firstOrNull()?.items?.firstOrNull()
    }
}

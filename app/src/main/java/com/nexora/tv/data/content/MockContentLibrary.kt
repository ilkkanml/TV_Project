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
    val isPlayable: Boolean = true
)

data class NexoraContentRow(
    val title: String,
    val subtitle: String,
    val items: List<NexoraContentItem>
)

object MockContentLibrary {

    private val liveNews = NexoraContentItem(
        id = "live-news",
        title = "World News HD",
        subtitle = "Live category shell",
        category = "News",
        description = "Mock live TV item for navigation testing. No real channel URL is included.",
        type = NexoraContentType.Live,
        badge = "LIVE",
        accentColor = 0xFF7C3AED
    )

    private val liveSports = NexoraContentItem(
        id = "live-sports",
        title = "Arena Sports",
        subtitle = "Licensed source placeholder",
        category = "Sports",
        description = "Mock sports category item. Future legal provider integration can map here safely.",
        type = NexoraContentType.Live,
        badge = "LIVE",
        accentColor = 0xFF7C3AED
    )

    private val liveCinema = NexoraContentItem(
        id = "live-cinema",
        title = "Cinema Linear",
        subtitle = "Linear TV placeholder",
        category = "Cinema",
        description = "Mock linear TV item used only for local UI and route foundation.",
        type = NexoraContentType.Live,
        badge = "LIVE",
        accentColor = 0xFF7C3AED
    )

    private val liveDocumentary = NexoraContentItem(
        id = "live-documentary",
        title = "Documentary Plus",
        subtitle = "Channel group shell",
        category = "Documentary",
        description = "Mock documentary channel group for category row readability.",
        type = NexoraContentType.Live,
        badge = "LIVE",
        accentColor = 0xFF7C3AED
    )

    private val movieOrbit = NexoraContentItem(
        id = "movie-orbit-fall",
        title = "Orbit Fall",
        subtitle = "Movie detail foundation",
        category = "Sci-Fi",
        description = "A cinematic mock movie used for detail-screen and selected-player route testing.",
        type = NexoraContentType.Movie,
        badge = "MOVIE",
        accentColor = 0xFF7C3AED
    )

    private val movieGlass = NexoraContentItem(
        id = "movie-glass-tower",
        title = "Glass Tower",
        subtitle = "Featured VOD shell",
        category = "Thriller",
        description = "Mock VOD item. Metadata is local and safe; no provider/API data is used.",
        type = NexoraContentType.Movie,
        badge = "MOVIE",
        accentColor = 0xFF7C3AED
    )

    private val movieCoast = NexoraContentItem(
        id = "movie-silent-coast",
        title = "Silent Coast",
        subtitle = "Watch later placeholder",
        category = "Drama",
        description = "Mock watch-later movie card for Home and Movies rows.",
        type = NexoraContentType.Movie,
        badge = "MOVIE",
        accentColor = 0xFF7C3AED
    )

    private val seriesMidnight = NexoraContentItem(
        id = "series-midnight-grid",
        title = "Midnight Grid",
        subtitle = "S01 E04 • 42 min left",
        category = "Series",
        description = "Mock series item for detail routing and episode-aware row copy.",
        type = NexoraContentType.Series,
        badge = "SERIES",
        accentColor = 0xFF7C3AED
    )

    private val seriesArchive = NexoraContentItem(
        id = "series-deep-archive",
        title = "Deep Archive",
        subtitle = "New season placeholder",
        category = "Series",
        description = "Mock series library card. Future episode catalog can attach here.",
        type = NexoraContentType.Series,
        badge = "SERIES",
        accentColor = 0xFF7C3AED
    )

    private val seriesSignal = NexoraContentItem(
        id = "series-signal-room",
        title = "Signal Room",
        subtitle = "Episode row shell",
        category = "Series",
        description = "Mock series row item for remote-readable navigation testing.",
        type = NexoraContentType.Series,
        badge = "SERIES",
        accentColor = 0xFF7C3AED
    )

    private val searchPlaceholder = NexoraContentItem(
        id = "search-placeholder",
        title = "Search Library",
        subtitle = "Search shell placeholder",
        category = "Search",
        description = "Local search placeholder only. No provider search, scraping, or backend query is included.",
        type = NexoraContentType.Setting,
        badge = "LOCAL",
        accentColor = 0xFF7C3AED,
        isPlayable = false
    )

    private val settingAccount = NexoraContentItem(
        id = "setting-account",
        title = "Account",
        subtitle = "Device access shell",
        category = "Settings",
        description = "Local settings placeholder. Production auth and billing are not changed here.",
        type = NexoraContentType.Setting,
        badge = "LOCAL",
        accentColor = 0xFF7C3AED,
        isPlayable = false
    )

    private val settingPlayback = NexoraContentItem(
        id = "setting-playback",
        title = "Playback",
        subtitle = "Preference placeholder",
        category = "Settings",
        description = "Playback settings placeholder only. No ExoPlayer or Media3 rewrite is included.",
        type = NexoraContentType.Setting,
        badge = "LOCAL",
        accentColor = 0xFF7C3AED,
        isPlayable = false
    )

    private val settingSupport = NexoraContentItem(
        id = "setting-support",
        title = "Support",
        subtitle = "Help placeholder",
        category = "Settings",
        description = "Support placeholder for future account-safe flows.",
        type = NexoraContentType.Setting,
        badge = "LOCAL",
        accentColor = 0xFF7C3AED,
        isPlayable = false
    )

    private val allItems = listOf(
        liveNews,
        liveSports,
        liveCinema,
        liveDocumentary,
        movieOrbit,
        movieGlass,
        movieCoast,
        seriesMidnight,
        seriesArchive,
        seriesSignal,
        searchPlaceholder,
        settingAccount,
        settingPlayback,
        settingSupport
    )

    fun rowsFor(section: NexoraContentSection): List<NexoraContentRow> = when (section) {
        NexoraContentSection.Home -> listOf(
            NexoraContentRow(
                title = "Continue Watching",
                subtitle = "Resume your latest mock items",
                items = listOf(seriesMidnight, movieCoast, liveNews)
            ),
            NexoraContentRow(
                title = "Featured Library",
                subtitle = "Movies, series and live placeholders",
                items = listOf(movieOrbit, liveCinema, seriesArchive, movieGlass)
            )
        )

        NexoraContentSection.Movies -> listOf(
            NexoraContentRow(
                title = "Movies",
                subtitle = "Cinematic VOD shell",
                items = listOf(movieOrbit, movieGlass, movieCoast)
            )
        )

        NexoraContentSection.Series -> listOf(
            NexoraContentRow(
                title = "Series",
                subtitle = "Episode-aware series shell",
                items = listOf(seriesMidnight, seriesArchive, seriesSignal)
            )
        )

        NexoraContentSection.Live -> listOf(
            NexoraContentRow(
                title = "Live",
                subtitle = "Mock channel groups only",
                items = listOf(liveNews, liveSports, liveCinema, liveDocumentary)
            )
        )

        NexoraContentSection.Search -> listOf(
            NexoraContentRow(
                title = "Search",
                subtitle = "Local placeholder, no provider query",
                items = listOf(searchPlaceholder, movieOrbit, seriesMidnight, liveCinema)
            )
        )

        NexoraContentSection.Settings -> listOf(
            NexoraContentRow(
                title = "Settings",
                subtitle = "Safe local placeholders",
                items = listOf(settingAccount, settingPlayback, settingSupport)
            )
        )
    }

    fun findContent(contentId: String?): NexoraContentItem? {
        if (contentId.isNullOrBlank()) return null
        return allItems.firstOrNull { it.id == contentId }
    }

    fun firstItemFor(section: NexoraContentSection): NexoraContentItem? {
        return rowsFor(section).firstOrNull()?.items?.firstOrNull()
    }
}

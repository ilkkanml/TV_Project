package com.nexora.tv.data.content

enum class NexoraContentSection {
    Home,
    Live,
    Movies,
    Series,
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
        accentColor = 0xFF00E5FF
    )

    private val liveSports = NexoraContentItem(
        id = "live-sports",
        title = "Arena Sports",
        subtitle = "Licensed source placeholder",
        category = "Sports",
        description = "Mock sports category item. Future legal provider integration can map here safely.",
        type = NexoraContentType.Live,
        badge = "LIVE",
        accentColor = 0xFFFF6D00
    )

    private val liveCinema = NexoraContentItem(
        id = "live-cinema",
        title = "Cinema Linear",
        subtitle = "Linear TV placeholder",
        category = "Cinema",
        description = "Mock linear TV item used only for local UI and route foundation.",
        type = NexoraContentType.Live,
        badge = "LIVE",
        accentColor = 0xFF2962FF
    )

    private val liveDocumentary = NexoraContentItem(
        id = "live-documentary",
        title = "Documentary Plus",
        subtitle = "Channel group shell",
        category = "Documentary",
        description = "Mock documentary channel group for category row readability.",
        type = NexoraContentType.Live,
        badge = "LIVE",
        accentColor = 0xFF00BFA5
    )

    private val movieOrbit = NexoraContentItem(
        id = "movie-orbit-fall",
        title = "Orbit Fall",
        subtitle = "Movie detail foundation",
        category = "Sci-Fi",
        description = "Mock movie item prepared for detail-screen and selected-player route testing.",
        type = NexoraContentType.Movie,
        badge = "MOVIE",
        accentColor = 0xFF7C4DFF
    )

    private val movieGlass = NexoraContentItem(
        id = "movie-glass-tower",
        title = "Glass Tower",
        subtitle = "Featured VOD shell",
        category = "Thriller",
        description = "Mock VOD item. Metadata is local and safe; no provider/API data is used.",
        type = NexoraContentType.Movie,
        badge = "MOVIE",
        accentColor = 0xFF2962FF
    )

    private val movieCoast = NexoraContentItem(
        id = "movie-silent-coast",
        title = "Silent Coast",
        subtitle = "Watch later placeholder",
        category = "Drama",
        description = "Mock watch-later movie card for Home and Movies rows.",
        type = NexoraContentType.Movie,
        badge = "MOVIE",
        accentColor = 0xFF00BFA5
    )

    private val seriesMidnight = NexoraContentItem(
        id = "series-midnight-grid",
        title = "Midnight Grid",
        subtitle = "S01 E04 • 42 min left",
        category = "Series",
        description = "Mock series item for detail routing and episode-aware row copy.",
        type = NexoraContentType.Series,
        badge = "SERIES",
        accentColor = 0xFF00E5FF
    )

    private val seriesArchive = NexoraContentItem(
        id = "series-deep-archive",
        title = "Deep Archive",
        subtitle = "New season placeholder",
        category = "Series",
        description = "Mock series library card. Future episode catalog can attach here.",
        type = NexoraContentType.Series,
        badge = "SERIES",
        accentColor = 0xFF7C4DFF
    )

    private val seriesSignal = NexoraContentItem(
        id = "series-signal-room",
        title = "Signal Room",
        subtitle = "Episode row shell",
        category = "Series",
        description = "Mock series row item for remote-readable navigation testing.",
        type = NexoraContentType.Series,
        badge = "SERIES",
        accentColor = 0xFF2962FF
    )

    private val settingAccount = NexoraContentItem(
        id = "setting-account",
        title = "Account",
        subtitle = "Device access shell",
        category = "Settings",
        description = "Local settings placeholder. Production auth and billing are not changed here.",
        type = NexoraContentType.Setting,
        badge = "LOCAL",
        accentColor = 0xFF00E5FF,
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
        accentColor = 0xFF2962FF,
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
        accentColor = 0xFF00BFA5,
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
        settingAccount,
        settingPlayback,
        settingSupport
    )

    fun rowsFor(section: NexoraContentSection): List<NexoraContentRow> = when (section) {
        NexoraContentSection.Home -> listOf(
            NexoraContentRow(
                title = "Continue Watching",
                subtitle = "Resume-ready mock row",
                items = listOf(seriesMidnight, movieCoast, liveNews)
            ),
            NexoraContentRow(
                title = "Featured Library",
                subtitle = "Live, movies and series in one safe catalog",
                items = listOf(movieOrbit, liveCinema, seriesArchive, movieGlass)
            )
        )

        NexoraContentSection.Live -> listOf(
            NexoraContentRow(
                title = "Live TV Categories",
                subtitle = "Mock channel groups only",
                items = listOf(liveNews, liveSports, liveCinema, liveDocumentary)
            )
        )

        NexoraContentSection.Movies -> listOf(
            NexoraContentRow(
                title = "Movies",
                subtitle = "Local VOD catalog shell",
                items = listOf(movieOrbit, movieGlass, movieCoast)
            )
        )

        NexoraContentSection.Series -> listOf(
            NexoraContentRow(
                title = "Series",
                subtitle = "Local series catalog shell",
                items = listOf(seriesMidnight, seriesArchive, seriesSignal)
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

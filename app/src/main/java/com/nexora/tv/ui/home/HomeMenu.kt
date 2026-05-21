package com.nexora.tv.ui.home

import com.nexora.tv.data.content.NexoraContentSection

internal enum class HomeMenu(val key: String, val icon: String, val section: NexoraContentSection) {
    Home("Home", "⌂", NexoraContentSection.Home),
    Movies("Movies", "◫", NexoraContentSection.Movies),
    Series("Series", "▤", NexoraContentSection.Series),
    Live("Live", "◉", NexoraContentSection.Live),
    Settings("Settings", "⚙", NexoraContentSection.Settings)
}

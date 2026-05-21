package com.nexora.tv.data.live

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LivePlaybackSession {
    var currentChannel: LiveChannel? by mutableStateOf(null)
        private set

    var detailItem: LiveChannel? by mutableStateOf(null)
        private set

    var loadedChannels: List<LiveChannel> by mutableStateOf(emptyList())
        private set

    var loadedMovies: List<LiveChannel> by mutableStateOf(emptyList())
        private set

    var loadedSeries: List<LiveChannel> by mutableStateOf(emptyList())
        private set

    var sourceTitle: String by mutableStateOf("No source loaded")
        private set

    var sourceStatus: String by mutableStateOf("Add a source to load items.")
        private set

    val hasLoadedChannels: Boolean
        get() = loadedChannels.isNotEmpty()

    fun select(channel: LiveChannel) {
        currentChannel = channel
    }

    fun selectDetail(item: LiveChannel) {
        detailItem = item
    }

    fun clearDetail() {
        detailItem = null
    }

    fun setCatalog(
        title: String,
        channels: List<LiveChannel>,
        movies: List<LiveChannel> = emptyList(),
        series: List<LiveChannel> = emptyList()
    ) {
        loadedChannels = channels.distinctBy { it.streamUrl.ifBlank { it.name + it.group } }.take(1500)
        loadedMovies = movies.distinctBy { it.streamUrl.ifBlank { it.name + it.group } }.take(1500)
        loadedSeries = series.distinctBy { it.streamUrl.ifBlank { it.name + it.group } }.take(1500)
        sourceTitle = title
        sourceStatus = "${loadedChannels.size} live / ${loadedMovies.size} movies / ${loadedSeries.size} series"
    }

    fun clearCatalog() {
        loadedChannels = emptyList()
        loadedMovies = emptyList()
        loadedSeries = emptyList()
        detailItem = null
        sourceTitle = "No source loaded"
        sourceStatus = "Add a source to load items."
    }

    fun clearPlayer() {
        currentChannel = null
    }

    fun clear() {
        clearPlayer()
    }
}

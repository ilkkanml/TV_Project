package com.nexora.tv.data.live

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LivePlaybackSession {
    var currentChannel: LiveChannel? by mutableStateOf(null)
        private set

    var loadedChannels: List<LiveChannel> by mutableStateOf(emptyList())
        private set

    var sourceTitle: String by mutableStateOf("No media source loaded")
        private set

    var sourceStatus: String by mutableStateOf("Add a media source to load channels.")
        private set

    val hasLoadedChannels: Boolean
        get() = loadedChannels.isNotEmpty()

    fun select(channel: LiveChannel) {
        currentChannel = channel
    }

    fun setCatalog(title: String, channels: List<LiveChannel>) {
        loadedChannels = channels
            .distinctBy { it.streamUrl }
            .take(1500)

        sourceTitle = title
        sourceStatus = if (loadedChannels.isEmpty()) {
            "No channels found."
        } else {
            "${loadedChannels.size} channels ready."
        }
    }

    fun clearCatalog() {
        loadedChannels = emptyList()
        sourceTitle = "No media source loaded"
        sourceStatus = "Add a media source to load channels."
    }

    fun clearPlayer() {
        currentChannel = null
    }

    fun clear() {
        clearPlayer()
    }
}

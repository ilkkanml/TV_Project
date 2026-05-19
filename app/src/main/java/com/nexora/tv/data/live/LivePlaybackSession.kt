package com.nexora.tv.data.live

object LivePlaybackSession {
    var currentChannel: LiveChannel? = null
        private set

    fun select(channel: LiveChannel) {
        currentChannel = channel
    }

    fun clear() {
        currentChannel = null
    }
}

package com.nexora.tv.data.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import java.util.UUID

data class MediaProfile(
    val id: String = UUID.randomUUID().toString(),
    val profileName: String,
    val sourceType: String,
    val serverAddress: String,
    val accountName: String,
    val accessKey: String,
    val live: List<LiveChannel>,
    val movies: List<LiveChannel>,
    val series: List<LiveChannel>,
    val status: String
)

object MediaProfileStore {
    val profiles = mutableStateListOf<MediaProfile>()

    var selectedProfileId: String? by mutableStateOf(null)
        private set

    var editingProfileId: String? by mutableStateOf(null)
        private set

    val selectedProfile: MediaProfile?
        get() = profiles.firstOrNull { it.id == selectedProfileId } ?: profiles.firstOrNull()

    val editingProfile: MediaProfile?
        get() = profiles.firstOrNull { it.id == editingProfileId }

    fun select(profile: MediaProfile) {
        selectedProfileId = profile.id
        LivePlaybackSession.setCatalog(
            title = profile.profileName,
            channels = profile.live,
            movies = profile.movies,
            series = profile.series
        )
    }

    fun startAdd() {
        editingProfileId = null
    }

    fun startEdit(profile: MediaProfile) {
        editingProfileId = profile.id
    }

    fun upsert(
        profileName: String,
        sourceType: String,
        serverAddress: String,
        accountName: String,
        accessKey: String,
        live: List<LiveChannel>,
        movies: List<LiveChannel>,
        series: List<LiveChannel>,
        status: String
    ): MediaProfile {
        val existing = editingProfile
        val updated = MediaProfile(
            id = existing?.id ?: UUID.randomUUID().toString(),
            profileName = profileName.ifBlank { "User Profile" },
            sourceType = sourceType,
            serverAddress = serverAddress,
            accountName = accountName,
            accessKey = accessKey,
            live = live,
            movies = movies,
            series = series,
            status = status
        )

        val index = profiles.indexOfFirst { it.id == updated.id }
        if (index >= 0) {
            profiles[index] = updated
        } else {
            profiles.add(updated)
        }

        editingProfileId = null
        select(updated)
        return updated
    }
}

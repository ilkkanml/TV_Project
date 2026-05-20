package com.nexora.tv.data.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import org.json.JSONArray
import org.json.JSONObject
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
    private const val PREFS = "nexora_media_profiles"
    private const val KEY_PROFILES = "profiles_json"
    private const val KEY_SELECTED = "selected_profile_id"

    val profiles = mutableStateListOf<MediaProfile>()

    var selectedProfileId: String? by mutableStateOf(null)
        private set

    var editingProfileId: String? by mutableStateOf(null)
        private set

    private var initialized by mutableStateOf(false)

    val selectedProfile: MediaProfile?
        get() = profiles.firstOrNull { it.id == selectedProfileId } ?: profiles.firstOrNull()

    val editingProfile: MediaProfile?
        get() = profiles.firstOrNull { it.id == editingProfileId }

    fun init(context: Context) {
        if (initialized) return
        initialized = true

        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        selectedProfileId = prefs.getString(KEY_SELECTED, null)
        val raw = prefs.getString(KEY_PROFILES, null) ?: return

        runCatching {
            val array = JSONArray(raw)
            profiles.clear()
            for (i in 0 until array.length()) {
                profiles.add(array.getJSONObject(i).toProfile())
            }
        }

        selectedProfile?.let { select(it, context) }
    }

    fun select(profile: MediaProfile, context: Context? = null) {
        selectedProfileId = profile.id
        LivePlaybackSession.setCatalog(
            title = profile.profileName,
            channels = profile.live,
            movies = profile.movies,
            series = profile.series
        )
        if (context != null) save(context)
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
        status: String,
        context: Context? = null
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
        selectedProfileId = updated.id
        LivePlaybackSession.setCatalog(updated.profileName, updated.live, updated.movies, updated.series)
        if (context != null) save(context)
        return updated
    }

    fun save(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val array = JSONArray()
        profiles.forEach { array.put(it.toJson()) }
        prefs.edit()
            .putString(KEY_PROFILES, array.toString())
            .putString(KEY_SELECTED, selectedProfileId)
            .apply()
    }

    private fun MediaProfile.toJson(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("profileName", profileName)
            .put("sourceType", sourceType)
            .put("serverAddress", serverAddress)
            .put("accountName", accountName)
            .put("accessKey", accessKey)
            .put("live", live.toJsonArray())
            .put("movies", movies.toJsonArray())
            .put("series", series.toJsonArray())
            .put("status", status)
    }

    private fun JSONObject.toProfile(): MediaProfile {
        return MediaProfile(
            id = optString("id", UUID.randomUUID().toString()),
            profileName = optString("profileName", "User Profile"),
            sourceType = optString("sourceType", "Local"),
            serverAddress = optString("serverAddress", ""),
            accountName = optString("accountName", ""),
            accessKey = optString("accessKey", ""),
            live = optJSONArray("live").toChannels(),
            movies = optJSONArray("movies").toChannels(),
            series = optJSONArray("series").toChannels(),
            status = optString("status", "Ready")
        )
    }

    private fun List<LiveChannel>.toJsonArray(): JSONArray {
        val array = JSONArray()
        forEach { channel ->
            array.put(
                JSONObject()
                    .put("name", channel.name)
                    .put("streamUrl", channel.streamUrl)
                    .put("group", channel.group)
                    .put("logoUrl", channel.logoUrl)
            )
        }
        return array
    }

    private fun JSONArray?.toChannels(): List<LiveChannel> {
        if (this == null) return emptyList()
        val result = mutableListOf<LiveChannel>()
        for (i in 0 until length()) {
            val item = optJSONObject(i) ?: continue
            result.add(
                LiveChannel(
                    name = item.optString("name"),
                    streamUrl = item.optString("streamUrl"),
                    group = item.optString("group", "Live"),
                    logoUrl = item.optString("logoUrl", "")
                )
            )
        }
        return result
    }
}

package com.nexora.tv.data.live

import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object ProviderSeriesEpisodeLoader {

    fun loadEpisodes(server: String, user: String, pass: String, seriesId: String): List<LiveChannel> {
        val base = server.trim().trimEnd('/')
        if (!base.startsWith("http://") && !base.startsWith("https://")) error("Server URL must start with http or https.")
        if (user.isBlank() || pass.isBlank() || seriesId.isBlank()) return emptyList()

        val safeUser = URLEncoder.encode(user.trim(), "UTF-8")
        val safePass = URLEncoder.encode(pass.trim(), "UTF-8")
        val safeSeriesId = URLEncoder.encode(seriesId.trim(), "UTF-8")
        val text = readText("$base/player_api.php?username=$safeUser&password=$safePass&action=get_series_info&series_id=$safeSeriesId")
        val root = JSONObject(text)
        val episodesObject = root.optJSONObject("episodes") ?: return emptyList()
        val result = mutableListOf<LiveChannel>()

        episodesObject.keys().forEach { seasonKey ->
            val episodes = episodesObject.optJSONArray(seasonKey) ?: JSONArray()
            for (i in 0 until episodes.length()) {
                val item = episodes.optJSONObject(i) ?: continue
                val episodeId = item.optString("id")
                val title = item.optString("title", item.optString("name", "Episode"))
                val extension = item.optString("container_extension", "mp4").ifBlank { "mp4" }
                if (episodeId.isBlank() || title.isBlank()) continue

                val seasonName = "Season ${item.optString("season", seasonKey).ifBlank { seasonKey }}"
                val episodeNumber = item.optString("episode_num", (i + 1).toString()).ifBlank { (i + 1).toString() }
                result.add(
                    LiveChannel(
                        name = title,
                        streamUrl = "$base/series/$safeUser/$safePass/$episodeId.$extension",
                        group = seasonName,
                        logoUrl = item.optJSONObject("info")?.optString("movie_image", "").orEmpty(),
                        mediaId = episodeId,
                        mediaKind = MediaKind.Episode,
                        description = item.optJSONObject("info")?.optString("plot", "").orEmpty(),
                        seasonName = seasonName,
                        episodeName = "E$episodeNumber · $title"
                    )
                )
            }
        }

        return result.distinctBy { it.streamUrl }
    }

    private fun readText(address: String): String {
        val connection = URL(address).openConnection() as HttpURLConnection
        connection.connectTimeout = 15000
        connection.readTimeout = 25000
        connection.requestMethod = "GET"
        connection.setRequestProperty("User-Agent", "NexoraTV")
        try {
            val code = connection.responseCode
            if (code !in 200..299) error("Server response: $code")
            return connection.inputStream.bufferedReader().use { it.readText() }
        } finally {
            connection.disconnect()
        }
    }
}

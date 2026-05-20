package com.nexora.tv.data.live

import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class ProviderPortalResult(
    val live: List<LiveChannel>,
    val movies: List<LiveChannel>,
    val series: List<LiveChannel>
)

object ProviderPortalLoader {

    fun loadAll(server: String, user: String, pass: String): ProviderPortalResult {
        val base = server.trim().trimEnd('/')
        if (!base.startsWith("http://") && !base.startsWith("https://")) {
            error("Server URL must start with http or https.")
        }

        val cleanUser = user.trim()
        val cleanPass = pass.trim()
        if (cleanUser.isBlank() || cleanPass.isBlank()) {
            error("User name and password are required.")
        }

        val safeUser = URLEncoder.encode(cleanUser, "UTF-8")
        val safePass = URLEncoder.encode(cleanPass, "UTF-8")

        val live = runCatching { loadLive(base, safeUser, safePass) }.getOrDefault(emptyList())
        val movies = runCatching { loadMovies(base, safeUser, safePass) }.getOrDefault(emptyList())
        val series = runCatching { loadSeries(base, safeUser, safePass) }.getOrDefault(emptyList())

        if (live.isEmpty() && movies.isEmpty() && series.isEmpty()) {
            error("No media data found. Check server, user name, password, or provider access.")
        }

        return ProviderPortalResult(live = live, movies = movies, series = series)
    }

    private fun loadLive(base: String, safeUser: String, safePass: String): List<LiveChannel> {
        val categoryMap = loadCategories(base, safeUser, safePass, "get_live_categories")
        val text = readText("$base/player_api.php?username=$safeUser&password=$safePass&action=get_live_streams")
        val array = JSONArray(text)
        val result = mutableListOf<LiveChannel>()
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            val streamId = item.optString("stream_id")
            val name = item.optString("name", "Live Channel")
            val categoryId = item.optString("category_id")
            val logo = item.optString("stream_icon", "")
            val extension = item.optString("container_extension", "ts").ifBlank { "ts" }
            if (streamId.isBlank() || name.isBlank()) continue
            val group = categoryMap[categoryId] ?: item.optString("category_name", "Live").ifBlank { "Live" }
            result.add(LiveChannel(name = name, streamUrl = "$base/live/$safeUser/$safePass/$streamId.$extension", group = group, logoUrl = logo))
        }
        return result.distinctBy { it.streamUrl }.take(1500)
    }

    private fun loadMovies(base: String, safeUser: String, safePass: String): List<LiveChannel> {
        val categoryMap = loadCategories(base, safeUser, safePass, "get_vod_categories")
        val text = readText("$base/player_api.php?username=$safeUser&password=$safePass&action=get_vod_streams")
        val array = JSONArray(text)
        val result = mutableListOf<LiveChannel>()
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            val streamId = item.optString("stream_id")
            val name = item.optString("name", "Movie")
            val categoryId = item.optString("category_id")
            val logo = item.optString("stream_icon", "")
            val extension = item.optString("container_extension", "mp4").ifBlank { "mp4" }
            if (streamId.isBlank() || name.isBlank()) continue
            val group = categoryMap[categoryId] ?: item.optString("category_name", "Movies").ifBlank { "Movies" }
            result.add(LiveChannel(name = name, streamUrl = "$base/movie/$safeUser/$safePass/$streamId.$extension", group = group, logoUrl = logo))
        }
        return result.distinctBy { it.streamUrl }.take(1500)
    }

    private fun loadSeries(base: String, safeUser: String, safePass: String): List<LiveChannel> {
        val categoryMap = loadCategories(base, safeUser, safePass, "get_series_categories")
        val text = readText("$base/player_api.php?username=$safeUser&password=$safePass&action=get_series")
        val array = JSONArray(text)
        val result = mutableListOf<LiveChannel>()
        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)
            val name = item.optString("name", "Series")
            val categoryId = item.optString("category_id")
            val logo = item.optString("cover", item.optString("stream_icon", ""))
            if (name.isBlank()) continue
            val group = categoryMap[categoryId] ?: item.optString("category_name", "Series").ifBlank { "Series" }
            result.add(LiveChannel(name = name, streamUrl = "", group = group, logoUrl = logo))
        }
        return result.distinctBy { it.name + it.group }.take(1500)
    }

    private fun loadCategories(base: String, safeUser: String, safePass: String, action: String): Map<String, String> {
        return runCatching {
            val text = readText("$base/player_api.php?username=$safeUser&password=$safePass&action=$action")
            val array = JSONArray(text)
            val map = mutableMapOf<String, String>()
            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)
                val id = item.optString("category_id")
                val name = item.optString("category_name", "Live")
                if (id.isNotBlank()) map[id] = name.ifBlank { "Live" }
            }
            map
        }.getOrDefault(emptyMap())
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

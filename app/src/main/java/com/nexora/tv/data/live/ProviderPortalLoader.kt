package com.nexora.tv.data.live

import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object ProviderPortalLoader {

    fun loadLive(server: String, user: String, pass: String): List<LiveChannel> {
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

        val categoryMap = loadLiveCategories(base, safeUser, safePass)
        val streamsText = readText("$base/player_api.php?username=$safeUser&password=$safePass&action=get_live_streams")
        val streams = JSONArray(streamsText)
        val result = mutableListOf<LiveChannel>()

        for (i in 0 until streams.length()) {
            val item = streams.getJSONObject(i)

            val streamId = item.optString("stream_id")
            val name = item.optString("name", "Live Channel")
            val categoryId = item.optString("category_id")
            val logo = item.optString("stream_icon", "")
            val extension = item.optString("container_extension", "ts").ifBlank { "ts" }

            if (streamId.isBlank() || name.isBlank()) continue

            val group = categoryMap[categoryId]
                ?: item.optString("category_name", "Live").ifBlank { "Live" }

            result.add(
                LiveChannel(
                    name = name,
                    streamUrl = "$base/live/$safeUser/$safePass/$streamId.$extension",
                    group = group,
                    logoUrl = logo
                )
            )
        }

        return result
            .distinctBy { it.streamUrl }
            .take(1500)
    }

    private fun loadLiveCategories(
        base: String,
        safeUser: String,
        safePass: String
    ): Map<String, String> {
        return runCatching {
            val text = readText("$base/player_api.php?username=$safeUser&password=$safePass&action=get_live_categories")
            val array = JSONArray(text)
            val map = mutableMapOf<String, String>()

            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)
                val id = item.optString("category_id")
                val name = item.optString("category_name", "Live")

                if (id.isNotBlank()) {
                    map[id] = name.ifBlank { "Live" }
                }
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
            if (code !in 200..299) {
                error("Server response: $code")
            }

            return connection.inputStream.bufferedReader().use { it.readText() }
        } finally {
            connection.disconnect()
        }
    }
}

package com.nexora.tv.data.live

import java.net.HttpURLConnection
import java.net.URL

object RemoteListLoader {
    fun load(address: String): List<LiveChannel> {
        val clean = address.trim()
        if (!clean.startsWith("http://") && !clean.startsWith("https://")) {
            error("Address must start with http or https")
        }

        val connection = URL(clean).openConnection() as HttpURLConnection
        connection.connectTimeout = 15000
        connection.readTimeout = 20000
        connection.requestMethod = "GET"
        connection.setRequestProperty("User-Agent", "NexoraTV")

        try {
            val code = connection.responseCode
            if (code !in 200..299) error("Server response: $code")
            val body = connection.inputStream.bufferedReader().use { it.readText() }
            return M3uParser.parse(body)
        } finally {
            connection.disconnect()
        }
    }
}

package com.nexora.tv.data.live

object M3uParser {
    fun parse(text: String): List<LiveChannel> {
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }
        val result = mutableListOf<LiveChannel>()
        var pendingName = "Live Channel"
        var pendingGroup = "Live"
        var pendingLogo = ""

        for (line in lines) {
            if (line.startsWith("#EXTINF", ignoreCase = true)) {
                pendingName = line.substringAfterLast(',', "Live Channel").trim().ifBlank { "Live Channel" }
                pendingGroup = readAttribute(line, "group-title").ifBlank { "Live" }
                pendingLogo = readAttribute(line, "tvg-logo")
            } else if (line.startsWith("http://", ignoreCase = true) || line.startsWith("https://", ignoreCase = true)) {
                result.add(
                    LiveChannel(
                        name = pendingName,
                        streamUrl = line,
                        group = pendingGroup,
                        logoUrl = pendingLogo
                    )
                )
                pendingName = "Live Channel"
                pendingGroup = "Live"
                pendingLogo = ""
            }
        }

        return result.distinctBy { it.streamUrl }.take(500)
    }

    private fun readAttribute(line: String, key: String): String {
        val marker = "$key=\""
        val start = line.indexOf(marker, ignoreCase = true)
        if (start < 0) return ""
        val valueStart = start + marker.length
        val valueEnd = line.indexOf('"', valueStart)
        if (valueEnd <= valueStart) return ""
        return line.substring(valueStart, valueEnd).trim()
    }
}

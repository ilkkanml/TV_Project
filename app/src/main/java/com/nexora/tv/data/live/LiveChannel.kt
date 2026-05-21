package com.nexora.tv.data.live

enum class MediaKind {
    Live,
    Movie,
    Series,
    Episode
}

data class LiveChannel(
    val name: String,
    val streamUrl: String,
    val group: String = "Live",
    val logoUrl: String = "",
    val mediaId: String = "",
    val mediaKind: MediaKind = MediaKind.Live,
    val description: String = "",
    val seasonName: String = "",
    val episodeName: String = ""
)

package com.nexora.tv.data.live

data class LiveChannel(
    val name: String,
    val streamUrl: String,
    val group: String = "Live",
    val logoUrl: String = ""
)

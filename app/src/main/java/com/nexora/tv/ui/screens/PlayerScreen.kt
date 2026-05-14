package com.nexora.tv.ui.screens

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun PlayerScreen() {

    val context = LocalContext.current

    val player = ExoPlayer.Builder(context).build().apply {

        setMediaItem(
            MediaItem.fromUri(
                "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
            )
        )

        prepare()
        playWhenReady = true
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                this.player = player
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )
}

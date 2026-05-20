package com.nexora.tv.ui.screens

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.TrackGroup
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xAA11131C)

private data class TrackOption(
    val title: String,
    val type: Int,
    val group: TrackGroup,
    val index: Int
)

private data class TrackCatalog(
    val video: List<TrackOption> = emptyList(),
    val audio: List<TrackOption> = emptyList(),
    val subtitles: List<TrackOption> = emptyList()
)

@Composable
fun PlayerScreen(navController: NavController) {
    val channel = LivePlaybackSession.currentChannel

    NexoraCinematicBackdrop {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (channel != null) {
                LivePlayerStage(navController, channel)
            } else {
                EmptyPlayerState(navController)
            }
        }
    }
}

@Composable
private fun LivePlayerStage(navController: NavController, channel: LiveChannel) {
    val context = LocalContext.current
    var showSettings by remember { mutableStateOf(false) }
    var trackCatalog by remember { mutableStateOf(TrackCatalog()) }

    val player = remember(channel.streamUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(channel.streamUrl))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onTracksChanged(tracks: Tracks) {
                trackCatalog = buildTrackCatalog(tracks)
            }
        }

        player.addListener(listener)
        trackCatalog = buildTrackCatalog(player.currentTracks)

        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { viewContext ->
                PlayerView(viewContext).apply {
                    layoutParams = android.view.ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    useController = true
                    this.player = player
                }
            },
            update = { view -> view.player = player }
        )

        TopOverlay(
            navController = navController,
            channel = channel,
            onSettingsClick = { showSettings = !showSettings }
        )

        BottomOverlay(
            navController = navController,
            channel = channel,
            onSettingsClick = { showSettings = !showSettings }
        )

        if (showSettings) {
            PlaybackSettingsPanel(
                player = player,
                catalog = trackCatalog,
                onClose = { showSettings = false }
            )
        }
    }
}

@Composable
private fun TopOverlay(
    navController: NavController,
    channel: LiveChannel,
    onSettingsClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(24.dp)
            .background(PanelDark, RoundedCornerShape(22.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(22.dp))
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(470.dp)) {
            Text(
                text = channel.name,
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = channel.group,
                color = NexoraVioletSoft,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        PlayerChip("LIVE")
        PlayerNavButton("Settings", width = 110, onClick = onSettingsClick)
        PlayerNavButton("Back") { navController.popBackStack() }
        PlayerNavButton("Home") { goHome(navController) }
    }
}

@Composable
private fun BoxScope.BottomOverlay(
    navController: NavController,
    channel: LiveChannel,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(24.dp)
            .width(570.dp)
            .background(PanelDark, RoundedCornerShape(22.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(22.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Now Playing",
            color = NexoraVioletSoft,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
        )
        Text(
            text = channel.name,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            PlayerNavButton("Settings", width = 110, onClick = onSettingsClick)
            PlayerNavButton("Back") { navController.popBackStack() }
            PlayerNavButton("Home") { goHome(navController) }
        }
    }
}

@Composable
private fun BoxScope.PlaybackSettingsPanel(
    player: ExoPlayer,
    catalog: TrackCatalog,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 28.dp)
            .width(390.dp)
            .height(610.dp)
            .background(PanelDark, RoundedCornerShape(26.dp))
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(26.dp))
            .padding(18.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.width(350.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Video Settings", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
            PlayerNavButton("Close", width = 82, onClick = onClose)
        }

        Text(
            text = "Available options depend on the stream.",
            color = Color.White.copy(alpha = 0.58f),
            fontSize = 12.sp,
            lineHeight = 17.sp
        )

        SettingsSectionTitle("Quality")
        SettingsOptionButton("Auto") { clearTrackType(player, C.TRACK_TYPE_VIDEO) }
        if (catalog.video.isEmpty()) {
            SettingsEmptyText("No manual quality option.")
        } else {
            catalog.video.forEach { option -> SettingsOptionButton(option.title) { applyTrack(player, option) } }
        }

        SettingsSectionTitle("Audio")
        if (catalog.audio.isEmpty()) {
            SettingsEmptyText("No language option.")
        } else {
            catalog.audio.forEach { option -> SettingsOptionButton(option.title) { applyTrack(player, option) } }
        }

        SettingsSectionTitle("Subtitles")
        SettingsOptionButton("Off") { disableTextTrack(player) }
        if (catalog.subtitles.isEmpty()) {
            SettingsEmptyText("No subtitle option.")
        } else {
            catalog.subtitles.forEach { option -> SettingsOptionButton(option.title) { applyTrack(player, option) } }
        }
    }
}

@Composable
private fun SettingsSectionTitle(text: String) {
    Text(text = text, color = NexoraVioletSoft, fontSize = 13.sp, fontWeight = FontWeight.Black, letterSpacing = 1.1.sp)
}

@Composable
private fun SettingsEmptyText(text: String) {
    Text(text = text, color = Color.White.copy(alpha = 0.48f), fontSize = 12.sp)
}

@Composable
private fun SettingsOptionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(340.dp).height(42.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PanelSoft, contentColor = Color.White)
    ) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun EmptyPlayerState(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No channel selected", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Go back and select a channel.", color = Color.White.copy(alpha = 0.68f), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            PlayerNavButton("Back") { navController.popBackStack() }
            PlayerNavButton("Home") { goHome(navController) }
        }
    }
}

@Composable
private fun PlayerChip(text: String) {
    Box(
        modifier = Modifier
            .background(NexoraViolet.copy(alpha = 0.20f), RoundedCornerShape(14.dp))
            .border(1.dp, NexoraViolet.copy(alpha = 0.50f), RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun PlayerNavButton(text: String, width: Int = 86, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(width.dp).height(40.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.10f), contentColor = Color.White)
    ) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Black)
    }
}

private fun goHome(navController: NavController) {
    navController.navigate(AppDestinations.Home.route) {
        launchSingleTop = true
    }
}

private fun buildTrackCatalog(tracks: Tracks): TrackCatalog {
    val video = mutableListOf<TrackOption>()
    val audio = mutableListOf<TrackOption>()
    val subtitles = mutableListOf<TrackOption>()

    tracks.groups.forEach { group ->
        for (index in 0 until group.length) {
            if (!group.isTrackSupported(index)) continue
            val format = group.getTrackFormat(index)
            val option = TrackOption(
                title = when (group.type) {
                    C.TRACK_TYPE_VIDEO -> buildVideoLabel(format.height, format.bitrate)
                    C.TRACK_TYPE_AUDIO -> buildLanguageLabel(format.label, format.language, "Audio")
                    C.TRACK_TYPE_TEXT -> buildLanguageLabel(format.label, format.language, "Subtitle")
                    else -> "Track"
                },
                type = group.type,
                group = group.mediaTrackGroup,
                index = index
            )
            when (group.type) {
                C.TRACK_TYPE_VIDEO -> video.add(option)
                C.TRACK_TYPE_AUDIO -> audio.add(option)
                C.TRACK_TYPE_TEXT -> subtitles.add(option)
            }
        }
    }

    return TrackCatalog(
        video = video.distinctBy { it.title },
        audio = audio.distinctBy { it.title },
        subtitles = subtitles.distinctBy { it.title }
    )
}

private fun buildVideoLabel(height: Int, bitrate: Int): String {
    val quality = if (height > 0) "${height}p" else "Video"
    val speed = if (bitrate > 0) " · ${bitrate / 1_000_000.0} Mbps" else ""
    return quality + speed
}

private fun buildLanguageLabel(label: String?, language: String?, fallback: String): String {
    return label?.takeIf { it.isNotBlank() }
        ?: language?.takeIf { it.isNotBlank() }?.uppercase()
        ?: fallback
}

private fun applyTrack(player: ExoPlayer, option: TrackOption) {
    val parameters = player.trackSelectionParameters
        .buildUpon()
        .setTrackTypeDisabled(option.type, false)
        .clearOverridesOfType(option.type)
        .setOverrideForType(TrackSelectionOverride(option.group, listOf(option.index)))
        .build()

    player.trackSelectionParameters = parameters
}

private fun clearTrackType(player: ExoPlayer, type: Int) {
    val parameters = player.trackSelectionParameters
        .buildUpon()
        .setTrackTypeDisabled(type, false)
        .clearOverridesOfType(type)
        .build()

    player.trackSelectionParameters = parameters
}

private fun disableTextTrack(player: ExoPlayer) {
    val parameters = player.trackSelectionParameters
        .buildUpon()
        .clearOverridesOfType(C.TRACK_TYPE_TEXT)
        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true)
        .build()

    player.trackSelectionParameters = parameters
}

package com.nexora.tv.ui.screens

import android.view.KeyEvent as AndroidKeyEvent
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.activity.compose.BackHandler
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
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.data.live.MediaKind
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xAA11131C)

private data class TrackOption(val title: String, val type: Int, val group: TrackGroup, val index: Int)
private data class TrackCatalog(val video: List<TrackOption> = emptyList(), val audio: List<TrackOption> = emptyList(), val subtitles: List<TrackOption> = emptyList())

@Composable
fun PlayerScreen(navController: NavController) {
    val channel = LivePlaybackSession.currentChannel
    NexoraCinematicBackdrop {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            if (channel != null && channel.streamUrl.isNotBlank()) LivePlayerStage(navController, channel) else EmptyPlayerState(navController)
        }
    }
}

@Composable
private fun LivePlayerStage(navController: NavController, channel: LiveChannel) {
    val context = LocalContext.current
    var showSettings by remember { mutableStateOf(false) }
    var trackCatalog by remember { mutableStateOf(TrackCatalog()) }
    var isPlaying by remember { mutableStateOf(true) }

    BackHandler(enabled = showSettings) { showSettings = false }

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

            override fun onIsPlayingChanged(value: Boolean) {
                isPlaying = value
            }
        }
        player.addListener(listener)
        trackCatalog = buildTrackCatalog(player.currentTracks)
        isPlaying = player.isPlaying || player.playWhenReady
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
                    useController = false
                    this.player = player
                    configureRemoteKeys(player) { showSettings = !showSettings }
                }
            },
            update = { view ->
                view.useController = false
                view.player = player
                view.configureRemoteKeys(player) { showSettings = !showSettings }
            }
        )

        TopOverlay(channel = channel)
        BottomOverlay(
            navController = navController,
            channel = channel,
            isPlaying = isPlaying,
            onPlayPauseClick = { togglePlayPause(player) },
            onSettingsClick = { showSettings = !showSettings }
        )

        if (showSettings) PlaybackSettingsPanel(player = player, catalog = trackCatalog, onClose = { showSettings = false })
    }
}

@Composable
private fun TopOverlay(channel: LiveChannel) {
    Row(
        modifier = Modifier.padding(24.dp).background(PanelDark, RoundedCornerShape(22.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(22.dp)).padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.width(520.dp)) {
            Text(channel.name, color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(channel.group, color = NexoraVioletSoft, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                AppLanguageStore.t("Source", "Kaynak") + ": " + LivePlaybackSession.sourceTitle,
                color = Color.White.copy(alpha = 0.54f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        PlayerChip(mediaChipLabel(channel))
    }
}

@Composable
private fun BoxScope.BottomOverlay(
    navController: NavController,
    channel: LiveChannel,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier.align(Alignment.BottomStart).padding(24.dp).width(690.dp).background(PanelDark, RoundedCornerShape(22.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(22.dp)).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(AppLanguageStore.ui("Now Playing"), color = NexoraVioletSoft, fontSize = 11.sp, fontWeight = FontWeight.Black, letterSpacing = 1.2.sp)
        Text(channel.name, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(LivePlaybackSession.sourceStatus, color = Color.White.copy(alpha = 0.46f), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            PlayerNavButton(if (isPlaying) AppLanguageStore.t("Pause", "Duraklat") else AppLanguageStore.t("Play", "Oynat"), width = 104, onClick = onPlayPauseClick)
            PlayerNavButton(AppLanguageStore.ui("Settings"), width = 110, onClick = onSettingsClick)
            PlayerNavButton(AppLanguageStore.ui("Back")) { navController.popBackStack() }
            PlayerNavButton(AppLanguageStore.ui("Home")) { goHome(navController) }
        }
    }
}

@Composable
private fun BoxScope.PlaybackSettingsPanel(player: ExoPlayer, catalog: TrackCatalog, onClose: () -> Unit) {
    Column(
        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 28.dp).width(390.dp).height(610.dp).background(PanelDark, RoundedCornerShape(26.dp)).border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(26.dp)).padding(18.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(modifier = Modifier.width(350.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(AppLanguageStore.ui("Video Settings"), color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
            PlayerNavButton(AppLanguageStore.ui("Close"), width = 82, onClick = onClose)
        }

        Text(AppLanguageStore.ui("Available options depend on the stream."), color = Color.White.copy(alpha = 0.58f), fontSize = 12.sp, lineHeight = 17.sp)

        SettingsSectionTitle(AppLanguageStore.ui("Quality"))
        SettingsOptionButton(AppLanguageStore.ui("Auto")) { clearTrackType(player, C.TRACK_TYPE_VIDEO) }
        if (catalog.video.isEmpty()) SettingsEmptyText(AppLanguageStore.ui("No manual quality option.")) else catalog.video.forEach { option -> SettingsOptionButton(option.title) { applyTrack(player, option) } }

        SettingsSectionTitle(AppLanguageStore.ui("Audio"))
        if (catalog.audio.isEmpty()) SettingsEmptyText(AppLanguageStore.ui("No language option.")) else catalog.audio.forEach { option -> SettingsOptionButton(option.title) { applyTrack(player, option) } }

        SettingsSectionTitle(AppLanguageStore.ui("Subtitles"))
        SettingsOptionButton(AppLanguageStore.ui("Off")) { disableTextTrack(player) }
        if (catalog.subtitles.isEmpty()) SettingsEmptyText(AppLanguageStore.ui("No subtitle option.")) else catalog.subtitles.forEach { option -> SettingsOptionButton(option.title) { applyTrack(player, option) } }
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
    Button(onClick = onClick, modifier = Modifier.width(340.dp).height(42.dp), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = PanelSoft, contentColor = Color.White)) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun EmptyPlayerState(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(48.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(AppLanguageStore.ui("No channel selected"), color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Text(AppLanguageStore.ui("Go back and select a channel."), color = Color.White.copy(alpha = 0.68f), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(22.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            PlayerNavButton(AppLanguageStore.ui("Back")) { navController.popBackStack() }
            PlayerNavButton(AppLanguageStore.ui("Home")) { goHome(navController) }
        }
    }
}

@Composable
private fun PlayerChip(text: String) {
    Box(modifier = Modifier.background(NexoraViolet.copy(alpha = 0.20f), RoundedCornerShape(14.dp)).border(1.dp, NexoraViolet.copy(alpha = 0.50f), RoundedCornerShape(14.dp)).padding(horizontal = 14.dp, vertical = 9.dp), contentAlignment = Alignment.Center) {
        Text(text = text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun PlayerNavButton(text: String, width: Int = 86, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.width(width.dp).height(40.dp), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.10f), contentColor = Color.White)) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Black, maxLines = 1)
    }
}

private fun PlayerView.configureRemoteKeys(player: ExoPlayer, onSettingsClick: () -> Unit) {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    setOnKeyListener { _, _, event ->
        handlePlayerRemoteKey(event, player, onSettingsClick)
    }
}

private fun handlePlayerRemoteKey(event: AndroidKeyEvent, player: ExoPlayer, onSettingsClick: () -> Unit): Boolean {
    if (event.action != AndroidKeyEvent.ACTION_UP) return false

    return when (event.keyCode) {
        AndroidKeyEvent.KEYCODE_DPAD_CENTER,
        AndroidKeyEvent.KEYCODE_ENTER,
        AndroidKeyEvent.KEYCODE_MEDIA_PLAY_PAUSE,
        AndroidKeyEvent.KEYCODE_MEDIA_PLAY,
        AndroidKeyEvent.KEYCODE_MEDIA_PAUSE,
        AndroidKeyEvent.KEYCODE_SPACE -> {
            togglePlayPause(player)
            true
        }

        AndroidKeyEvent.KEYCODE_MENU,
        AndroidKeyEvent.KEYCODE_SETTINGS -> {
            onSettingsClick()
            true
        }

        else -> false
    }
}

private fun togglePlayPause(player: ExoPlayer) {
    if (player.isPlaying) player.pause() else player.play()
}

private fun mediaChipLabel(channel: LiveChannel): String {
    return when (channel.mediaKind) {
        MediaKind.Live -> "LIVE"
        MediaKind.Movie -> "MOVIE"
        MediaKind.Episode -> "EPISODE"
        MediaKind.Series -> "SERIES"
    }
}

private fun goHome(navController: NavController) {
    navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
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

    return TrackCatalog(video = video.distinctBy { it.title }, audio = audio.distinctBy { it.title }, subtitles = subtitles.distinctBy { it.title })
}

private fun buildVideoLabel(height: Int, bitrate: Int): String {
    val quality = if (height > 0) "${height}p" else "Video"
    val speed = if (bitrate > 0) " · ${bitrate / 1_000_000.0} Mbps" else ""
    return quality + speed
}

private fun buildLanguageLabel(label: String?, language: String?, fallback: String): String {
    return label?.takeIf { it.isNotBlank() } ?: language?.takeIf { it.isNotBlank() }?.uppercase() ?: fallback
}

private fun applyTrack(player: ExoPlayer, option: TrackOption) {
    val parameters = player.trackSelectionParameters.buildUpon().setTrackTypeDisabled(option.type, false).clearOverridesOfType(option.type).setOverrideForType(TrackSelectionOverride(option.group, listOf(option.index))).build()
    player.trackSelectionParameters = parameters
}

private fun clearTrackType(player: ExoPlayer, type: Int) {
    val parameters = player.trackSelectionParameters.buildUpon().setTrackTypeDisabled(type, false).clearOverridesOfType(type).build()
    player.trackSelectionParameters = parameters
}

private fun disableTextTrack(player: ExoPlayer) {
    val parameters = player.trackSelectionParameters.buildUpon().clearOverridesOfType(C.TRACK_TYPE_TEXT).setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true).build()
    player.trackSelectionParameters = parameters
}
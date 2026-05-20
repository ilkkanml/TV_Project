package com.nexora.tv.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.data.live.M3uParser
import com.nexora.tv.data.live.ProviderPortalLoader
import com.nexora.tv.data.live.RemoteListLoader
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraGreen = Color(0xFF39FF88)
private val NexoraBlue = Color(0xFF4CC9FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xAA11131C)

private enum class SetupMode(val label: String) {
    ProviderApi("Provider API"),
    M3u("M3U URL"),
    LocalData("Local data"),
    SingleStream("Play single stream")
}

@Composable
fun PlaylistProfileScreen(navController: NavController) {
    val editingProfile = MediaProfileStore.editingProfile
    val connectFocusRequester = remember { FocusRequester() }

    var mode by remember { mutableStateOf(SetupMode.ProviderApi) }
    var profileName by remember { mutableStateOf(editingProfile?.profileName ?: "") }
    var serverAddress by remember { mutableStateOf(editingProfile?.serverAddress ?: "") }
    var accountName by remember { mutableStateOf(editingProfile?.accountName ?: "") }
    var accessKey by remember { mutableStateOf(editingProfile?.accessKey ?: "") }
    var listAddress by remember { mutableStateOf("") }
    var localData by remember { mutableStateOf("") }
    var singleStreamUrl by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showClearConfirm by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf(editingProfile?.status ?: "Create a profile and connect a personal media source.") }

    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Clear form?") },
            text = { Text("This will clear the current form fields. Existing saved profiles will not be deleted.") },
            confirmButton = {
                TextButton(onClick = {
                    profileName = ""
                    serverAddress = ""
                    accountName = ""
                    accessKey = ""
                    listAddress = ""
                    localData = ""
                    singleStreamUrl = ""
                    statusText = "Create a profile and connect a personal media source."
                    showClearConfirm = false
                }) { Text("Clear") }
            },
            dismissButton = { TextButton(onClick = { showClearConfirm = false }) { Text("Cancel") } }
        )
    }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(500.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                Text("NEXORA", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = 2.3.sp, maxLines = 1)
                Text("Create Profile", color = NexoraVioletSoft, fontSize = 24.sp, fontWeight = FontWeight.Black, maxLines = 1)
                Text(
                    text = "Use OK/Enter to edit a field. Moving across fields will not open the keyboard.",
                    color = Color.White.copy(alpha = 0.62f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                TvInputBox("Profile name", profileName, { profileName = it })

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SetupMode.values().forEach { item ->
                        SetupModeButton(
                            text = item.label,
                            selected = mode == item,
                            onClick = {
                                mode = item
                                statusText = when (item) {
                                    SetupMode.ProviderApi -> "Provider API selected. Enter server, user name and password."
                                    SetupMode.M3u -> "M3U URL selected. Paste your list URL."
                                    SetupMode.LocalData -> "Local data selected. Paste list text."
                                    SetupMode.SingleStream -> "Single stream selected. Paste one stream URL."
                                }
                            }
                        )
                    }
                }

                when (mode) {
                    SetupMode.ProviderApi -> {
                        TvInputBox("Server URL", serverAddress, { serverAddress = it })
                        TvInputBox("User name", accountName, { accountName = it })
                        TvInputBox(
                            label = "Password",
                            value = accessKey,
                            onChange = { accessKey = it },
                            secret = true,
                            modifier = Modifier.focusProperties { down = connectFocusRequester }
                        )
                    }
                    SetupMode.M3u -> TvInputBox("M3U URL", listAddress, { listAddress = it })
                    SetupMode.LocalData -> TvInputBox("Paste list data", localData, { localData = it }, height = 82)
                    SetupMode.SingleStream -> TvInputBox("Stream URL", singleStreamUrl, { singleStreamUrl = it })
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            if (isLoading) return@Button
                            val safeProfileName = profileName.ifBlank { "UserProfile" }

                            when (mode) {
                                SetupMode.ProviderApi -> {
                                    isLoading = true
                                    statusText = "Connecting..."
                                    Thread {
                                        val result = runCatching { ProviderPortalLoader.loadAll(serverAddress, accountName, accessKey) }
                                        Handler(Looper.getMainLooper()).post {
                                            isLoading = false
                                            result
                                                .onSuccess { loaded ->
                                                    val status = "${loaded.live.size} live • ${loaded.movies.size} movies • ${loaded.series.size} series"
                                                    LivePlaybackSession.setCatalog(safeProfileName, loaded.live, loaded.movies, loaded.series)
                                                    MediaProfileStore.upsert(
                                                        profileName = safeProfileName,
                                                        sourceType = "Provider API",
                                                        serverAddress = serverAddress,
                                                        accountName = accountName,
                                                        accessKey = accessKey,
                                                        live = loaded.live,
                                                        movies = loaded.movies,
                                                        series = loaded.series,
                                                        status = status
                                                    )
                                                    statusText = status
                                                    navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                                                }
                                                .onFailure { error -> statusText = error.message ?: "Could not connect." }
                                        }
                                    }.start()
                                }

                                SetupMode.M3u -> {
                                    isLoading = true
                                    statusText = "Loading..."
                                    Thread {
                                        val result = runCatching { RemoteListLoader.load(listAddress) }
                                        Handler(Looper.getMainLooper()).post {
                                            isLoading = false
                                            result
                                                .onSuccess { loaded ->
                                                    val status = "${loaded.size} live • 0 movies • 0 series"
                                                    LivePlaybackSession.setCatalog(safeProfileName, loaded)
                                                    MediaProfileStore.upsert(safeProfileName, "M3U URL", listAddress, "", "", loaded, emptyList(), emptyList(), status)
                                                    statusText = status
                                                    if (loaded.isNotEmpty()) navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                                                }
                                                .onFailure { error -> statusText = error.message ?: "Could not load." }
                                        }
                                    }.start()
                                }

                                SetupMode.LocalData -> {
                                    val parsed = M3uParser.parse(localData)
                                    val status = "${parsed.size} live • 0 movies • 0 series"
                                    LivePlaybackSession.setCatalog(safeProfileName, parsed)
                                    MediaProfileStore.upsert(safeProfileName, "Local data", "Local", "", "", parsed, emptyList(), emptyList(), status)
                                    statusText = status
                                    if (parsed.isNotEmpty()) navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                                }

                                SetupMode.SingleStream -> {
                                    val stream = singleStreamUrl.trim()
                                    if (stream.startsWith("http://") || stream.startsWith("https://")) {
                                        val item = LiveChannel("Single Stream", stream, "Single Stream")
                                        LivePlaybackSession.select(item)
                                        MediaProfileStore.upsert(safeProfileName, "Single stream", stream, "", "", listOf(item), emptyList(), emptyList(), "1 stream ready")
                                        navController.navigate(AppDestinations.Player.route) { launchSingleTop = true }
                                    } else {
                                        statusText = "Stream URL must start with http or https."
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .width(174.dp)
                            .height(52.dp)
                            .focusRequester(connectFocusRequester),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
                    ) { Text(if (isLoading) "Loading" else if (mode == SetupMode.SingleStream) "Play Stream" else "Connect", fontWeight = FontWeight.Black, fontSize = 13.sp) }

                    Button(
                        onClick = { showClearConfirm = true },
                        modifier = Modifier.width(102.dp).height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                    ) { Text("Clear", fontSize = 12.sp) }

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.width(102.dp).height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                    ) { Text("Back", fontSize = 12.sp) }
                }

                StatusBox(statusText)
            }

            EarlyAccessPanel()
        }
    }
}

@Composable
private fun TvInputBox(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    secret: Boolean = false,
    height: Int = 66,
    modifier: Modifier = Modifier
) {
    var editing by remember { mutableStateOf(false) }
    var draft by remember(value) { mutableStateOf(value) }
    val displayValue = when {
        value.isBlank() -> "Press OK to enter"
        secret -> "•".repeat(value.length.coerceAtMost(12))
        else -> value
    }

    if (editing) {
        AlertDialog(
            onDismissRequest = { editing = false },
            title = { Text(label) },
            text = {
                OutlinedTextField(
                    value = draft,
                    onValueChange = { draft = it.filterNot { char -> char.isWhitespace() } },
                    singleLine = true,
                    visualTransformation = if (secret) PasswordVisualTransformation() else VisualTransformation.None,
                    colors = OutlinedTextFieldDefaults.colors()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onChange(draft.filterNot { it.isWhitespace() })
                    editing = false
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { editing = false }) { Text("Cancel") } }
        )
    }

    Button(
        onClick = {
            draft = value
            editing = true
        },
        modifier = modifier
            .width(500.dp)
            .height(height.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.055f), contentColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Text(label, color = NexoraVioletSoft, fontSize = 11.sp, fontWeight = FontWeight.Black, maxLines = 1)
            Text(displayValue, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun SetupModeButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.height(42.dp), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = if (selected) NexoraViolet else Color.White.copy(alpha = 0.08f), contentColor = Color.White)) {
        Text(text, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Black else FontWeight.Medium, maxLines = 1)
    }
}

@Composable
private fun StatusBox(text: String) {
    Box(modifier = Modifier.width(500.dp).background(PanelSoft, RoundedCornerShape(20.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(14.dp)) {
        Text(text, color = Color.White.copy(alpha = 0.76f), fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun EarlyAccessPanel() {
    Column(
        modifier = Modifier
            .width(650.dp)
            .height(594.dp)
            .background(PanelDark, RoundedCornerShape(30.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Early Access Commitment", color = Color.White, fontSize = 31.sp, fontWeight = FontWeight.Black)
        Text("Nexora TV is being built as a premium TV player ecosystem.", color = NexoraVioletSoft, fontSize = 15.sp, fontWeight = FontWeight.Bold)

        NoticeCard("Free during early access", "The application remains free while the complete ecosystem is being built and tested.")
        NoticeCard("Player, not a provider", "Nexora TV does not sell channels, subscriptions, accounts, playlists, or media access.")
        NoticeCard("Legal use only", "Users must enter only media access they are legally authorized to use.")
        NoticeCard("No advertising sales", "The app does not sell ad inventory or place third-party advertising in this early access phase.")
        NoticeCard("Privacy-first direction", "The setup flow is designed to keep media source details local to the user experience wherever possible.")

        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            InfoPill("TV-friendly")
            InfoPill("Remote-first")
            InfoPill("Early access")
        }
    }
}

@Composable
private fun NoticeCard(title: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Black)
        Text(body, color = Color.White.copy(alpha = 0.68f), fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun InfoPill(text: String) {
    Box(modifier = Modifier.background(NexoraBlue.copy(alpha = 0.14f), RoundedCornerShape(14.dp)).border(1.dp, NexoraBlue.copy(alpha = 0.35f), RoundedCornerShape(14.dp)).padding(horizontal = 12.dp, vertical = 8.dp), contentAlignment = Alignment.Center) {
        Text(text, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
    }
}

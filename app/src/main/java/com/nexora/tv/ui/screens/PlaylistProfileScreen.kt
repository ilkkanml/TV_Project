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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.data.live.RemoteListLoader
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraGreen = Color(0xFF39FF88)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xAA11131C)

@Composable
fun PlaylistProfileScreen(navController: NavController) {
    var listAddress by remember { mutableStateOf("") }
    var channels by remember { mutableStateOf<List<LiveChannel>>(emptyList()) }
    var selectedGroup by remember { mutableStateOf("All") }
    var isLoading by remember { mutableStateOf(false) }
    var statusText by remember { mutableStateOf("Enter your own list address, then load channels.") }

    val groups = remember(channels) {
        listOf("All") + channels.map { it.group.ifBlank { "Live" } }.distinct().take(24)
    }

    val visibleChannels = remember(channels, selectedGroup) {
        if (selectedGroup == "All") channels else channels.filter { it.group == selectedGroup }
    }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 34.dp, vertical = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(430.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "NEXORA",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.4.sp,
                    maxLines = 1
                )

                Text(
                    text = "Live List Setup",
                    color = NexoraVioletSoft,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1
                )

                Text(
                    text = "Use your own active list address. The app does not provide channels.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                OutlinedTextField(
                    value = listAddress,
                    onValueChange = { listAddress = it },
                    label = { Text("List URL") },
                    modifier = Modifier.width(430.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NexoraViolet,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                        focusedLabelColor = NexoraVioletSoft,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.50f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = NexoraVioletSoft,
                        focusedContainerColor = Color.White.copy(alpha = 0.03f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.02f)
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            if (isLoading) return@Button

                            isLoading = true
                            statusText = "Loading channels..."

                            Thread {
                                val result = runCatching {
                                    RemoteListLoader.load(listAddress)
                                }

                                Handler(Looper.getMainLooper()).post {
                                    isLoading = false
                                    result
                                        .onSuccess { loaded ->
                                            channels = loaded
                                            selectedGroup = "All"
                                            statusText = if (loaded.isEmpty()) {
                                                "No channels found."
                                            } else {
                                                "Loaded ${loaded.size} channels."
                                            }
                                        }
                                        .onFailure { error ->
                                            channels = emptyList()
                                            statusText = error.message ?: "Could not load channels."
                                        }
                                }
                            }.start()
                        },
                        modifier = Modifier.width(174.dp).height(46.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexoraViolet,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (isLoading) "Loading" else "Load Channels",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = {
                            listAddress = ""
                            channels = emptyList()
                            selectedGroup = "All"
                            statusText = "Enter your own list address, then load channels."
                        },
                        modifier = Modifier.width(110.dp).height(46.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.08f),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Clear", fontSize = 12.sp)
                    }
                }

                Box(
                    modifier = Modifier
                        .width(430.dp)
                        .background(PanelSoft, RoundedCornerShape(20.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
                        .padding(14.dp)
                ) {
                    Text(
                        text = statusText,
                        color = Color.White.copy(alpha = 0.76f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }

                if (groups.isNotEmpty()) {
                    Text(
                        text = "Groups",
                        color = NexoraVioletSoft,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black
                    )

                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        groups.forEach { group ->
                            GroupButton(
                                text = group,
                                selected = selectedGroup == group,
                                onClick = { selectedGroup = group }
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .width(710.dp)
                    .height(590.dp)
                    .background(PanelDark, RoundedCornerShape(30.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp))
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.width(660.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Channels",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "${visibleChannels.size} visible",
                            color = Color.White.copy(alpha = 0.56f),
                            fontSize = 12.sp
                        )
                    }

                    StatusPill(if (channels.isEmpty()) "EMPTY" else "READY")
                }

                Column(
                    modifier = Modifier
                        .width(660.dp)
                        .height(500.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (visibleChannels.isEmpty()) {
                        EmptyListHint()
                    } else {
                        visibleChannels.forEach { channel ->
                            ChannelRow(
                                channel = channel,
                                onClick = {
                                    LivePlaybackSession.select(channel)
                                    navController.navigate(AppDestinations.Player.route) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(38.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) NexoraViolet else Color.White.copy(alpha = 0.08f),
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Black else FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ChannelRow(
    channel: LiveChannel,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(660.dp)
            .height(62.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.06f),
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.width(620.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(470.dp)) {
                Text(
                    text = channel.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = channel.group,
                    color = Color.White.copy(alpha = 0.52f),
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = "Play",
                color = NexoraGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
private fun StatusPill(text: String) {
    Box(
        modifier = Modifier
            .width(86.dp)
            .height(36.dp)
            .background(NexoraViolet.copy(alpha = 0.18f), RoundedCornerShape(14.dp))
            .border(1.dp, NexoraViolet.copy(alpha = 0.44f), RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun EmptyListHint() {
    Column(
        modifier = Modifier
            .width(660.dp)
            .height(180.dp)
            .background(Color.White.copy(alpha = 0.04f), RoundedCornerShape(24.dp))
            .border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(24.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No channels loaded",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Load a list first, then select a channel to play.",
            color = Color.White.copy(alpha = 0.62f),
            fontSize = 13.sp
        )
    }
}

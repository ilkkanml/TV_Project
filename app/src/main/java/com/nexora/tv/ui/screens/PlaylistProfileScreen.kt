package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xAA11131C)

@Composable
fun PlaylistProfileScreen(navController: NavController) {
    var profileName by remember { mutableStateOf("") }
    var playlistName by remember { mutableStateOf("") }
    var serverUrl by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Portal / Xtream") }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 58.dp, vertical = 42.dp),
            horizontalArrangement = Arrangement.spacedBy(34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(500.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Text(
                    text = "NEXORA",
                    color = Color.White,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "Playlist & Profile Setup",
                    color = NexoraVioletSoft,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "Add a local profile shell for playlist access you own, license, or are legally authorized to use.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 15.sp,
                    lineHeight = 21.sp
                )

                LegalPanel()

                SetupField(
                    label = "Profile Name",
                    value = profileName,
                    onChange = {
                        profileName = it
                    }
                )

                SetupField(
                    label = "Playlist Name",
                    value = playlistName,
                    onChange = {
                        playlistName = it
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TypeButton(
                        text = "Portal / Xtream",
                        selected = selectedType == "Portal / Xtream",
                        onClick = {
                            selectedType = "Portal / Xtream"
                        }
                    )

                    TypeButton(
                        text = "M3U URL",
                        selected = selectedType == "M3U URL",
                        onClick = {
                            selectedType = "M3U URL"
                        }
                    )
                }

                SetupField(
                    label = if (selectedType == "M3U URL") {
                        "M3U URL Placeholder"
                    } else {
                        "Server / Portal URL"
                    },
                    value = serverUrl,
                    onChange = {
                        serverUrl = it
                    }
                )

                SetupField(
                    label = "Username Placeholder",
                    value = username,
                    onChange = {
                        username = it
                    }
                )

                SetupField(
                    label = "Password Placeholder",
                    value = password,
                    isPassword = true,
                    onChange = {
                        password = it
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate(AppDestinations.Home.route) {
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexoraViolet,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Continue Home",
                            fontWeight = FontWeight.Black
                        )
                    }

                    Button(
                        onClick = {
                            profileName = ""
                            playlistName = ""
                            serverUrl = ""
                            username = ""
                            password = ""
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.08f),
                            contentColor = Color.White.copy(alpha = 0.78f)
                        )
                    ) {
                        Text("Clear")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .width(650.dp)
                    .background(
                        color = PanelDark,
                        shape = RoundedCornerShape(34.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(34.dp)
                    )
                    .padding(28.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "Local Setup Preview",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "No playlist is fetched here. No credentials are validated. This is only a premium UI shell for first-run setup.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 15.sp,
                    lineHeight = 21.sp
                )

                PreviewCard(
                    title = "Source Type",
                    body = selectedType
                )

                PreviewCard(
                    title = "Profile",
                    body = profileName.ifBlank { "Not set" }
                )

                PreviewCard(
                    title = "Playlist",
                    body = playlistName.ifBlank { "Not set" }
                )

                PreviewCard(
                    title = "Endpoint",
                    body = serverUrl.ifBlank { "Waiting for legal source" }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    StatusPill("Licensed")
                    StatusPill("Local Shell")
                    StatusPill("No Backend")
                }

                Box(
                    modifier = Modifier
                        .width(590.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.06f),
                            shape = RoundedCornerShape(22.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = NexoraViolet.copy(alpha = 0.36f),
                            shape = RoundedCornerShape(22.dp)
                        )
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Nexora does not provide channels, playlists, hidden sources, scraped streams, or unauthorized provider access.",
                        color = Color.White.copy(alpha = 0.76f),
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SetupField(
    label: String,
    value: String,
    isPassword: Boolean = false,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = {
            Text(label)
        },
        modifier = Modifier.width(500.dp),
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            androidx.compose.ui.text.input.VisualTransformation.None
        },
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
}

@Composable
private fun TypeButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(244.dp)
            .height(52.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                NexoraViolet
            } else {
                Color.White.copy(alpha = 0.07f)
            },
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontWeight = if (selected) {
                FontWeight.Black
            } else {
                FontWeight.Medium
            }
        )
    }
}

@Composable
private fun LegalPanel() {
    Column(
        modifier = Modifier
            .width(500.dp)
            .background(
                color = PanelSoft,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = NexoraViolet.copy(alpha = 0.42f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = "Licensed access only",
            color = NexoraVioletSoft,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = "Use only legal playlists or provider credentials that you are authorized to access.",
            color = Color.White.copy(alpha = 0.82f),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun PreviewCard(
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .width(590.dp)
            .background(
                color = Color.White.copy(alpha = 0.06f),
                shape = RoundedCornerShape(22.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.10f),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            color = NexoraVioletSoft,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = body,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun StatusPill(text: String) {
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(44.dp)
            .background(
                color = NexoraViolet.copy(alpha = 0.20f),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = NexoraViolet.copy(alpha = 0.55f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
        )
    }
}
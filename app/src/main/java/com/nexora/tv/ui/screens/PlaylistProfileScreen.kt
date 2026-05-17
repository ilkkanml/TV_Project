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
import com.nexora.tv.data.playlist.LocalProfileRepository
import com.nexora.tv.data.playlist.PlaylistProfileDraft
import com.nexora.tv.data.playlist.PlaylistProfileShellState
import com.nexora.tv.data.playlist.PlaylistProfileValidationResult
import com.nexora.tv.data.playlist.PlaylistSourceType
import com.nexora.tv.data.playlist.SavedLocalProfileShell
import com.nexora.tv.data.playlist.validateForLocalShell
import com.nexora.tv.navigation.AppDestinations

@Composable
fun PlaylistProfileScreen(navController: NavController) {
    val repository = remember { LocalProfileRepository() }
    var savedProfiles by remember { mutableStateOf<List<SavedLocalProfileShell>>(emptyList()) }
    var selectedProfileId by remember { mutableStateOf<String?>(null) }
    var editingProfileId by remember { mutableStateOf<String?>(null) }

    var profileName by remember { mutableStateOf("") }
    var sourceType by remember { mutableStateOf(PlaylistSourceType.M3uUrl) }
    var m3uUrl by remember { mutableStateOf("") }
    var xtreamServerUrl by remember { mutableStateOf("") }
    var xtreamUsername by remember { mutableStateOf("") }
    var xtreamPassword by remember { mutableStateOf("") }
    var result by remember {
        mutableStateOf(
            PlaylistProfileValidationResult(
                state = PlaylistProfileShellState.Empty,
                title = "No profile saved",
                message = "Create a local shell using only playlist access you legally own or are authorized to use."
            )
        )
    }

    val draft = PlaylistProfileDraft(
        profileName = profileName,
        sourceType = sourceType,
        m3uUrl = m3uUrl,
        xtreamServerUrl = xtreamServerUrl,
        xtreamUsername = xtreamUsername,
        xtreamPassword = xtreamPassword
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06111D))
            .padding(horizontal = 56.dp, vertical = 44.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .width(470.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "NEXORA",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = if (editingProfileId == null) "Playlist Profile" else "Edit Profile Shell",
                color = Color(0xFF00E5FF),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Legal input shell only. Nexora does not provide channels, playlists, credentials, or hidden sources.",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            LegalNoticeCard()

            OutlinedTextField(
                value = profileName,
                onValueChange = {
                    profileName = it
                    result = emptyResult()
                },
                label = { Text("Profile Name") },
                modifier = Modifier.width(470.dp),
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SourceTypeButton(
                    sourceType = PlaylistSourceType.M3uUrl,
                    selected = sourceType == PlaylistSourceType.M3uUrl,
                    onSelected = {
                        sourceType = PlaylistSourceType.M3uUrl
                        result = emptyResult()
                    }
                )
                SourceTypeButton(
                    sourceType = PlaylistSourceType.XtreamCodes,
                    selected = sourceType == PlaylistSourceType.XtreamCodes,
                    onSelected = {
                        sourceType = PlaylistSourceType.XtreamCodes
                        result = emptyResult()
                    }
                )
            }

            if (sourceType == PlaylistSourceType.M3uUrl) {
                OutlinedTextField(
                    value = m3uUrl,
                    onValueChange = {
                        m3uUrl = it
                        result = emptyResult()
                    },
                    label = { Text("M3U URL") },
                    modifier = Modifier.width(470.dp),
                    singleLine = true
                )
            } else {
                OutlinedTextField(
                    value = xtreamServerUrl,
                    onValueChange = {
                        xtreamServerUrl = it
                        result = emptyResult()
                    },
                    label = { Text("Server URL") },
                    modifier = Modifier.width(470.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = xtreamUsername,
                    onValueChange = {
                        xtreamUsername = it
                        result = emptyResult()
                    },
                    label = { Text("Username") },
                    modifier = Modifier.width(470.dp),
                    singleLine = true
                )
                OutlinedTextField(
                    value = xtreamPassword,
                    onValueChange = {
                        xtreamPassword = it
                        result = emptyResult()
                    },
                    label = { Text("Password") },
                    modifier = Modifier.width(470.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        val validation = draft.validateForLocalShell()
                        result = validation

                        if (validation.state == PlaylistProfileShellState.Saved) {
                            val nextProfiles = repository.upsertShell(
                                currentProfiles = savedProfiles,
                                editingProfileId = editingProfileId,
                                draft = draft
                            )
                            savedProfiles = nextProfiles
                            selectedProfileId = nextProfiles.firstOrNull { it.isActive }?.id
                            editingProfileId = null
                            result = validation.copy(
                                message = "${validation.message} Sensitive values are not persisted; only a safe local shell summary is kept."
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF123A46),
                        contentColor = Color.White
                    )
                ) {
                    Text(if (editingProfileId == null) "Save Local Shell" else "Update Shell")
                }

                Button(
                    onClick = {
                        profileName = ""
                        sourceType = PlaylistSourceType.M3uUrl
                        m3uUrl = ""
                        xtreamServerUrl = ""
                        xtreamUsername = ""
                        xtreamPassword = ""
                        editingProfileId = null
                        result = emptyResult()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF141821),
                        contentColor = Color.LightGray
                    )
                ) {
                    Text("Clear")
                }

                Button(
                    onClick = {
                        navController.navigate(AppDestinations.Home.route) {
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF141821),
                        contentColor = Color.White
                    )
                ) {
                    Text("Back")
                }
            }
        }

        Column(
            modifier = Modifier
                .width(620.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusCard(result)

            SavedProfilesShell(
                profiles = savedProfiles,
                selectedProfileId = selectedProfileId,
                onSelect = { profile ->
                    val nextProfiles = repository.selectActive(savedProfiles, profile.id)
                    savedProfiles = nextProfiles
                    selectedProfileId = profile.id
                    result = PlaylistProfileValidationResult(
                        state = PlaylistProfileShellState.Saved,
                        title = "Active profile selected",
                        message = "${profile.profileName} is active locally. No provider connection was made."
                    )
                },
                onEdit = { profile ->
                    profileName = profile.profileName
                    sourceType = profile.sourceType
                    m3uUrl = ""
                    xtreamServerUrl = ""
                    xtreamUsername = ""
                    xtreamPassword = ""
                    editingProfileId = profile.id
                    selectedProfileId = profile.id
                    result = PlaylistProfileValidationResult(
                        state = PlaylistProfileShellState.Empty,
                        title = "Editing safe profile shell",
                        message = "Sensitive source fields are intentionally blank. Re-enter authorized values to update this local shell."
                    )
                },
                onDelete = { profile ->
                    val nextProfiles = repository.deleteShell(savedProfiles, profile.id)
                    savedProfiles = nextProfiles
                    selectedProfileId = nextProfiles.firstOrNull { it.isActive }?.id
                    if (editingProfileId == profile.id) {
                        editingProfileId = null
                        profileName = ""
                        m3uUrl = ""
                        xtreamServerUrl = ""
                        xtreamUsername = ""
                        xtreamPassword = ""
                    }
                    result = PlaylistProfileValidationResult(
                        state = PlaylistProfileShellState.Empty,
                        title = "Profile shell deleted",
                        message = "Deleted the local in-memory shell. No remote data or provider source was touched."
                    )
                }
            )

            SourceSummaryCard(
                title = sourceType.label,
                body = sourceType.description,
                accent = if (sourceType == PlaylistSourceType.M3uUrl) Color(0xFF00E5FF) else Color(0xFF2962FF)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                SmallProfileStateCard("Empty", "Waiting input", Color(0xFF7C4DFF))
                SmallProfileStateCard("Invalid", "Safe validation", Color(0xFFFF6D00))
                SmallProfileStateCard("Saved", "Local shell only", Color(0xFF00BFA5))
            }

            SourceSummaryCard(
                title = "Compliance Boundary",
                body = "This screen does not fetch playlists, test credentials, connect providers, store cloud data, or modify playback/auth systems.",
                accent = Color(0xFF00BFA5)
            )
        }
    }
}

private fun emptyResult(): PlaylistProfileValidationResult = PlaylistProfileValidationResult(
    state = PlaylistProfileShellState.Empty,
    title = "No profile saved",
    message = "Local form state only. Nothing has been connected or uploaded."
)

@Composable
private fun SourceTypeButton(
    sourceType: PlaylistSourceType,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Button(
        onClick = onSelected,
        modifier = Modifier
            .width(228.dp)
            .height(54.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF123A46) else Color(0xFF141821),
            contentColor = if (selected) Color.White else Color.LightGray
        )
    ) {
        Text(
            text = sourceType.label,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun LegalNoticeCard() {
    Column(
        modifier = Modifier
            .width(470.dp)
            .background(Color(0xFF111722), RoundedCornerShape(22.dp))
            .border(1.dp, Color(0xFF00E5FF), RoundedCornerShape(22.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Legal Ownership Notice",
            color = Color(0xFF00E5FF),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Only enter playlist access that you own, license, or are legally authorized to use.",
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun StatusCard(result: PlaylistProfileValidationResult) {
    val accent = when (result.state) {
        PlaylistProfileShellState.Empty -> Color(0xFF7C4DFF)
        PlaylistProfileShellState.Invalid -> Color(0xFFFF6D00)
        PlaylistProfileShellState.Saved -> Color(0xFF00BFA5)
    }

    Column(
        modifier = Modifier
            .width(620.dp)
            .height(220.dp)
            .background(Color(0xFF111722), RoundedCornerShape(28.dp))
            .border(1.dp, accent, RoundedCornerShape(28.dp))
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = result.title,
            color = accent,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = result.message,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "State: ${result.state.name}",
            color = Color.LightGray,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun SavedProfilesShell(
    profiles: List<SavedLocalProfileShell>,
    selectedProfileId: String?,
    onSelect: (SavedLocalProfileShell) -> Unit,
    onEdit: (SavedLocalProfileShell) -> Unit,
    onDelete: (SavedLocalProfileShell) -> Unit
) {
    Column(
        modifier = Modifier
            .width(620.dp)
            .background(Color(0xFF111722), RoundedCornerShape(24.dp))
            .border(1.dp, Color(0xFF00E5FF), RoundedCornerShape(24.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Saved Profiles Shell",
            color = Color(0xFF00E5FF),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Local in-memory list only. Source URLs and credentials are not persisted in this shell.",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        if (profiles.isEmpty()) {
            SmallProfileStateCard(
                title = "No saved shell",
                body = "Save a valid local profile shell to see it here.",
                accent = Color(0xFF7C4DFF)
            )
        } else {
            profiles.forEach { profile ->
                SavedProfileCard(
                    profile = profile,
                    selected = selectedProfileId == profile.id,
                    onSelect = { onSelect(profile) },
                    onEdit = { onEdit(profile) },
                    onDelete = { onDelete(profile) }
                )
            }
        }
    }
}

@Composable
private fun SavedProfileCard(
    profile: SavedLocalProfileShell,
    selected: Boolean,
    onSelect: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val accent = if (profile.isActive || selected) Color(0xFF00BFA5) else Color(0xFF2962FF)

    Column(
        modifier = Modifier
            .width(584.dp)
            .background(Color(0xFF151A24), RoundedCornerShape(20.dp))
            .border(1.dp, accent, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = profile.profileName,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${profile.sourceType.label} • ${profile.sourceSummary}",
            color = Color.LightGray,
            fontSize = 13.sp
        )
        Text(
            text = if (profile.isActive) "Active local shell" else "Saved local shell",
            color = accent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = onSelect,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF123A46),
                    contentColor = Color.White
                )
            ) {
                Text("Select")
            }
            Button(
                onClick = onEdit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF141821),
                    contentColor = Color.LightGray
                )
            ) {
                Text("Edit")
            }
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF241206),
                    contentColor = Color.White
                )
            ) {
                Text("Delete")
            }
        }
    }
}

@Composable
private fun SourceSummaryCard(title: String, body: String, accent: Color) {
    Column(
        modifier = Modifier
            .width(620.dp)
            .background(Color(0xFF111722), RoundedCornerShape(22.dp))
            .border(1.dp, accent, RoundedCornerShape(22.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            color = accent,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = body,
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun SmallProfileStateCard(title: String, body: String, accent: Color) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .height(94.dp)
            .background(Color(0xFF111722), RoundedCornerShape(20.dp))
            .border(1.dp, accent, RoundedCornerShape(20.dp))
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                color = accent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = body,
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}

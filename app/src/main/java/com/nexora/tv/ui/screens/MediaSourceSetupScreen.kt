package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.profile.MediaProfile
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val setupViolet = Color(0xFF7C3AED)
private val setupVioletSoft = Color(0xFF9F67FF)
private val setupBlue = Color(0xFF4CC9FF)
private val setupGreen = Color(0xFF39FF88)
private val setupPanel = Color(0xCC090B12)
private val setupField = Color(0xAA121624)

internal enum class SourceMode {
    Portal,
    ListUrl,
    LocalFile,
    Single
}

@Composable
fun MediaSourceSetupScreen(navController: NavController) {
    val context = LocalContext.current
    MediaProfileStore.init(context)

    val editingProfile = MediaProfileStore.editingProfile
    var mode by remember(editingProfile?.id) { mutableStateOf(initialMode(editingProfile)) }
    var profileName by remember(editingProfile?.id) { mutableStateOf(editingProfile?.profileName.orEmpty()) }
    var server by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Provider API", FieldTarget.Server)) }
    var user by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Provider API", FieldTarget.User)) }
    var secret by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Provider API", FieldTarget.Secret)) }
    var listUrl by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("M3U URL", FieldTarget.Server)) }
    var localText by remember(editingProfile?.id) { mutableStateOf("") }
    var singleUrl by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Single stream", FieldTarget.Server)) }
    var loading by remember { mutableStateOf(false) }
    var message by remember(editingProfile?.id) {
        mutableStateOf(editingProfile?.status ?: AppLanguageStore.t("Create a profile and connect a media source.", "Profil oluştur ve medya kaynağını bağla."))
    }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(500.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                Text("NEXORA", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Black, letterSpacing = 2.3.sp, maxLines = 1)
                Text(
                    if (editingProfile == null) AppLanguageStore.t("Create Profile", "Profil Oluştur") else AppLanguageStore.t("Edit Profile", "Profili Düzenle"),
                    color = setupVioletSoft,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1
                )
                Text(
                    AppLanguageStore.t("Select a source type and connect.", "Kaynak tipini seç ve bağlan."),
                    color = Color.White.copy(alpha = 0.62f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                SourceModeSelector(mode) { selected ->
                    mode = selected
                    message = sourceModeMessage(selected)
                }

                SetupInputField(AppLanguageStore.t("Profile name", "Profil adı"), profileName) { profileName = it }

                when (mode) {
                    SourceMode.Portal -> {
                        SetupInputField(AppLanguageStore.t("Server URL", "Sunucu URL"), server) { server = it.noSpaces() }
                        SetupInputField(AppLanguageStore.t("User name", "Kullanıcı adı"), user) { user = it.noSpaces() }
                        SetupInputField(AppLanguageStore.t("Access key", "Erişim anahtarı"), secret, secret = true) { secret = it.noSpaces() }
                    }
                    SourceMode.ListUrl -> {
                        SetupInputField("M3U URL", listUrl) { listUrl = it.noSpaces() }
                    }
                    SourceMode.LocalFile -> {
                        SetupInputField(AppLanguageStore.t("Paste list data", "Liste verisini yapıştır"), localText, singleLine = false, height = 110) { localText = it }
                    }
                    SourceMode.Single -> {
                        SetupInputField(AppLanguageStore.t("Stream URL", "Yayın URL"), singleUrl) { singleUrl = it.noSpaces() }
                    }
                }

                SetupActionRow(
                    loading = loading,
                    mode = mode,
                    onConnect = {
                        if (!loading) {
                            connectCurrentSource(
                                context = context,
                                mode = mode,
                                profileName = profileName,
                                server = server,
                                user = user,
                                pass = secret,
                                listUrl = listUrl,
                                localText = localText,
                                singleUrl = singleUrl,
                                navController = navController,
                                setLoading = { loading = it },
                                setMessage = { message = it }
                            )
                        }
                    },
                    onClear = {
                        profileName = ""
                        server = ""
                        user = ""
                        secret = ""
                        listUrl = ""
                        localText = ""
                        singleUrl = ""
                        message = AppLanguageStore.t("Form cleared.", "Form temizlendi.")
                    },
                    onBack = { navController.popBackStack() }
                )

                SetupStatusBox(message)
            }

            SetupInfoPanel()
        }
    }
}

@Composable
private fun SourceModeSelector(selected: SourceMode, onSelected: (SourceMode) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SourceModeButton("Provider API", selected == SourceMode.Portal) { onSelected(SourceMode.Portal) }
        SourceModeButton("M3U", selected == SourceMode.ListUrl) { onSelected(SourceMode.ListUrl) }
        SourceModeButton("Local", selected == SourceMode.LocalFile) { onSelected(SourceMode.LocalFile) }
        SourceModeButton("Single", selected == SourceMode.Single) { onSelected(SourceMode.Single) }
    }
}

@Composable
private fun SourceModeButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(118.dp).height(44.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) setupViolet else Color.White.copy(alpha = 0.08f),
            contentColor = Color.White
        )
    ) {
        Text(text, fontSize = 11.sp, fontWeight = FontWeight.Black, maxLines = 1)
    }
}

@Composable
private fun SetupInputField(
    label: String,
    value: String,
    secret: Boolean = false,
    singleLine: Boolean = true,
    height: Int = 56,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = singleLine,
        visualTransformation = if (secret) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.width(500.dp).height(height.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = setupBlue,
            unfocusedBorderColor = Color.White.copy(alpha = 0.18f),
            focusedLabelColor = setupBlue,
            unfocusedLabelColor = Color.White.copy(alpha = 0.56f),
            focusedContainerColor = setupField,
            unfocusedContainerColor = setupField
        )
    )
}

@Composable
private fun SetupActionRow(loading: Boolean, mode: SourceMode, onConnect: () -> Unit, onClear: () -> Unit, onBack: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(onClick = onConnect, modifier = Modifier.width(174.dp).height(52.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = setupViolet, contentColor = Color.White)) {
            Text(
                text = when {
                    loading -> AppLanguageStore.t("Loading", "Yükleniyor")
                    mode == SourceMode.Single -> AppLanguageStore.t("Play Stream", "Yayını Aç")
                    else -> AppLanguageStore.t("Connect", "Bağlan")
                },
                fontWeight = FontWeight.Black,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(onClick = onClear, modifier = Modifier.width(102.dp).height(52.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)) {
            Text(AppLanguageStore.t("Clear", "Temizle"), fontSize = 12.sp)
        }
        Button(onClick = onBack, modifier = Modifier.width(102.dp).height(52.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)) {
            Text(AppLanguageStore.t("Back", "Geri"), fontSize = 12.sp)
        }
    }
}

@Composable
private fun SetupStatusBox(message: String) {
    Column(
        modifier = Modifier.width(500.dp).background(setupPanel, RoundedCornerShape(18.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(18.dp)).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(AppLanguageStore.t("Status", "Durum"), color = setupVioletSoft, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Text(message, color = Color.White.copy(alpha = 0.72f), fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun SetupInfoPanel() {
    Column(
        modifier = Modifier.width(520.dp).background(setupPanel, RoundedCornerShape(30.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp)).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(AppLanguageStore.t("Early Access", "Erken Erişim"), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.t("Free preview build", "Ücretsiz ön izleme sürümü"), color = setupGreen, fontSize = 14.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.t("Use your own media access.", "Kendi medya erişimini kullan."), color = Color.White.copy(alpha = 0.72f), fontSize = 13.sp, lineHeight = 20.sp)
        Text(AppLanguageStore.t("Credentials stay local for this test build.", "Bu test sürümünde bilgiler yerelde kalır."), color = Color.White.copy(alpha = 0.62f), fontSize = 12.sp, lineHeight = 18.sp)
    }
}

private enum class FieldTarget { Server, User, Secret }

private fun MediaProfile?.fieldFor(sourceType: String, target: FieldTarget): String {
    val profile = this?.takeIf { it.sourceType == sourceType } ?: return ""
    return when (target) {
        FieldTarget.Server -> profile.serverAddress
        FieldTarget.User -> profile.accountName
        FieldTarget.Secret -> profile.accessKey
    }
}

private fun initialMode(profile: MediaProfile?): SourceMode {
    return when (profile?.sourceType) {
        "M3U URL" -> SourceMode.ListUrl
        "Local data" -> SourceMode.LocalFile
        "Single stream" -> SourceMode.Single
        else -> SourceMode.Portal
    }
}

private fun sourceModeMessage(mode: SourceMode): String {
    return when (mode) {
        SourceMode.Portal -> AppLanguageStore.t("Provider API selected.", "Provider API seçildi.")
        SourceMode.ListUrl -> AppLanguageStore.t("M3U URL selected.", "M3U URL seçildi.")
        SourceMode.LocalFile -> AppLanguageStore.t("Local data selected.", "Yerel veri seçildi.")
        SourceMode.Single -> AppLanguageStore.t("Single stream selected.", "Tek yayın seçildi.")
    }
}

private fun String.noSpaces(): String = filterNot { it.isWhitespace() }

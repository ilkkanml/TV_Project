package com.nexora.tv.ui.screens

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.data.live.M3uParser
import com.nexora.tv.data.live.ProviderPortalLoader
import com.nexora.tv.data.live.RemoteListLoader
import com.nexora.tv.data.profile.MediaProfile
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val SetupViolet = Color(0xFF7C3AED)
private val SetupVioletSoft = Color(0xFF9F67FF)
private val SetupBlue = Color(0xFF4CC9FF)
private val SetupGreen = Color(0xFF39FF88)
private val SetupPanel = Color(0xCC090B12)
private val SetupField = Color(0xAA121624)

private enum class SourceMode {
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
    val portalFocus = remember { FocusRequester() }
    val profileFocus = remember { FocusRequester() }
    val serverFocus = remember { FocusRequester() }
    val userFocus = remember { FocusRequester() }
    val passFocus = remember { FocusRequester() }
    val listFocus = remember { FocusRequester() }
    val localFocus = remember { FocusRequester() }
    val singleFocus = remember { FocusRequester() }
    val connectFocus = remember { FocusRequester() }

    var mode by remember(editingProfile?.id) { mutableStateOf(initialMode(editingProfile)) }
    var activeFieldId by remember { mutableStateOf<String?>(null) }
    var profileName by remember(editingProfile?.id) { mutableStateOf(editingProfile?.profileName.orEmpty()) }
    var server by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Provider API", FieldTarget.Server)) }
    var user by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Provider API", FieldTarget.User)) }
    var pass by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Provider API", FieldTarget.Secret)) }
    var listUrl by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("M3U URL", FieldTarget.Server)) }
    var localText by remember(editingProfile?.id) { mutableStateOf("") }
    var singleUrl by remember(editingProfile?.id) { mutableStateOf(editingProfile.fieldFor("Single stream", FieldTarget.Server)) }
    var loading by remember { mutableStateOf(false) }
    var showClear by remember { mutableStateOf(false) }
    var message by remember(editingProfile?.id) {
        mutableStateOf(editingProfile?.status ?: AppLanguageStore.t("Create a profile and connect a personal media source.", "Profil oluştur ve kişisel medya kaynağını bağla."))
    }

    val firstFieldFocus = when (mode) {
        SourceMode.Portal -> serverFocus
        SourceMode.ListUrl -> listFocus
        SourceMode.LocalFile -> localFocus
        SourceMode.Single -> singleFocus
    }
    val firstFieldId = when (mode) {
        SourceMode.Portal -> "server"
        SourceMode.ListUrl -> "list"
        SourceMode.LocalFile -> "local"
        SourceMode.Single -> "single"
    }

    val filePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }.orEmpty()
            }.onSuccess {
                mode = SourceMode.LocalFile
                localText = it
                activeFieldId = null
                message = AppLanguageStore.t("Local file loaded. Press Connect to continue.", "Yerel dosya yüklendi. Devam etmek için Bağlan'a bas.")
                connectFocus.requestFocus()
            }.onFailure {
                message = AppLanguageStore.t("Could not read selected file.", "Seçilen dosya okunamadı.")
            }
        }
    }

    if (showClear) {
        ClearFormDialog(
            onDismiss = { showClear = false },
            onConfirm = {
                profileName = ""
                server = ""
                user = ""
                pass = ""
                listUrl = ""
                localText = ""
                singleUrl = ""
                activeFieldId = null
                message = AppLanguageStore.t("Create a profile and connect a personal media source.", "Profil oluştur ve kişisel medya kaynağını bağla.")
                showClear = false
            }
        )
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
                    color = SetupVioletSoft,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 1
                )
                Text(
                    AppLanguageStore.t(
                        "Focus selects a field. Press OK/Enter to edit. Use Next/Enter to continue.",
                        "Focus kutuyu seçer. Düzenlemek için OK/Enter'a bas. Devam etmek için Next/Enter kullan."
                    ),
                    color = Color.White.copy(alpha = 0.62f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                SourceModeSelector(
                    selected = mode,
                    portalFocus = portalFocus,
                    onSelected = { selected ->
                        mode = selected
                        activeFieldId = null
                        message = sourceModeMessage(selected)
                    }
                )

                SetupInputField(
                    fieldId = "profile",
                    activeFieldId = activeFieldId,
                    onActiveFieldChange = { activeFieldId = it },
                    label = AppLanguageStore.t("Profile name", "Profil adı"),
                    value = profileName,
                    onChange = { profileName = it },
                    focusRequester = profileFocus,
                    nextFocusRequester = firstFieldFocus,
                    nextFieldId = firstFieldId,
                    modifier = Modifier.focusProperties { up = portalFocus }
                )

                when (mode) {
                    SourceMode.Portal -> {
                        SetupInputField("server", activeFieldId, { activeFieldId = it }, AppLanguageStore.t("Server URL", "Sunucu URL"), server, { server = it.filterNot(Char::isWhitespace) }, serverFocus, userFocus, "user", modifier = Modifier.focusProperties { up = profileFocus })
                        SetupInputField("user", activeFieldId, { activeFieldId = it }, AppLanguageStore.t("User name", "Kullanıcı adı"), user, { user = it.filterNot(Char::isWhitespace) }, userFocus, passFocus, "pass")
                        SetupInputField("pass", activeFieldId, { activeFieldId = it }, AppLanguageStore.t("Password", "Şifre"), pass, { pass = it.filterNot(Char::isWhitespace) }, passFocus, connectFocus, null, secret = true, modifier = Modifier.focusProperties { down = connectFocus })
                    }

                    SourceMode.ListUrl -> {
                        SetupInputField("list", activeFieldId, { activeFieldId = it }, "M3U URL", listUrl, { listUrl = it.filterNot(Char::isWhitespace) }, listFocus, connectFocus)
                    }

                    SourceMode.LocalFile -> {
                        Button(
                            onClick = { filePicker.launch("*/*") },
                            modifier = Modifier.width(500.dp).height(52.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                        ) {
                            Text(AppLanguageStore.t("Choose file from device", "Cihazdan dosya seç"), fontWeight = FontWeight.Black, fontSize = 13.sp)
                        }
                        SetupInputField("local", activeFieldId, { activeFieldId = it }, AppLanguageStore.t("Paste list data", "Liste verisini yapıştır"), localText, { localText = it }, localFocus, connectFocus, singleLine = false, height = 96)
                    }

                    SourceMode.Single -> {
                        SetupInputField("single", activeFieldId, { activeFieldId = it }, AppLanguageStore.t("Stream URL", "Yayın URL"), singleUrl, { singleUrl = it.filterNot(Char::isWhitespace) }, singleFocus, connectFocus)
                    }
                }

                SetupActionRow(
                    loading = loading,
                    mode = mode,
                    connectFocus = connectFocus,
                    onConnect = {
                        if (!loading) {
                            activeFieldId = null
                            connectCurrentSource(context, mode, profileName, server, user, pass, listUrl, localText, singleUrl, navController, { loading = it }, { message = it })
                        }
                    },
                    onClear = { showClear = true },
                    onBack = { navController.popBackStack() }
                )

                SetupStatusBox(message)
            }

            MediaSetupSecurityPanel()
        }
    }
}

@Composable
private fun SourceModeSelector(selected: SourceMode, portalFocus: FocusRequester, onSelected: (SourceMode) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        SourceModeButton("Provider API", selected == SourceMode.Portal, Modifier.focusRequester(portalFocus)) { onSelected(SourceMode.Portal) }
        SourceModeButton("M3U", selected == SourceMode.ListUrl) { onSelected(SourceMode.ListUrl) }
        SourceModeButton("Local", selected == SourceMode.LocalFile) { onSelected(SourceMode.LocalFile) }
        SourceModeButton("Single", selected == SourceMode.Single) { onSelected(SourceMode.Single) }
    }
}

@Composable
private fun SourceModeButton(text: String, selected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.width(118.dp).height(44.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (selected) SetupViolet else Color.White.copy(alpha = 0.08f), contentColor = Color.White)
    ) {
        Text(text, fontSize = 11.sp, fontWeight = FontWeight.Black, maxLines = 1)
    }
}

@Composable
private fun SetupInputField(
    fieldId: String,
    activeFieldId: String?,
    onActiveFieldChange: (String?) -> Unit,
    label: String,
    value: String,
    onChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester,
    nextFieldId: String? = null,
    modifier: Modifier = Modifier,
    secret: Boolean = false,
    singleLine: Boolean = true,
    height: Int = 56
) {
    val focusManager = LocalFocusManager.current
    val isActive = activeFieldId == fieldId

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        singleLine = singleLine,
        enabled = true,
        readOnly = !isActive,
        visualTransformation = if (secret) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
            onNext = {
                onActiveFieldChange(nextFieldId)
                nextFocusRequester.requestFocus()
            },
            onDone = {
                onActiveFieldChange(null)
                focusManager.clearFocus()
            }
        ),
        modifier = modifier
            .width(500.dp)
            .height(height.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) onActiveFieldChange(fieldId)
            },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White.copy(alpha = 0.86f),
            focusedBorderColor = SetupBlue,
            unfocusedBorderColor = Color.White.copy(alpha = 0.18f),
            disabledBorderColor = Color.White.copy(alpha = 0.14f),
            focusedLabelColor = SetupBlue,
            unfocusedLabelColor = Color.White.copy(alpha = 0.56f),
            disabledLabelColor = Color.White.copy(alpha = 0.48f),
            focusedContainerColor = SetupField,
            unfocusedContainerColor = SetupField,
            disabledContainerColor = SetupField
        )
    )
}

@Composable
private fun SetupActionRow(loading: Boolean, mode: SourceMode, connectFocus: FocusRequester, onConnect: () -> Unit, onClear: () -> Unit, onBack: () -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(
            onClick = onConnect,
            modifier = Modifier.width(174.dp).height(52.dp).focusRequester(connectFocus),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SetupViolet, contentColor = Color.White)
        ) {
            Text(
                text = if (loading) AppLanguageStore.t("Loading", "Yükleniyor") else if (mode == SourceMode.Single) AppLanguageStore.t("Play Stream", "Yayını Aç") else AppLanguageStore.t("Connect", "Bağlan"),
                fontWeight = FontWeight.Black,
                fontSize = 13.sp
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
        modifier = Modifier.width(500.dp).background(SetupPanel, RoundedCornerShape(18.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(18.dp)).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(AppLanguageStore.t("Status", "Durum"), color = SetupVioletSoft, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Text(message, color = Color.White.copy(alpha = 0.72f), fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun MediaSetupSecurityPanel() {
    Column(
        modifier = Modifier.width(520.dp).background(SetupPanel, RoundedCornerShape(30.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp)).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(AppLanguageStore.t("Security & Early Access", "Güvenlik ve Erken Erişim"), color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.t("Free during early access", "Erken erişimde ücretsiz"), color = SetupGreen, fontSize = 14.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.t("Legal use only", "Sadece yasal kullanım"), color = Color.White.copy(alpha = 0.82f), fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(
            AppLanguageStore.t(
                "Add only licensed media sources or sources you have the legal right to access. Provider, playlist, stream URL, user name and password stay on the device and are not sent to the install database.",
                "Yalnızca lisanslı veya yasal erişim hakkın olan medya kaynaklarını ekle. Provider, playlist, yayın URL, kullanıcı adı ve şifre cihazda kalır; install database'e gönderilmez."
            ),
            color = Color.White.copy(alpha = 0.68f),
            fontSize = 13.sp,
            lineHeight = 20.sp
        )
        Text(
            AppLanguageStore.t(
                "We do not use MAC as the primary device identity. Install registration uses a local installId and a stable platform hash only.",
                "MAC adresini ana cihaz kimliği olarak kullanmıyoruz. Install kaydı yalnızca local installId ve sabit platform hash kullanır."
            ),
            color = Color.White.copy(alpha = 0.62f),
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun ClearFormDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(AppLanguageStore.t("Clear form?", "Form temizlensin mi?")) },
        text = { Text(AppLanguageStore.t("Current form fields will be cleared. Saved profiles will not be deleted.", "Mevcut form alanları temizlenir. Kayıtlı profiller silinmez.")) },
        confirmButton = { TextButton(onClick = onConfirm) { Text(AppLanguageStore.t("Clear", "Temizle")) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(AppLanguageStore.t("Cancel", "İptal")) } }
    )
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
        SourceMode.Portal -> AppLanguageStore.t("Provider API selected. Enter server, user name and password.", "Provider API seçildi. Sunucu, kullanıcı adı ve şifre gir.")
        SourceMode.ListUrl -> AppLanguageStore.t("M3U URL selected. Enter the list URL.", "M3U URL seçildi. Liste adresini gir.")
        SourceMode.LocalFile -> AppLanguageStore.t("Local data selected. Choose a file or paste list text.", "Yerel veri seçildi. Dosya seç veya liste metni yapıştır.")
        SourceMode.Single -> AppLanguageStore.t("Single stream selected. Enter one stream URL.", "Tek yayın seçildi. Bir yayın adresi gir.")
    }
}

private fun connectCurrentSource(
    context: Context,
    mode: SourceMode,
    profileName: String,
    server: String,
    user: String,
    pass: String,
    listUrl: String,
    localText: String,
    singleUrl: String,
    navController: NavController,
    setLoading: (Boolean) -> Unit,
    setMessage: (String) -> Unit
) {
    val safeName = profileName.ifBlank { AppLanguageStore.t("User Profile", "Kullanıcı Profili") }

    when (mode) {
        SourceMode.Portal -> {
            if (server.isBlank() || user.isBlank() || pass.isBlank()) {
                setMessage(AppLanguageStore.t("Server, user name and password are required.", "Sunucu, kullanıcı adı ve şifre zorunludur."))
                return
            }
            setLoading(true)
            setMessage(AppLanguageStore.t("Connecting...", "Bağlanıyor..."))
            Thread {
                val result = runCatching { ProviderPortalLoader.loadAll(server, user, pass) }
                Handler(Looper.getMainLooper()).post {
                    setLoading(false)
                    result.onSuccess { loaded ->
                        val status = "${loaded.live.size} live • ${loaded.movies.size} movies • ${loaded.series.size} series"
                        val account = loaded.account
                        LivePlaybackSession.setCatalog(safeName, loaded.live, loaded.movies, loaded.series)
                        MediaProfileStore.upsert(
                            profileName = safeName,
                            sourceType = "Provider API",
                            serverAddress = server,
                            accountName = user,
                            accessKey = pass,
                            live = loaded.live,
                            movies = loaded.movies,
                            series = loaded.series,
                            status = status,
                            context = context,
                            mediaAccountExpiry = account.expiryDate,
                            mediaAccountStatus = account.status,
                            activeConnections = account.activeConnections,
                            maxConnections = account.maxConnections,
                            trialStatus = account.trialStatus
                        )
                        setMessage(status)
                        navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                    }.onFailure { error ->
                        setMessage(error.message ?: AppLanguageStore.t("Could not connect.", "Bağlanılamadı."))
                    }
                }
            }.start()
        }

        SourceMode.ListUrl -> {
            if (listUrl.isBlank()) {
                setMessage(AppLanguageStore.t("M3U URL is required.", "M3U URL zorunludur."))
                return
            }
            setLoading(true)
            setMessage(AppLanguageStore.t("Loading...", "Yükleniyor..."))
            Thread {
                val result = runCatching { RemoteListLoader.load(listUrl) }
                Handler(Looper.getMainLooper()).post {
                    setLoading(false)
                    result.onSuccess { loaded ->
                        val status = "${loaded.size} live • 0 movies • 0 series"
                        LivePlaybackSession.setCatalog(safeName, loaded)
                        MediaProfileStore.upsert(safeName, "M3U URL", listUrl, "", "", loaded, emptyList(), emptyList(), status, context)
                        setMessage(status)
                        if (loaded.isNotEmpty()) navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                    }.onFailure { error ->
                        setMessage(error.message ?: AppLanguageStore.t("Could not load.", "Yüklenemedi."))
                    }
                }
            }.start()
        }

        SourceMode.LocalFile -> {
            if (localText.isBlank()) {
                setMessage(AppLanguageStore.t("Local data is empty.", "Yerel veri boş."))
                return
            }
            val parsed = M3uParser.parse(localText)
            val status = "${parsed.size} live • 0 movies • 0 series"
            LivePlaybackSession.setCatalog(safeName, parsed)
            MediaProfileStore.upsert(safeName, "Local data", "Local", "", "", parsed, emptyList(), emptyList(), status, context)
            setMessage(status)
            if (parsed.isNotEmpty()) navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
        }

        SourceMode.Single -> {
            val stream = singleUrl.trim()
            if (!stream.startsWith("http://") && !stream.startsWith("https://")) {
                setMessage(AppLanguageStore.t("Stream URL must start with http or https.", "Yayın URL http veya https ile başlamalı."))
                return
            }
            val item = LiveChannel("Single Stream", stream, "Single Stream")
            LivePlaybackSession.select(item)
            MediaProfileStore.upsert(safeName, "Single stream", stream, "", "", listOf(item), emptyList(), emptyList(), "1 stream ready", context)
            navController.navigate(AppDestinations.Player.route) { launchSingleTop = true }
        }
    }
}

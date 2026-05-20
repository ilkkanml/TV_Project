package com.nexora.tv.ui.screens

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.data.live.M3uParser
import com.nexora.tv.data.live.ProviderPortalLoader
import com.nexora.tv.data.live.RemoteListLoader
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

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

    var mode by remember { mutableStateOf(SourceMode.Portal) }
    var activeFieldId by remember { mutableStateOf<String?>(null) }
    var profileName by remember { mutableStateOf(editingProfile?.profileName ?: "") }
    var server by remember { mutableStateOf(editingProfile?.serverAddress ?: "") }
    var user by remember { mutableStateOf(editingProfile?.accountName ?: "") }
    var pass by remember { mutableStateOf(editingProfile?.accessKey ?: "") }
    var listUrl by remember { mutableStateOf("") }
    var localText by remember { mutableStateOf("") }
    var singleUrl by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var showClear by remember { mutableStateOf(false) }
    var message by remember {
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
                message = AppLanguageStore.t("Local file loaded. Press Connect to continue.", "Yerel dosya yüklendi. Devam etmek için Connect'e bas.")
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
                Text(AppLanguageStore.t("Create Profile", "Profil Oluştur"), color = SetupVioletSoft, fontSize = 24.sp, fontWeight = FontWeight.Black, maxLines = 1)
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
                        SetupInputField(
                            fieldId = "server",
                            activeFieldId = activeFieldId,
                            onActiveFieldChange = { activeFieldId = it },
                            label = AppLanguageStore.t("Server URL", "Sunucu URL"),
                            value = server,
                            onChange = { server = it.filterNot(Char::isWhitespace) },
                            focusRequester = serverFocus,
                            nextFocusRequester = userFocus,
                            nextFieldId = "user",
                            modifier = Modifier.focusProperties { up = profileFocus }
                        )
                        SetupInputField(
                            fieldId = "user",
                            activeFieldId = activeFieldId,
                            onActiveFieldChange = { activeFieldId = it },
                            label = AppLanguageStore.t("User name", "Kullanıcı adı"),
                            value = user,
                            onChange = { user = it.filterNot(Char::isWhitespace) },
                            focusRequester = userFocus,
                            nextFocusRequester = passFocus,
                            nextFieldId = "pass"
                        )
                        SetupInputField(
                            fieldId = "pass",
                            activeFieldId = activeFieldId,
                            onActiveFieldChange = { activeFieldId = it },
                            label = AppLanguageStore.t("Password", "Şifre"),
                            value = pass,
                            onChange = { pass = it.filterNot(Char::isWhitespace) },
                            focusRequester = passFocus,
                            nextFocusRequester = connectFocus,
                            nextFieldId = null,
                            secret = true,
                            modifier = Modifier.focusProperties { down = connectFocus }
                        )
                    }

                    SourceMode.ListUrl -> {
                        SetupInputField(
                            fieldId = "list",
                            activeFieldId = activeFieldId,
                            onActiveFieldChange = { activeFieldId = it },
                            label = "M3U URL",
                            value = listUrl,
                            onChange = { listUrl = it.filterNot(Char::isWhitespace) },
                            focusRequester = listFocus,
                            nextFocusRequester = connectFocus
                        )
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
                        SetupInputField(
                            fieldId = "local",
                            activeFieldId = activeFieldId,
                            onActiveFieldChange = { activeFieldId = it },
                            label = AppLanguageStore.t("Paste list data", "Liste verisini yapıştır"),
                            value = localText,
                            onChange = { localText = it },
                            focusRequester = localFocus,
                            nextFocusRequester = connectFocus,
                            singleLine = false,
                            height = 96
                        )
                    }

                    SourceMode.Single -> {
                        SetupInputField(
                            fieldId = "single",
                            activeFieldId = activeFieldId,
                            onActiveFieldChange = { activeFieldId = it },
                            label = AppLanguageStore.t("Stream URL", "Yayın URL"),
                            value = singleUrl,
                            onChange = { singleUrl = it.filterNot(Char::isWhitespace) },
                            focusRequester = singleFocus,
                            nextFocusRequester = connectFocus
                        )
                    }
                }

                SetupActionRow(
                    loading = loading,
                    mode = mode,
                    connectFocus = connectFocus,
                    onConnect = {
                        if (!loading) {
                            activeFieldId = null
                            connectCurrentSource(
                                context = context,
                                mode = mode,
                                profileName = profileName,
                                server = server,
                                user = user,
                                pass = pass,
                                listUrl = listUrl,
                                localText = localText,
                                singleUrl = singleUrl,
                                navController = navController,
                                setLoading = { loading = it },
                                setMessage = { message = it }
                            )
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
private fun ClearFormDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(AppLanguageStore.t("Clear form?", "Form temizlensin mi?")) },
        text = { Text(AppLanguageStore.t("Current form fields will be cleared. Saved profiles will not be deleted.", "Mevcut form alanları temizlenir. Kayıtlı profiller silinmez.")) },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text(AppLanguageStore.t("Clear", "Temizle")) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(AppLanguageStore.t("Cancel", "İptal")) }
        }
    )
}

@Composable
private fun SetupActionRow(
    loading: Boolean,
    mode: SourceMode,
    connectFocus: FocusRequester,
    onConnect: () -> Unit,
    onClear: () -> Unit,
    onBack: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Button(
            onClick = onConnect,
            modifier = Modifier.width(174.dp).height(52.dp).focusRequester(connectFocus),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SetupViolet, contentColor = Color.White)
        ) {
            Text(
                text = if (loading) AppLanguageStore.t("Loading", "Yükleniyor") else if (mode == SourceMode.Single) AppLanguageStore.t("Play Stream", "Yayını Aç") else "Connect",
                fontWeight = FontWeight.Black,
                fontSize = 13.sp
            )
        }

        Button(
            onClick = onClear,
            modifier = Modifier.width(102.dp).height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
        ) { Text(AppLanguageStore.t("Clear", "Temizle"), fontSize = 12.sp) }

        Button(
            onClick = onBack,
            modifier = Modifier.width(102.dp).height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
        ) { Text(AppLanguageStore.t("Back", "Geri"), fontSize = 12.sp) }
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
            setLoading(true)
            setMessage(AppLanguageStore.t("Connecting...", "Bağlanıyor..."))
            Thread {
                val result = runCatching { ProviderPortalLoader.loadAll(server, user, pass) }
                Handler(Looper.getMainLooper()).post {
                    setLoading(false)
                    result.onSuccess { loaded ->
                        val status = "${loaded.live.size} live • ${loaded.movies.size} movies • ${loaded.series.size} series"
                        LivePlaybackSession.setCatalog(safeName, loaded.live, loaded.movies, loaded.series)
                        MediaProfileStore.upsert(safeName, "Provider API", server, user, pass, loaded.live, loaded.movies, loaded.series, status, context)
                        setMessage(status)
                        navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                    }.onFailure { error ->
                        setMessage(error.message ?: AppLanguageStore.t("Could not connect.", "Bağlanılamadı."))
                    }
                }
            }.start()
        }

        SourceMode.ListUrl -> {
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
            val parsed = M3uParser.parse(localText)
            val status = "${parsed.size} live • 0 movies • 0 series"
            LivePlaybackSession.setCatalog(safeName, parsed)
            MediaProfileStore.upsert(safeName, "Local data", "Local", "", "", parsed, emptyList(), emptyList(), status, context)
            setMessage(status)
            if (parsed.isNotEmpty()) navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
        }

        SourceMode.Single -> {
            val stream = singleUrl.trim()
            if (stream.startsWith("http://") || stream.startsWith("https://")) {
                val item = LiveChannel("Single Stream", stream, "Single Stream")
                LivePlaybackSession.select(item)
                MediaProfileStore.upsert(safeName, "Single stream", stream, "", "", listOf(item), emptyList(), emptyList(), "1 stream ready", context)
                navController.navigate(AppDestinations.Player.route) { launchSingleTop = true }
            } else {
                setMessage(AppLanguageStore.t("Stream URL must start with http or https.", "Yayın URL http veya https ile başlamalı."))
            }
        }
    }
}

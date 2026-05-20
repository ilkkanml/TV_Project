package com.nexora.tv.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
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

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraBlue = Color(0xFF4CC9FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xAA11131C)

private enum class SourceMode(val label: String) {
    Portal("Provider API"),
    ListUrl("M3U URL"),
    LocalFile("Local data"),
    Single("Play single stream")
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

    var mode by remember { mutableStateOf(SourceMode.Portal) }
    var profileName by remember { mutableStateOf(editingProfile?.profileName ?: "") }
    var server by remember { mutableStateOf(editingProfile?.serverAddress ?: "") }
    var user by remember { mutableStateOf(editingProfile?.accountName ?: "") }
    var pass by remember { mutableStateOf(editingProfile?.accessKey ?: "") }
    var listUrl by remember { mutableStateOf("") }
    var localText by remember { mutableStateOf("") }
    var singleUrl by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var showClear by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf(editingProfile?.status ?: AppLanguageStore.t("Create a profile and connect a personal media source.", "Profil oluştur ve kişisel medya kaynağını bağla.")) }

    val firstFieldFocus = when (mode) {
        SourceMode.Portal -> serverFocus
        SourceMode.ListUrl -> listFocus
        SourceMode.LocalFile -> localFocus
        SourceMode.Single -> singleFocus
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
        AlertDialog(
            onDismissRequest = { showClear = false },
            title = { Text(AppLanguageStore.t("Clear form?", "Form temizlensin mi?")) },
            text = { Text(AppLanguageStore.t("Current form fields will be cleared. Saved profiles will not be deleted.", "Mevcut form alanları temizlenir. Kayıtlı profiller silinmez.")) },
            confirmButton = {
                TextButton(onClick = {
                    profileName = ""
                    server = ""
                    user = ""
                    pass = ""
                    listUrl = ""
                    localText = ""
                    singleUrl = ""
                    message = AppLanguageStore.t("Create a profile and connect a personal media source.", "Profil oluştur ve kişisel medya kaynağını bağla.")
                    showClear = false
                }) { Text(AppLanguageStore.t("Clear", "Temizle")) }
            },
            dismissButton = {
                TextButton(onClick = { showClear = false }) { Text(AppLanguageStore.t("Cancel", "İptal")) }
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
                Text(AppLanguageStore.t("Create Profile", "Profil Oluştur"), color = NexoraVioletSoft, fontSize = 24.sp, fontWeight = FontWeight.Black, maxLines = 1)
                Text(
                    AppLanguageStore.t(
                        "Focus a field and press OK/Enter to edit. Press Enter again to move to the next field.",
                        "Bir alana gel ve düzenlemek için OK/Enter'a bas. Sonraki kutuya geçmek için tekrar Enter'a bas."
                    ),
                    color = Color.White.copy(alpha = 0.62f),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SourceMode.values().forEach { item ->
                        ModeButton(
                            text = item.label,
                            selected = mode == item,
                            focusRequester = if (item == SourceMode.Portal) portalFocus else null,
                            onSelected = {
                                mode = item
                                message = when (item) {
                                    SourceMode.Portal -> AppLanguageStore.t("Provider API selected. Enter server, user name and password.", "Provider API seçildi. Sunucu, kullanıcı adı ve şifre gir.")
                                    SourceMode.ListUrl -> AppLanguageStore.t("M3U URL selected. Enter the list URL.", "M3U URL seçildi. Liste adresini gir.")
                                    SourceMode.LocalFile -> AppLanguageStore.t("Local data selected. Choose a file or paste list text.", "Yerel veri seçildi. Dosya seç veya liste metni yapıştır.")
                                    SourceMode.Single -> AppLanguageStore.t("Single stream selected. Enter one stream URL.", "Tek yayın seçildi. Bir yayın adresi gir.")
                                }
                            }
                        )
                    }
                }

                TvEditField(
                    label = AppLanguageStore.t("Profile name", "Profil adı"),
                    value = profileName,
                    onChange = { profileName = it },
                    focusRequester = profileFocus,
                    nextFocusRequester = firstFieldFocus,
                    modifier = Modifier.focusProperties { up = portalFocus }
                )

                when (mode) {
                    SourceMode.Portal -> {
                        TvEditField(
                            label = AppLanguageStore.t("Server URL", "Sunucu URL"),
                            value = server,
                            onChange = { server = it.filterNot(Char::isWhitespace) },
                            focusRequester = serverFocus,
                            nextFocusRequester = userFocus,
                            modifier = Modifier.focusProperties { up = profileFocus }
                        )
                        TvEditField(
                            label = AppLanguageStore.t("User name", "Kullanıcı adı"),
                            value = user,
                            onChange = { user = it.filterNot(Char::isWhitespace) },
                            focusRequester = userFocus,
                            nextFocusRequester = passFocus
                        )
                        TvEditField(
                            label = AppLanguageStore.t("Password", "Şifre"),
                            value = pass,
                            onChange = { pass = it.filterNot(Char::isWhitespace) },
                            secret = true,
                            focusRequester = passFocus,
                            nextFocusRequester = connectFocus,
                            modifier = Modifier.focusProperties { down = connectFocus }
                        )
                    }
                    SourceMode.ListUrl -> TvEditField(
                        label = "M3U URL",
                        value = listUrl,
                        onChange = { listUrl = it.filterNot(Char::isWhitespace) },
                        focusRequester = listFocus,
                        nextFocusRequester = connectFocus
                    )
                    SourceMode.LocalFile -> {
                        Button(
                            onClick = { filePicker.launch("*/*") },
                            modifier = Modifier.width(500.dp).height(52.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                        ) { Text(AppLanguageStore.t("Choose file from device", "Cihazdan dosya seç"), fontWeight = FontWeight.Black, fontSize = 13.sp) }
                        TvEditField(
                            label = AppLanguageStore.t("Paste list data", "Liste verisini yapıştır"),
                            value = localText,
                            onChange = { localText = it },
                            singleLine = false,
                            height = 96,
                            focusRequester = localFocus,
                            nextFocusRequester = connectFocus
                        )
                    }
                    SourceMode.Single -> TvEditField(
                        label = AppLanguageStore.t("Stream URL", "Yayın URL"),
                        value = singleUrl,
                        onChange = { singleUrl = it.filterNot(Char::isWhitespace) },
                        focusRequester = singleFocus,
                        nextFocusRequester = connectFocus
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            if (loading) return@Button
                            val safeName = profileName.ifBlank { AppLanguageStore.t("User Profile", "Kullanıcı Profili") }

                            when (mode) {
                                SourceMode.Portal -> {
                                    loading = true
                                    message = AppLanguageStore.t("Connecting...", "Bağlanıyor...")
                                    Thread {
                                        val result = runCatching { ProviderPortalLoader.loadAll(server, user, pass) }
                                        Handler(Looper.getMainLooper()).post {
                                            loading = false
                                            result.onSuccess { loaded ->
                                                val status = "${loaded.live.size} live • ${loaded.movies.size} movies • ${loaded.series.size} series"
                                                LivePlaybackSession.setCatalog(safeName, loaded.live, loaded.movies, loaded.series)
                                                MediaProfileStore.upsert(safeName, "Provider API", server, user, pass, loaded.live, loaded.movies, loaded.series, status, context)
                                                message = status
                                                navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                                            }.onFailure { error ->
                                                message = error.message ?: AppLanguageStore.t("Could not connect.", "Bağlanılamadı.")
                                            }
                                        }
                                    }.start()
                                }
                                SourceMode.ListUrl -> {
                                    loading = true
                                    message = AppLanguageStore.t("Loading...", "Yükleniyor...")
                                    Thread {
                                        val result = runCatching { RemoteListLoader.load(listUrl) }
                                        Handler(Looper.getMainLooper()).post {
                                            loading = false
                                            result.onSuccess { loaded ->
                                                val status = "${loaded.size} live • 0 movies • 0 series"
                                                LivePlaybackSession.setCatalog(safeName, loaded)
                                                MediaProfileStore.upsert(safeName, "M3U URL", listUrl, "", "", loaded, emptyList(), emptyList(), status, context)
                                                message = status
                                                if (loaded.isNotEmpty()) navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                                            }.onFailure { error ->
                                                message = error.message ?: AppLanguageStore.t("Could not load.", "Yüklenemedi.")
                                            }
                                        }
                                    }.start()
                                }
                                SourceMode.LocalFile -> {
                                    val parsed = M3uParser.parse(localText)
                                    val status = "${parsed.size} live • 0 movies • 0 series"
                                    LivePlaybackSession.setCatalog(safeName, parsed)
                                    MediaProfileStore.upsert(safeName, "Local data", "Local", "", "", parsed, emptyList(), emptyList(), status, context)
                                    message = status
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
                                        message = AppLanguageStore.t("Stream URL must start with http or https.", "Yayın URL http veya https ile başlamalı.")
                                    }
                                }
                            }
                        },
                        modifier = Modifier.width(174.dp).height(52.dp).focusRequester(connectFocus),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
                    ) { Text(if (loading) AppLanguageStore.t("Loading", "Yükleniyor") else if (mode == SourceMode.Single) AppLanguageStore.t("Play Stream", "Yayını Aç") else "Connect", fontWeight = FontWeight.Black, fontSize = 13.sp) }

                    Button(
                        onClick = { showClear = true },
                        modifier = Modifier.width(102.dp).height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                    ) { Text(AppLanguageStore.t("Clear", "Temizle"), fontSize = 12.sp) }

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.width(102.dp).height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                    ) { Text(AppLanguageStore.t("Back", "Geri"), fontSize = 12.sp) }
                }

                StatusBox(message)
            }

            SecurityPanel()
        }
    }
}

@Composable
private fun TvEditField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    secret: Boolean = false,
    singleLine: Boolean = true,
    height: Int = 66,
    focusRequester: FocusRequester? = null,
    nextFocusRequester: FocusRequester? = null,
    modifier: Modifier = Modifier
) {
    var active by remember { mutableStateOf(false) }
    val internalRequester = remember { FocusRequester() }
    val requester = focusRequester ?: internalRequester

    fun moveNext() {
        active = false
        nextFocusRequester?.requestFocus()
    }

    LaunchedEffect(active) {
        if (active) requester.requestFocus()
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        readOnly = !active,
        singleLine = singleLine,
        visualTransformation = if (secret) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done),
        keyboardActions = KeyboardActions(
            onNext = { moveNext() },
            onDone = { moveNext() }
        ),
        modifier = modifier
            .width(500.dp)
            .height(height.dp)
            .focusRequester(requester)
            .onFocusChanged { if (!it.isFocused) active = false }
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyUp && (event.key == Key.Enter || event.key == Key.NumPadEnter || event.key == Key.DirectionCenter)) {
                    if (active) moveNext() else active = true
                    true
                } else false
            },
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NexoraViolet,
            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
            focusedLabelColor = NexoraVioletSoft,
            unfocusedLabelColor = Color.White.copy(alpha = 0.50f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = NexoraVioletSoft,
            focusedContainerColor = Color.White.copy(alpha = 0.035f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.02f)
        ),
        placeholder = { Text(AppLanguageStore.t("Press OK to edit", "Düzenlemek için OK"), color = Color.White.copy(alpha = 0.42f)) }
    )
}

@Composable
private fun ModeButton(text: String, selected: Boolean, focusRequester: FocusRequester? = null, onSelected: () -> Unit) {
    Button(
        onClick = onSelected,
        modifier = Modifier
            .height(42.dp)
            .then(if (focusRequester != null) Modifier.focusRequester(focusRequester) else Modifier)
            .onFocusChanged { if (it.isFocused) onSelected() },
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (selected) NexoraViolet else Color.White.copy(alpha = 0.08f), contentColor = Color.White)
    ) { Text(text, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Black else FontWeight.Medium, maxLines = 1) }
}

@Composable
private fun StatusBox(text: String) {
    Box(modifier = Modifier.width(500.dp).background(PanelSoft, RoundedCornerShape(20.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(14.dp)) {
        Text(text, color = Color.White.copy(alpha = 0.76f), fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun SecurityPanel() {
    Column(
        modifier = Modifier
            .width(650.dp)
            .height(594.dp)
            .background(PanelDark, RoundedCornerShape(30.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(AppLanguageStore.t("Security & Early Access", "Güvenlik ve Erken Erişim"), color = Color.White, fontSize = 31.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.t("Built as a premium TV player ecosystem.", "Premium TV oynatıcı ekosistemi olarak geliştiriliyor."), color = NexoraVioletSoft, fontSize = 15.sp, fontWeight = FontWeight.Bold)

        NoticeCard(AppLanguageStore.t("Player, not a provider", "Oynatıcıdır, sağlayıcı değildir"), AppLanguageStore.t("Nexora TV does not sell channels, subscriptions, accounts, lists, or media access.", "Nexora TV kanal, abonelik, hesap, liste veya medya erişimi satmaz."))
        NoticeCard(AppLanguageStore.t("Legal use only", "Sadece yasal kullanım"), AppLanguageStore.t("Users must enter only media access they are legally authorized to use.", "Kullanıcı yalnızca yasal erişim hakkı olan medya kaynaklarını girmelidir."))
        NoticeCard(AppLanguageStore.t("No MAC-based identity", "MAC tabanlı kimlik yok"), AppLanguageStore.t("We do not use MAC as the device identity. It can reveal hardware-level details, may be blocked by privacy rules, and is not reliable across devices.", "MAC adresini cihaz kimliği olarak kullanmıyoruz. Donanım bilgisi açığa çıkarabilir, gizlilik kurallarıyla engellenebilir ve cihazlar arasında güvenilir değildir."))
        NoticeCard(AppLanguageStore.t("Privacy-first direction", "Gizlilik öncelikli yaklaşım"), AppLanguageStore.t("The setup flow keeps access controlled by the user and avoids unnecessary device identifiers.", "Kurulum akışı erişimi kullanıcı kontrolünde tutar ve gereksiz cihaz kimliklerinden kaçınır."))
        NoticeCard(AppLanguageStore.t("Free during early access", "Erken erişimde ücretsiz"), AppLanguageStore.t("The app remains free while the full ecosystem is being completed and tested.", "Tüm ekosistem tamamlanıp test edilene kadar uygulama ücretsiz kalır."))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            InfoPill("TV-friendly")
            InfoPill("Remote-first")
            InfoPill("early access")
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
        Text(body, color = Color.White.copy(alpha = 0.68f), fontSize = 12.sp, lineHeight = 18.sp)
    }
}

@Composable
private fun InfoPill(text: String) {
    Box(modifier = Modifier.background(NexoraBlue.copy(alpha = 0.14f), RoundedCornerShape(14.dp)).border(1.dp, NexoraBlue.copy(alpha = 0.35f), RoundedCornerShape(14.dp)).padding(horizontal = 12.dp, vertical = 8.dp), contentAlignment = Alignment.Center) {
        Text(text, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
    }
}

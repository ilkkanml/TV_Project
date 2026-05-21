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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.content.MockContentLibrary
import com.nexora.tv.data.content.NexoraContentItem
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.data.live.MediaKind
import com.nexora.tv.data.live.ProviderSeriesEpisodeLoader
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraBlue = Color(0xFF4CC9FF)
private val NexoraGreen = Color(0xFF39FF88)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xB0111624)

@Composable
fun ContentDetailScreen(navController: NavController, contentId: String?) {
    val providerItem = LivePlaybackSession.detailItem
    if (contentId == "provider-media" && providerItem != null) {
        ProviderContentDetail(navController, providerItem)
        return
    }

    val content = MockContentLibrary.findContent(contentId)

    if (content == null) {
        MissingContentDetail(navController)
        return
    }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 42.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailPoster(content)
            DetailInfo(navController, content)
        }
    }
}

@Composable
private fun ProviderContentDetail(navController: NavController, item: LiveChannel) {
    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 42.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProviderPoster(item)
            ProviderInfo(navController, item)
        }
    }
}

@Composable
private fun ProviderPoster(item: LiveChannel) {
    val accent = if (item.mediaKind == MediaKind.Series) NexoraBlue else NexoraViolet
    Box(
        modifier = Modifier.width(360.dp).height(540.dp).shadow(24.dp, RoundedCornerShape(34.dp), ambientColor = accent, spotColor = accent).background(PanelDark, RoundedCornerShape(34.dp)).border(2.dp, accent.copy(alpha = 0.72f), RoundedCornerShape(34.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(accent.copy(alpha = 0.34f), Color.Black.copy(alpha = 0.18f), Color.Black.copy(alpha = 0.86f))), RoundedCornerShape(34.dp)))

        Column(modifier = Modifier.align(Alignment.TopStart).padding(22.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DetailPill(if (item.mediaKind == MediaKind.Series) AppLanguageStore.ui("Series") else AppLanguageStore.ui("Movies"), accent)
            DetailPill(item.group.ifBlank { AppLanguageStore.ui("Category") }, NexoraBlue)
        }

        Column(modifier = Modifier.align(Alignment.BottomStart).padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(item.name, color = Color.White, fontSize = 31.sp, fontWeight = FontWeight.Black, lineHeight = 34.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Text(item.group.ifBlank { AppLanguageStore.ui("Category") }, color = Color.White.copy(alpha = 0.70f), fontSize = 13.sp, lineHeight = 18.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun ProviderInfo(navController: NavController, item: LiveChannel) {
    val accent = if (item.mediaKind == MediaKind.Series) NexoraBlue else NexoraViolet
    Column(
        modifier = Modifier.width(770.dp).background(PanelDark, RoundedCornerShape(34.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(34.dp)).padding(30.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(if (item.mediaKind == MediaKind.Series) AppLanguageStore.ui("Series").uppercase() else AppLanguageStore.ui("Movies").uppercase(), color = NexoraVioletSoft, fontSize = 13.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
        Text(item.name, color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Black, lineHeight = 52.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(item.description.ifBlank { AppLanguageStore.t("No description is available for this title.", "Bu içerik için açıklama mevcut değil.") }, color = Color.White.copy(alpha = 0.72f), fontSize = 16.sp, lineHeight = 23.sp, maxLines = 5, overflow = TextOverflow.Ellipsis)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DetailPill(item.group.ifBlank { AppLanguageStore.ui("Category") }, accent)
            DetailPill(AppLanguageStore.ui("Licensed"), NexoraBlue)
            DetailPill(AppLanguageStore.t("Provider catalog", "Sağlayıcı kataloğu"), NexoraViolet)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            MetricCard(AppLanguageStore.ui("Quality"), AppLanguageStore.t("Player selectable", "Player içinden seçilir"))
            MetricCard(AppLanguageStore.ui("Audio"), AppLanguageStore.t("Stream based", "Yayına bağlı"))
            MetricCard(AppLanguageStore.ui("Subtitles"), AppLanguageStore.t("If available", "Varsa"))
        }

        if (item.mediaKind == MediaKind.Series) {
            SeriesEpisodePanel(navController = navController, series = item)
        } else {
            PlaybackInfoBox()
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            if (item.mediaKind == MediaKind.Movie && item.streamUrl.isNotBlank()) {
                Button(
                    onClick = {
                        LivePlaybackSession.select(item)
                        navController.navigate(AppDestinations.Player.route) { launchSingleTop = true }
                    },
                    modifier = Modifier.width(136.dp).height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NexoraGreen, contentColor = Color.Black)
                ) { Text(AppLanguageStore.ui("PLAY"), fontWeight = FontWeight.Black) }
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.width(128.dp).height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
            ) { Text(AppLanguageStore.ui("Back"), fontWeight = FontWeight.Bold) }

            Button(
                onClick = { navController.navigate(AppDestinations.Home.route) { launchSingleTop = true } },
                modifier = Modifier.width(128.dp).height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
            ) { Text(AppLanguageStore.ui("Home"), fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
private fun PlaybackInfoBox() {
    Box(modifier = Modifier.width(690.dp).background(PanelSoft, RoundedCornerShape(24.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp)).padding(16.dp)) {
        Text(
            text = AppLanguageStore.t(
                "Audio, subtitle and picture quality options appear inside Player Settings when the stream provides them.",
                "Ses, altyazı ve görüntü kalitesi seçenekleri yayın destekliyorsa Player Settings içinde görünür."
            ),
            color = Color.White.copy(alpha = 0.62f),
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun SeriesEpisodePanel(navController: NavController, series: LiveChannel) {
    val context = LocalContext.current
    MediaProfileStore.init(context)
    val profile = MediaProfileStore.selectedProfile
    var loading by remember(series.mediaId, profile?.id) { mutableStateOf(false) }
    var episodes by remember(series.mediaId, profile?.id) { mutableStateOf<List<LiveChannel>>(emptyList()) }
    var message by remember(series.mediaId, profile?.id) { mutableStateOf("") }
    var selectedSeason by remember(series.mediaId, profile?.id) { mutableStateOf("") }

    LaunchedEffect(series.mediaId, profile?.id) {
        if (series.mediaId.isBlank()) {
            message = AppLanguageStore.t("Series id is missing. Episode data cannot be loaded.", "Dizi kimliği eksik. Bölüm verisi yüklenemiyor.")
            return@LaunchedEffect
        }
        if (profile == null || profile.sourceType != "Provider API") {
            message = AppLanguageStore.t("Episode data requires a Provider API profile.", "Bölüm verisi için Provider API profili gerekir.")
            return@LaunchedEffect
        }
        loading = true
        message = AppLanguageStore.t("Loading episodes...", "Bölümler yükleniyor...")
        Thread {
            val result = runCatching {
                ProviderSeriesEpisodeLoader.loadEpisodes(
                    server = profile.serverAddress,
                    user = profile.accountName,
                    pass = profile.accessKey,
                    seriesId = series.mediaId
                )
            }
            Handler(Looper.getMainLooper()).post {
                loading = false
                result.onSuccess { loaded ->
                    episodes = loaded
                    selectedSeason = loaded.firstOrNull()?.seasonName.orEmpty()
                    message = if (loaded.isEmpty()) {
                        AppLanguageStore.t("No episodes were returned by the provider.", "Sağlayıcıdan bölüm verisi gelmedi.")
                    } else {
                        AppLanguageStore.t("Select a season and episode to play.", "Oynatmak için sezon ve bölüm seç.")
                    }
                }.onFailure { error ->
                    message = error.message ?: AppLanguageStore.t("Episodes could not be loaded.", "Bölümler yüklenemedi.")
                }
            }
        }.start()
    }

    val seasons = episodes.map { it.seasonName.ifBlank { AppLanguageStore.t("Season", "Sezon") } }.distinct()
    val activeSeason = selectedSeason.ifBlank { seasons.firstOrNull().orEmpty() }
    val visibleEpisodes = if (activeSeason.isBlank()) emptyList() else episodes.filter { it.seasonName == activeSeason }

    Column(modifier = Modifier.width(690.dp).background(PanelSoft, RoundedCornerShape(24.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(AppLanguageStore.t("Seasons & Episodes", "Sezonlar ve Bölümler"), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
        Text(message, color = Color.White.copy(alpha = 0.62f), fontSize = 12.sp, lineHeight = 18.sp)

        if (loading) {
            Text(AppLanguageStore.t("Please wait...", "Lütfen bekle..."), color = NexoraVioletSoft, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }

        if (seasons.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                seasons.forEach { season ->
                    DetailSmallButton(text = season, selected = season == activeSeason) { selectedSeason = season }
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxWidth().height(260.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(visibleEpisodes, key = { it.streamUrl.ifBlank { it.mediaId + it.name } }) { episode ->
                EpisodeButton(navController = navController, episode = episode)
            }
        }
    }
}

@Composable
private fun EpisodeButton(navController: NavController, episode: LiveChannel) {
    Button(
        onClick = {
            LivePlaybackSession.select(episode)
            navController.navigate(AppDestinations.Player.route) { launchSingleTop = true }
        },
        modifier = Modifier.fillMaxWidth().height(48.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.07f), contentColor = Color.White)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(episode.episodeName.ifBlank { episode.name }, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.width(520.dp))
            Text(AppLanguageStore.ui("PLAY"), color = NexoraGreen, fontSize = 11.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun DetailSmallButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.height(38.dp), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = if (selected) NexoraViolet else Color.White.copy(alpha = 0.08f), contentColor = Color.White)) {
        Text(text, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Black else FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun DetailPoster(content: NexoraContentItem) {
    val accent = Color(content.accentColor)
    Box(
        modifier = Modifier.width(360.dp).height(540.dp).shadow(24.dp, RoundedCornerShape(34.dp), ambientColor = accent, spotColor = accent).background(PanelDark, RoundedCornerShape(34.dp)).border(2.dp, accent.copy(alpha = 0.72f), RoundedCornerShape(34.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(accent.copy(alpha = 0.34f), Color.Black.copy(alpha = 0.18f), Color.Black.copy(alpha = 0.86f))), RoundedCornerShape(34.dp)))

        Column(modifier = Modifier.align(Alignment.TopStart).padding(22.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DetailPill(content.badge, accent)
            DetailPill(content.category, NexoraBlue)
        }

        Column(modifier = Modifier.align(Alignment.BottomStart).padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(content.title, color = Color.White, fontSize = 31.sp, fontWeight = FontWeight.Black, lineHeight = 34.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(content.subtitle, color = Color.White.copy(alpha = 0.70f), fontSize = 13.sp, lineHeight = 18.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun DetailInfo(navController: NavController, content: NexoraContentItem) {
    val accent = Color(content.accentColor)
    Column(
        modifier = Modifier.width(770.dp).background(PanelDark, RoundedCornerShape(34.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(34.dp)).padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(AppLanguageStore.ui(content.type.label).uppercase(), color = NexoraVioletSoft, fontSize = 13.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
        Text(content.title, color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Black, lineHeight = 52.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(content.description, color = Color.White.copy(alpha = 0.72f), fontSize = 16.sp, lineHeight = 23.sp, maxLines = 4, overflow = TextOverflow.Ellipsis)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DetailPill(content.category, accent)
            DetailPill(AppLanguageStore.ui("Licensed"), NexoraBlue)
            DetailPill(content.badge, NexoraViolet)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            MetricCard(AppLanguageStore.ui("Runtime"), "42 min")
            MetricCard(AppLanguageStore.ui("Quality"), "4K")
            MetricCard(AppLanguageStore.ui("Audio"), AppLanguageStore.ui("TV ready"))
        }

        Box(modifier = Modifier.width(690.dp).background(PanelSoft, RoundedCornerShape(24.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp)).padding(16.dp)) {
            Text(
                text = AppLanguageStore.t(
                    "This is local placeholder content for interface testing. Real playback is available only from the user's own legal media source.",
                    "Bu içerik yalnızca arayüz testi için yerel örnektir. Gerçek oynatma sadece kullanıcının kendi yasal medya kaynağından yapılır."
                ),
                color = Color.White.copy(alpha = 0.58f),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.width(128.dp).height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
            ) { Text(AppLanguageStore.ui("Back"), fontWeight = FontWeight.Bold) }

            Button(
                onClick = { navController.navigate(AppDestinations.Home.route) { launchSingleTop = true } },
                modifier = Modifier.width(128.dp).height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
            ) { Text(AppLanguageStore.ui("Home"), fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
private fun MetricCard(title: String, body: String) {
    Column(modifier = Modifier.width(170.dp).background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, color = NexoraVioletSoft, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Text(body, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun DetailPill(text: String, color: Color) {
    Box(modifier = Modifier.height(38.dp).background(color.copy(alpha = 0.18f), RoundedCornerShape(14.dp)).border(1.dp, color.copy(alpha = 0.44f), RoundedCornerShape(14.dp)).padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
        Text(text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun MissingContentDetail(navController: NavController) {
    NexoraCinematicBackdrop {
        Column(modifier = Modifier.fillMaxSize().padding(58.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
            Text(AppLanguageStore.ui("Content not found"), color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
            Text(AppLanguageStore.t("This screen protects navigation if a local content id is missing.", "Yerel içerik kimliği eksikse bu ekran navigasyonu korur."), color = Color.White.copy(alpha = 0.68f), fontSize = 16.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Button(onClick = { navController.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)) {
                    Text(AppLanguageStore.ui("Back"))
                }
                Button(onClick = { navController.navigate(AppDestinations.Home.route) { launchSingleTop = true } }, colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)) {
                    Text(AppLanguageStore.ui("Home"))
                }
            }
        }
    }
}

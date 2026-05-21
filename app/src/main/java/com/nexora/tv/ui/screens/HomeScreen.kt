package com.nexora.tv.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.content.MockContentLibrary
import com.nexora.tv.data.content.NexoraContentItem
import com.nexora.tv.data.content.NexoraContentRow
import com.nexora.tv.data.content.NexoraContentSection
import com.nexora.tv.data.live.LiveChannel
import com.nexora.tv.data.live.LivePlaybackSession
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private const val SETTING_ACCOUNT = "setting-account"
private const val SETTING_LANGUAGE = "setting-language"
private val Purple = Color(0xFF7C3AED)
private val PurpleSoft = Color(0xFF9F67FF)
private val Blue = Color(0xFF4CC9FF)
private val Green = Color(0xFF39FF88)
private val Panel = Color(0xCC090B12)
private val Card = Color(0x80131A29)

private enum class HomeMenu(val key: String, val icon: String, val section: NexoraContentSection) {
    Home("Home", "⌂", NexoraContentSection.Home),
    Movies("Movies", "◫", NexoraContentSection.Movies),
    Series("Series", "▤", NexoraContentSection.Series),
    Live("Live", "◉", NexoraContentSection.Live),
    Settings("Settings", "⚙", NexoraContentSection.Settings)
}

@Composable
fun HomeScreen(navController: NavController) {
    var selectedMenuName by rememberSaveable { mutableStateOf(HomeMenu.Home.name) }
    val selectedMenu = runCatching { HomeMenu.valueOf(selectedMenuName) }.getOrDefault(HomeMenu.Home)
    var selectedItemId by rememberSaveable { mutableStateOf(MockContentLibrary.firstItemFor(selectedMenu.section)?.id) }
    val rows = MockContentLibrary.rowsFor(selectedMenu.section)
    val selectedItem = MockContentLibrary.findContent(selectedItemId) ?: rows.firstOrNull()?.items?.firstOrNull()
    var showBackHint by remember { mutableStateOf(false) }

    val recentHomeItems = remember(
        LivePlaybackSession.loadedMovies,
        LivePlaybackSession.loadedSeries
    ) {
        (LivePlaybackSession.loadedMovies + LivePlaybackSession.loadedSeries)
            .distinctBy { it.streamUrl.ifBlank { it.name + it.group } }
            .take(80)
    }

    val loadedItems = when (selectedMenu) {
        HomeMenu.Home -> recentHomeItems
        HomeMenu.Live -> LivePlaybackSession.loadedChannels
        HomeMenu.Movies -> LivePlaybackSession.loadedMovies
        HomeMenu.Series -> LivePlaybackSession.loadedSeries
        else -> emptyList()
    }
    val showingLoadedCatalog = loadedItems.isNotEmpty()

    BackHandler {
        if (showBackHint) navController.navigate(AppDestinations.Profiles.route) { launchSingleTop = true } else showBackHint = true
    }

    NexoraCinematicBackdrop {
        Box(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxSize().padding(14.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Sidebar(selectedMenu) { menu ->
                    showBackHint = false
                    if (selectedMenu != menu) {
                        selectedMenuName = menu.name
                        selectedItemId = MockContentLibrary.firstItemFor(menu.section)?.id
                    }
                }

                Column(Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TopBar(selectedMenu)

                    if (showingLoadedCatalog) {
                        LoadedCatalogPanel(
                            navController = navController,
                            title = when (selectedMenu) {
                                HomeMenu.Home -> AppLanguageStore.t("Recently Added", "Son Eklenenler")
                                else -> AppLanguageStore.ui(selectedMenu.key)
                            },
                            countLabel = when (selectedMenu) {
                                HomeMenu.Home -> AppLanguageStore.t("items", "içerik")
                                HomeMenu.Live -> AppLanguageStore.t("channels", "kanal")
                                HomeMenu.Movies -> AppLanguageStore.t("movies", "film")
                                HomeMenu.Series -> AppLanguageStore.t("series", "dizi")
                                else -> "items"
                            },
                            items = loadedItems,
                            fallbackGroup = when (selectedMenu) {
                                HomeMenu.Home -> AppLanguageStore.t("Movies & Series", "Film ve Diziler")
                                else -> AppLanguageStore.ui(selectedMenu.key)
                            }
                        )
                    } else {
                        if (selectedMenu != HomeMenu.Settings) {
                            HeroPanel(selectedMenu, selectedItem) { openTarget(navController, it) }
                        }
                        rows.forEach { row ->
                            ContentRow(row, selectedItem?.id, { selectedItemId = it.id }) { openTarget(navController, it) }
                        }
                    }
                }
            }
            if (showBackHint) BackHintPopup()
        }
    }
}

private fun openTarget(navController: NavController, item: NexoraContentItem) {
    when (item.id) {
        SETTING_ACCOUNT -> navController.navigate(AppDestinations.Profiles.route) { launchSingleTop = true }
        SETTING_LANGUAGE -> navController.navigate(AppDestinations.Language.route) { launchSingleTop = true }
        else -> navController.navigate(AppDestinations.Detail.createRoute(item.id)) { launchSingleTop = true }
    }
}

@Composable
private fun Sidebar(selectedMenu: HomeMenu, onMenuSelected: (HomeMenu) -> Unit) {
    val requesters = remember { HomeMenu.values().associateWith { FocusRequester() } }
    LaunchedEffect(selectedMenu) { requesters[selectedMenu]?.requestFocus() }

    Column(
        modifier = Modifier.width(188.dp).fillMaxHeight().background(Color(0xAA060810), RoundedCornerShape(26.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(26.dp)).padding(horizontal = 12.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Text("NEXORA TV", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 1.1.sp)
        Text("PLAYER ECOSYSTEM", color = PurpleSoft, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(Modifier.height(4.dp))
        HomeMenu.values().forEach { menu ->
            MenuButton(menu.icon, AppLanguageStore.ui(menu.key), selectedMenu == menu, requesters.getValue(menu)) { onMenuSelected(menu) }
        }
        Spacer(Modifier.weight(1f))
        Text(
            AppLanguageStore.t("Focus changes section • OK opens cards", "Focus bölümü değiştirir • OK kartı açar"),
            color = Color.White.copy(alpha = 0.48f),
            fontSize = 9.sp,
            lineHeight = 12.sp,
            modifier = Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.04f), RoundedCornerShape(16.dp)).padding(10.dp)
        )
    }
}

@Composable
private fun MenuButton(icon: String, title: String, selected: Boolean, requester: FocusRequester, onSelected: () -> Unit) {
    Button(
        onClick = onSelected,
        modifier = Modifier.fillMaxWidth().height(44.dp).focusRequester(requester).onFocusChanged { if (it.isFocused) onSelected() }.then(if (selected) Modifier.shadow(9.dp, RoundedCornerShape(15.dp), ambientColor = Purple, spotColor = Purple) else Modifier),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (selected) Color(0xCC171C30) else Color.White.copy(alpha = 0.045f), contentColor = Color.White)
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(24.dp).height(24.dp).background(if (selected) Purple.copy(alpha = 0.20f) else Color.White.copy(alpha = 0.06f), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Text(icon, color = if (selected) Blue else Color.White.copy(alpha = 0.84f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text(title, fontSize = 13.sp, fontWeight = if (selected) FontWeight.Black else FontWeight.Medium)
        }
    }
}

@Composable
private fun TopBar(selectedMenu: HomeMenu) {
    Row(
        modifier = Modifier.fillMaxWidth().height(72.dp).background(Color(0x66070A12), RoundedCornerShape(20.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(AppLanguageStore.ui(selectedMenu.key), color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(AppLanguageStore.t("Licensed Android TV shell • remote-first navigation", "Lisanslı Android TV shell • kumanda öncelikli navigasyon"), color = Color.White.copy(alpha = 0.60f), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        UtilityPill("4K")
        UtilityPill("Dolby")
        UtilityPill("10:45")
    }
}

@Composable
private fun HeroPanel(selectedMenu: HomeMenu, item: NexoraContentItem?, onOpen: (NexoraContentItem) -> Unit) {
    if (item == null) return
    val accent = Color(item.accentColor)
    Box(Modifier.fillMaxWidth().height(226.dp).background(Panel, RoundedCornerShape(28.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(28.dp))) {
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Color.Black.copy(alpha = 0.78f), accent.copy(alpha = 0.20f), Color.Transparent)), RoundedCornerShape(28.dp)))
        Column(Modifier.padding(start = 26.dp, top = 22.dp, bottom = 18.dp).width(410.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(AppLanguageStore.t("FEATURED ${selectedMenu.key.uppercase()}", "ÖNE ÇIKAN ${AppLanguageStore.ui(selectedMenu.key).uppercase()}"), color = PurpleSoft, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.5.sp)
            Text(item.title, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Black, lineHeight = 38.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(item.subtitle, color = Color.White.copy(alpha = 0.70f), fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(item.description, color = Color.White.copy(alpha = 0.62f), fontSize = 12.sp, lineHeight = 16.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlassButton(AppLanguageStore.ui("Open Details"), true) { onOpen(item) }
                GlassButton(AppLanguageStore.ui("More Info"), false) { onOpen(item) }
            }
        }
        Column(Modifier.align(Alignment.CenterEnd).padding(end = 22.dp).width(214.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            InfoCard(AppLanguageStore.ui("Category"), item.category)
            InfoCard(AppLanguageStore.ui("Type"), AppLanguageStore.ui(item.type.label))
            InfoCard(AppLanguageStore.ui("Badge"), item.badge)
        }
    }
}

@Composable
private fun ContentRow(row: NexoraContentRow, selectedItemId: String?, onFocus: (NexoraContentItem) -> Unit, onOpen: (NexoraContentItem) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(AppLanguageStore.ui(row.title), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.ui(row.subtitle), color = Color.White.copy(alpha = 0.56f), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            row.items.forEach { item -> LibraryCard(item, selectedItemId == item.id, { onFocus(item) }) { onOpen(item) } }
        }
    }
}

@Composable
private fun LibraryCard(item: NexoraContentItem, selected: Boolean, onFocus: () -> Unit, onOpen: () -> Unit) {
    val accent = Color(item.accentColor)
    Button(onClick = onOpen, modifier = Modifier.width(186.dp).height(112.dp).onFocusChanged { if (it.isFocused) onFocus() }.then(if (selected) Modifier.shadow(11.dp, RoundedCornerShape(20.dp), ambientColor = accent, spotColor = accent) else Modifier), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = Card, contentColor = Color.White)) {
        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(accent.copy(alpha = 0.22f), Color.Black.copy(alpha = 0.10f), Color.Black.copy(alpha = 0.54f))), RoundedCornerShape(20.dp)).border(if (selected) 2.dp else 1.dp, if (selected) accent.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(12.dp)) {
            Text(item.badge, color = if (item.badge == "LIVE") Green else Blue, fontSize = 9.sp, fontWeight = FontWeight.Black, modifier = Modifier.align(Alignment.TopStart))
            Text(item.title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Black, lineHeight = 19.sp, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.align(Alignment.CenterStart))
            Text(item.subtitle, color = Color.White.copy(alpha = 0.62f), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.align(Alignment.BottomStart))
        }
    }
}

@Composable
private fun LoadedCatalogPanel(navController: NavController, title: String, countLabel: String, items: List<LiveChannel>, fallbackGroup: String) {
    var selectedGroup by rememberSaveable(items.size, title) { mutableStateOf("All") }
    var showFilters by rememberSaveable(title) { mutableStateOf(true) }
    val groups = items.map { it.group.ifBlank { fallbackGroup } }.distinct().take(48)
    val visibleItems = if (selectedGroup == "All") items else items.filter { it.group.ifBlank { fallbackGroup } == selectedGroup }

    Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(28.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(28.dp)).padding(22.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(title, color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                Text(LivePlaybackSession.sourceStatus, color = Color.White.copy(alpha = 0.62f), fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            UtilityPill("${items.size} $countLabel")
        }
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterButton(AppLanguageStore.ui("All"), selectedGroup == "All") { selectedGroup = "All" }
            FilterButton(AppLanguageStore.ui("Filter"), showFilters) { showFilters = !showFilters }
            if (showFilters) groups.forEach { group -> FilterButton(group, selectedGroup == group) { selectedGroup = group } }
        }
        Column(Modifier.fillMaxWidth().height(470.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            visibleItems.forEachIndexed { index, item ->
                ChannelRow(item, fallbackGroup, if (index == 0) Modifier.focusProperties { up = FocusRequester.Cancel } else Modifier) {
                    if (item.streamUrl.isNotBlank()) {
                        LivePlaybackSession.select(item)
                        navController.navigate(AppDestinations.Player.route) { launchSingleTop = true }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.height(38.dp).focusProperties { up = FocusRequester.Cancel }, shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = if (selected) Purple else Color.White.copy(alpha = 0.08f), contentColor = Color.White)) {
        Text(text, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Black else FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun ChannelRow(item: LiveChannel, fallbackGroup: String, modifier: Modifier, onClick: () -> Unit) {
    val playable = item.streamUrl.isNotBlank()
    Button(onClick = onClick, enabled = playable, modifier = modifier.fillMaxWidth().height(62.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.06f), contentColor = Color.White, disabledContainerColor = Color.White.copy(alpha = 0.035f), disabledContentColor = Color.White.copy(alpha = 0.48f))) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.width(620.dp)) {
                Text(item.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(item.group.ifBlank { fallbackGroup }, color = Color.White.copy(alpha = 0.52f), fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Text(if (playable) AppLanguageStore.ui("PLAY") else AppLanguageStore.ui("CATALOG"), color = if (playable) Green else Color.White.copy(alpha = 0.45f), fontSize = 12.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun BackHintPopup() {
    Box(Modifier.fillMaxSize().padding(top = 24.dp, end = 24.dp), contentAlignment = Alignment.TopEnd) {
        Column(Modifier.width(330.dp).background(Color(0xEE0A0D16), RoundedCornerShape(24.dp)).border(1.dp, Purple.copy(alpha = 0.45f), RoundedCornerShape(24.dp)).shadow(18.dp, RoundedCornerShape(24.dp), ambientColor = Purple, spotColor = Purple).padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(AppLanguageStore.ui("Go back?"), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
            Text(AppLanguageStore.ui("Press Back again to return to user profiles."), color = Color.White.copy(alpha = 0.70f), fontSize = 12.sp, lineHeight = 17.sp)
        }
    }
}

@Composable
private fun UtilityPill(text: String) {
    Box(Modifier.background(Color.White.copy(alpha = 0.055f), RoundedCornerShape(13.dp)).border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(13.dp)).padding(horizontal = 10.dp, vertical = 7.dp), contentAlignment = Alignment.Center) {
        Text(text, color = Color.White.copy(alpha = 0.82f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun InfoCard(title: String, value: String) {
    Row(Modifier.fillMaxWidth().height(32.dp).background(Color.White.copy(alpha = 0.045f), RoundedCornerShape(13.dp)).border(1.dp, Color.White.copy(alpha = 0.055f), RoundedCornerShape(13.dp)).padding(horizontal = 9.dp), horizontalArrangement = Arrangement.spacedBy(7.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = PurpleSoft, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp, modifier = Modifier.width(54.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(value, color = Color.White.copy(alpha = 0.88f), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun GlassButton(text: String, primary: Boolean, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.width(if (primary) 136.dp else 120.dp).height(42.dp), shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(containerColor = if (primary) Purple else Color.White.copy(alpha = 0.075f), contentColor = Color.White)) {
        Text(text, fontWeight = FontWeight.Black, fontSize = 12.sp, maxLines = 1)
    }
}

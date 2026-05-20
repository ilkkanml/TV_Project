package com.nexora.tv.ui.screens

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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.content.MockContentLibrary
import com.nexora.tv.data.content.NexoraContentItem
import com.nexora.tv.data.content.NexoraContentRow
import com.nexora.tv.data.content.NexoraContentSection
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private const val PROFILE_ACCESS_SETTING_ID = "setting-account"
private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraBlue = Color(0xFF4CC9FF)
private val NexoraGreen = Color(0xFF39FF88)
private val PanelDark = Color(0xCC090B12)
private val CardSoft = Color(0x80131A29)

private enum class HomeMenu(val label: String, val icon: String, val section: NexoraContentSection) {
    Home("Home", "⌂", NexoraContentSection.Home),
    Movies("Movies", "◫", NexoraContentSection.Movies),
    Series("Series", "▤", NexoraContentSection.Series),
    Live("Live", "◉", NexoraContentSection.Live),
    Search("Search", "⌕", NexoraContentSection.Search),
    Settings("Settings", "⚙", NexoraContentSection.Settings)
}

@Composable
fun HomeScreen(navController: NavController) {
    var selectedMenuName by rememberSaveable { mutableStateOf(HomeMenu.Home.name) }
    val selectedMenu = runCatching { HomeMenu.valueOf(selectedMenuName) }.getOrDefault(HomeMenu.Home)
    var selectedItemId by rememberSaveable { mutableStateOf(MockContentLibrary.firstItemFor(selectedMenu.section)?.id) }
    val rows = MockContentLibrary.rowsFor(selectedMenu.section)
    val selectedItem = MockContentLibrary.findContent(selectedItemId) ?: rows.firstOrNull()?.items?.firstOrNull()

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Sidebar(
                selectedMenu = selectedMenu,
                onMenuSelected = { menu ->
                    if (selectedMenu != menu) {
                        selectedMenuName = menu.name
                        selectedItemId = MockContentLibrary.firstItemFor(menu.section)?.id
                    }
                }
            )

            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TopUtilityBar(selectedMenu)

                if (selectedMenu != HomeMenu.Settings && selectedMenu != HomeMenu.Search) {
                    HeroPanel(
                        selectedMenu = selectedMenu,
                        selectedItem = selectedItem,
                        onItemPlay = { item -> openPlayerOrDetails(navController, item) },
                        onItemDetails = { item -> openDetails(navController, item) }
                    )
                }

                if (selectedMenu == HomeMenu.Search) SearchShell()

                rows.forEach { row ->
                    ContentRow(
                        row = row,
                        selectedItemId = selectedItem?.id,
                        onItemFocused = { item -> selectedItemId = item.id },
                        onItemDetails = { item -> openDetails(navController, item) }
                    )
                }
            }
        }
    }
}

private fun openDetails(navController: NavController, item: NexoraContentItem) {
    if (item.id == PROFILE_ACCESS_SETTING_ID) {
        navController.navigate(AppDestinations.PlaylistProfile.route) { launchSingleTop = true }
    } else {
        navController.navigate(AppDestinations.Detail.createRoute(item.id)) { launchSingleTop = true }
    }
}

private fun openPlayerOrDetails(navController: NavController, item: NexoraContentItem) {
    if (item.id == PROFILE_ACCESS_SETTING_ID) {
        navController.navigate(AppDestinations.PlaylistProfile.route) { launchSingleTop = true }
    } else if (item.isPlayable) {
        openDetails(navController, item)
    } else {
        navController.navigate(AppDestinations.Detail.createRoute(item.id)) { launchSingleTop = true }
    }
}

@Composable
private fun Sidebar(selectedMenu: HomeMenu, onMenuSelected: (HomeMenu) -> Unit) {
    val menuFocusRequesters = remember {
        HomeMenu.values().associateWith { FocusRequester() }
    }

    LaunchedEffect(selectedMenu) {
        menuFocusRequesters[selectedMenu]?.requestFocus()
    }

    Column(
        modifier = Modifier
            .width(188.dp)
            .fillMaxHeight()
            .background(Color(0xAA060810), RoundedCornerShape(26.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(26.dp))
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Text("NEXORA TV", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 1.1.sp)
        Text("PLAYER ECOSYSTEMS", color = NexoraVioletSoft, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(4.dp))
        HomeMenu.values().forEach { menu ->
            MenuButton(
                icon = menu.icon,
                title = menu.label,
                selected = selectedMenu == menu,
                focusRequester = menuFocusRequesters.getValue(menu),
                onSelected = { onMenuSelected(menu) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Focus changes section • OK opens cards",
            color = Color.White.copy(alpha = 0.48f),
            fontSize = 9.sp,
            lineHeight = 12.sp,
            modifier = Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.04f), RoundedCornerShape(16.dp)).padding(10.dp)
        )
    }
}

@Composable
private fun MenuButton(
    icon: String,
    title: String,
    selected: Boolean,
    focusRequester: FocusRequester,
    onSelected: () -> Unit
) {
    Button(
        onClick = onSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) onSelected()
            }
            .then(
                if (selected) Modifier.shadow(9.dp, RoundedCornerShape(15.dp), ambientColor = NexoraViolet, spotColor = NexoraViolet) else Modifier
            ),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xCC171C30) else Color.White.copy(alpha = 0.045f),
            contentColor = Color.White
        )
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.width(24.dp).height(24.dp).background(
                    if (selected) NexoraViolet.copy(alpha = 0.20f) else Color.White.copy(alpha = 0.06f), RoundedCornerShape(8.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, color = if (selected) NexoraBlue else Color.White.copy(alpha = 0.84f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text(title, fontSize = 13.sp, fontWeight = if (selected) FontWeight.Black else FontWeight.Medium)
        }
    }
}

@Composable
private fun TopUtilityBar(selectedMenu: HomeMenu) {
    Row(
        modifier = Modifier.fillMaxWidth().height(72.dp).background(Color(0x66070A12), RoundedCornerShape(20.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(selectedMenu.label, color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("Licensed Android TV shell • remote-first navigation", color = Color.White.copy(alpha = 0.60f), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        UtilityPill("4K")
        UtilityPill("Dolby")
        UtilityPill("10:45")
    }
}

@Composable
private fun HeroPanel(selectedMenu: HomeMenu, selectedItem: NexoraContentItem?, onItemPlay: (NexoraContentItem) -> Unit, onItemDetails: (NexoraContentItem) -> Unit) {
    val item = selectedItem ?: return
    val accent = Color(item.accentColor)
    Box(
        modifier = Modifier.fillMaxWidth().height(226.dp).background(PanelDark, RoundedCornerShape(28.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(28.dp))
    ) {
        Box(Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(Color.Black.copy(alpha = 0.78f), accent.copy(alpha = 0.20f), Color.Transparent)), RoundedCornerShape(28.dp)))

        Column(
            modifier = Modifier.padding(start = 26.dp, top = 22.dp, bottom = 18.dp).width(410.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("FEATURED ${selectedMenu.label.uppercase()}", color = NexoraVioletSoft, fontSize = 10.sp, fontWeight = FontWeight.Black, letterSpacing = 1.5.sp)
            Text(item.title, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Black, lineHeight = 38.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(item.subtitle, color = Color.White.copy(alpha = 0.70f), fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(item.description, color = Color.White.copy(alpha = 0.62f), fontSize = 12.sp, lineHeight = 16.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlassActionButton(if (item.isPlayable) "Play Now" else "Open", true) { onItemPlay(item) }
                GlassActionButton("More Info", false) { onItemDetails(item) }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 22.dp).width(214.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            HeroInfoCard("Category", item.category)
            HeroInfoCard("Type", item.type.label)
            HeroInfoCard("Badge", item.badge)
        }
    }
}

@Composable
private fun ContentRow(row: NexoraContentRow, selectedItemId: String?, onItemFocused: (NexoraContentItem) -> Unit, onItemDetails: (NexoraContentItem) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(row.title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
        Text(row.subtitle, color = Color.White.copy(alpha = 0.56f), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            row.items.forEach { item ->
                LibraryCard(
                    item = item,
                    selected = selectedItemId == item.id,
                    onFocus = { onItemFocused(item) },
                    onClick = { onItemDetails(item) }
                )
            }
        }
    }
}

@Composable
private fun LibraryCard(item: NexoraContentItem, selected: Boolean, onFocus: () -> Unit, onClick: () -> Unit) {
    val accent = Color(item.accentColor)
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(186.dp)
            .height(112.dp)
            .onFocusChanged { focusState -> if (focusState.isFocused) onFocus() }
            .then(
                if (selected) Modifier.shadow(11.dp, RoundedCornerShape(20.dp), ambientColor = accent, spotColor = accent) else Modifier
            ),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CardSoft, contentColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(accent.copy(alpha = 0.22f), Color.Black.copy(alpha = 0.10f), Color.Black.copy(alpha = 0.54f))), RoundedCornerShape(20.dp)).border(if (selected) 2.dp else 1.dp, if (selected) accent.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(12.dp)
        ) {
            Text(item.badge, color = if (item.badge == "LIVE") NexoraGreen else NexoraBlue, fontSize = 9.sp, fontWeight = FontWeight.Black, modifier = Modifier.align(Alignment.TopStart))
            Text(item.title, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Black, lineHeight = 19.sp, maxLines = 2, overflow = TextOverflow.Ellipsis, modifier = Modifier.align(Alignment.CenterStart))
            Text(item.subtitle, color = Color.White.copy(alpha = 0.62f), fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.align(Alignment.BottomStart))
        }
    }
}

@Composable
private fun SearchShell() {
    Column(
        modifier = Modifier.fillMaxWidth().background(PanelDark, RoundedCornerShape(28.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(28.dp)).padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Search shell", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black)
        Text("Search input will be wired later. Current results below are clickable.", color = Color.White.copy(alpha = 0.66f), fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
private fun UtilityPill(text: String) {
    Box(Modifier.background(Color.White.copy(alpha = 0.055f), RoundedCornerShape(13.dp)).border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(13.dp)).padding(horizontal = 10.dp, vertical = 7.dp), contentAlignment = Alignment.Center) {
        Text(text, color = Color.White.copy(alpha = 0.82f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun HeroInfoCard(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().height(32.dp).background(Color.White.copy(alpha = 0.045f), RoundedCornerShape(13.dp)).border(1.dp, Color.White.copy(alpha = 0.055f), RoundedCornerShape(13.dp)).padding(horizontal = 9.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = NexoraVioletSoft, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp, modifier = Modifier.width(54.dp), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(value, color = Color.White.copy(alpha = 0.88f), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun GlassActionButton(text: String, primary: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(if (primary) 136.dp else 120.dp).height(42.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (primary) NexoraViolet else Color.White.copy(alpha = 0.075f), contentColor = Color.White)
    ) {
        Text(text, fontWeight = FontWeight.Black, fontSize = 12.sp, maxLines = 1)
    }
}

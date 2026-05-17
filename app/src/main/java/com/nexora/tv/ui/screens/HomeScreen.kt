package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.content.MockContentLibrary
import com.nexora.tv.data.content.NexoraContentItem
import com.nexora.tv.data.content.NexoraContentRow
import com.nexora.tv.data.content.NexoraContentSection
import com.nexora.tv.navigation.AppDestinations

private const val PROFILE_ACCESS_SETTING_ID = "setting-account"

private enum class HomeMenu(
    val label: String,
    val section: NexoraContentSection
) {
    Home("Home", NexoraContentSection.Home),
    Live("Live TV", NexoraContentSection.Live),
    Movies("Movies", NexoraContentSection.Movies),
    Series("Series", NexoraContentSection.Series),
    Settings("Settings", NexoraContentSection.Settings)
}

@Composable
fun HomeScreen(navController: NavController) {
    var selectedMenu by remember { mutableStateOf(HomeMenu.Home) }
    var selectedItemId by remember {
        mutableStateOf(MockContentLibrary.firstItemFor(NexoraContentSection.Home)?.id)
    }

    val rows = MockContentLibrary.rowsFor(selectedMenu.section)
    val selectedItem = MockContentLibrary.findContent(selectedItemId)
        ?: rows.firstOrNull()?.items?.firstOrNull()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundFor(selectedItem?.accentColor))
            .padding(42.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        StaticMenu(
            selectedMenu = selectedMenu,
            onMenuSelected = {
                selectedMenu = it
                selectedItemId = MockContentLibrary.firstItemFor(it.section)?.id
            }
        )
        StaticContent(
            selectedMenu = selectedMenu,
            rows = rows,
            selectedItem = selectedItem,
            onItemFocused = { selectedItemId = it.id },
            onItemOpen = {
                when {
                    it.id == PROFILE_ACCESS_SETTING_ID -> {
                        navController.navigate(AppDestinations.PlaylistProfile.route) {
                            launchSingleTop = true
                        }
                    }

                    it.isPlayable -> {
                        navController.navigate(AppDestinations.Detail.createRoute(it.id)) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun StaticMenu(
    selectedMenu: HomeMenu,
    onMenuSelected: (HomeMenu) -> Unit
) {
    Column(
        modifier = Modifier.width(180.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "NEXORA",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Premium TV",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        HomeMenu.values().forEach { menu ->
            MenuButton(
                title = menu.label,
                selected = selectedMenu == menu,
                onSelected = { onMenuSelected(menu) }
            )
        }
    }
}

@Composable
private fun MenuButton(
    title: String,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Button(
        onClick = onSelected,
        modifier = Modifier
            .width(180.dp)
            .height(50.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF123A46) else Color(0xFF141821),
            contentColor = if (selected) Color.White else Color.LightGray
        )
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun StaticContent(
    selectedMenu: HomeMenu,
    rows: List<NexoraContentRow>,
    selectedItem: NexoraContentItem?,
    onItemFocused: (NexoraContentItem) -> Unit,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        StaticHeader(selectedMenu, selectedItem)

        rows.forEach { row ->
            ContentRow(
                row = row,
                selectedItemId = selectedItem?.id,
                onItemFocused = onItemFocused,
                onItemOpen = onItemOpen
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            StaticStatusTile("Library", "Mock/local")
            StaticStatusTile("Routes", "Detail ready")
            StaticStatusTile("Streams", "No real source")
        }
    }
}

@Composable
private fun StaticHeader(selectedMenu: HomeMenu, selectedItem: NexoraContentItem?) {
    val accent = Color(selectedItem?.accentColor ?: 0xFF00E5FF)

    Column(
        modifier = Modifier
            .width(850.dp)
            .height(164.dp)
            .background(Color(0xFF111722), RoundedCornerShape(28.dp))
            .border(1.dp, accent, RoundedCornerShape(28.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = selectedMenu.label,
            color = accent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = selectedItem?.title ?: "Content Library",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = selectedItem?.description ?: "Safe mock content library foundation.",
            color = Color.LightGray,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun ContentRow(
    row: NexoraContentRow,
    selectedItemId: String?,
    onItemFocused: (NexoraContentItem) -> Unit,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = row.title,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = row.subtitle,
            color = Color.LightGray,
            fontSize = 12.sp
        )

        Row(
            modifier = Modifier
                .width(890.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            row.items.forEach { item ->
                ContentCard(
                    item = item,
                    selected = selectedItemId == item.id,
                    onSelected = {
                        onItemFocused(item)
                        onItemOpen(item)
                    }
                )
            }
        }
    }
}

@Composable
private fun ContentCard(
    item: NexoraContentItem,
    selected: Boolean,
    onSelected: () -> Unit
) {
    val accent = Color(item.accentColor)

    Button(
        onClick = onSelected,
        modifier = Modifier
            .width(220.dp)
            .height(292.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) accent.copy(alpha = 0.34f) else Color(0xFF151A24),
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    2.dp,
                    if (selected) accent else Color(0x22FFFFFF),
                    RoundedCornerShape(24.dp)
                )
                .padding(18.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = item.badge,
                    color = accent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.subtitle,
                    color = Color.LightGray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun StaticStatusTile(title: String, body: String) {
    Column(
        modifier = Modifier
            .width(210.dp)
            .height(74.dp)
            .background(Color(0xFF111722), RoundedCornerShape(20.dp))
            .border(1.dp, Color(0x22FFFFFF), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
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

private fun backgroundFor(accentColor: Long?): Color = when (accentColor) {
    0xFF00E5FF -> Color(0xFF061A22)
    0xFF2962FF -> Color(0xFF071226)
    0xFF7C4DFF -> Color(0xFF130D2A)
    0xFF00BFA5 -> Color(0xFF06211D)
    0xFFFF6D00 -> Color(0xFF241206)
    else -> Color(0xFF06111D)
}

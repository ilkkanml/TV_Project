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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
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
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private const val PROFILE_ACCESS_SETTING_ID = "setting-account"
private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xAA11131C)

private enum class HomeMenu(
    val label: String,
    val section: NexoraContentSection
) {
    Home("Home", NexoraContentSection.Home),
    Movies("Movies", NexoraContentSection.Movies),
    Series("Series", NexoraContentSection.Series),
    Live("Live", NexoraContentSection.Live),
    Search("Search", NexoraContentSection.Search),
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

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 34.dp, top = 32.dp, end = 38.dp, bottom = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Sidebar(
                selectedMenu = selectedMenu,
                onMenuSelected = {
                    selectedMenu = it
                    selectedItemId = MockContentLibrary.firstItemFor(it.section)?.id
                }
            )
            ContentStage(
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
}

@Composable
private fun Sidebar(
    selectedMenu: HomeMenu,
    onMenuSelected: (HomeMenu) -> Unit
) {
    Column(
        modifier = Modifier
            .width(218.dp)
            .fillMaxHeight()
            .background(Color(0x9905060B), RoundedCornerShape(34.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(34.dp))
            .padding(horizontal = 18.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "NEXORA",
            color = Color.White,
            fontSize = 31.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
        Text(
            text = "Licensed",
            color = NexoraVioletSoft,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        HomeMenu.values().forEach { menu ->
            MenuButton(
                title = menu.label,
                selected = selectedMenu == menu,
                onSelected = { onMenuSelected(menu) }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .width(180.dp)
                .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(24.dp))
                .border(1.dp, NexoraViolet.copy(alpha = 0.35f), RoundedCornerShape(24.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Internal Alpha",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Licensed-ready shell",
                color = Color.White.copy(alpha = 0.62f),
                fontSize = 11.sp
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
            .width(182.dp)
            .height(52.dp)
            .then(
                if (selected) {
                    Modifier.shadow(
                        elevation = 18.dp,
                        shape = RoundedCornerShape(18.dp),
                        ambientColor = NexoraViolet,
                        spotColor = NexoraViolet
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) NexoraViolet.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.055f),
            contentColor = Color.White
        )
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Black else FontWeight.Medium
        )
    }
}

@Composable
private fun ContentStage(
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
        HeroPanel(selectedMenu, selectedItem, onItemOpen)

        rows.forEach { row ->
            ContentRow(
                row = row,
                selectedItemId = selectedItem?.id,
                onItemFocused = onItemFocused,
                onItemOpen = onItemOpen
            )
        }
    }
}

@Composable
private fun HeroPanel(
    selectedMenu: HomeMenu,
    selectedItem: NexoraContentItem?,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    Box(
        modifier = Modifier
            .width(940.dp)
            .height(250.dp)
            .background(PanelDark, RoundedCornerShape(34.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(34.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.74f),
                            NexoraViolet.copy(alpha = 0.20f),
                            Color.Transparent
                        )
                    ),
                    RoundedCornerShape(34.dp)
                )
        )

        Column(
            modifier = Modifier
                .width(560.dp)
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = selectedMenu.label.uppercase(),
                color = NexoraVioletSoft,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Text(
                text = selectedItem?.title ?: "Nexora Library",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 42.sp
            )
            Text(
                text = selectedItem?.description ?: "A calm, licensed-ready TV library shell for live, movies and series.",
                color = Color.White.copy(alpha = 0.72f),
                fontSize = 15.sp,
                lineHeight = 21.sp
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (selectedItem?.isPlayable == true) {
                    Button(
                        onClick = { onItemOpen(selectedItem) },
                        modifier = Modifier
                            .width(136.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NexoraViolet,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Open", fontWeight = FontWeight.Bold)
                    }
                }
                Box(
                    modifier = Modifier
                        .width(116.dp)
                        .height(48.dp)
                        .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(18.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(18.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Licensed",
                        color = Color.White.copy(alpha = 0.86f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun ContentRow(
    row: NexoraContentRow,
    selectedItemId: String?,
    onItemFocused: (NexoraContentItem) -> Unit,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.width(940.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = row.title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = row.subtitle,
                color = Color.White.copy(alpha = 0.48f),
                fontSize = 12.sp
            )
        }

        Row(
            modifier = Modifier
                .width(940.dp)
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
    Button(
        onClick = onSelected,
        modifier = Modifier
            .width(216.dp)
            .height(142.dp)
            .then(
                if (selected) {
                    Modifier.shadow(
                        elevation = 18.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = NexoraViolet,
                        spotColor = NexoraViolet
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) NexoraViolet.copy(alpha = 0.34f) else PanelSoft,
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = if (selected) 2.dp else 1.dp,
                    color = if (selected) NexoraVioletSoft else Color.White.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(24.dp)
                )
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.06f),
                            Color.Black.copy(alpha = 0.22f)
                        )
                    ),
                    RoundedCornerShape(24.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = item.badge,
                    color = NexoraVioletSoft,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 20.sp
                )
                Text(
                    text = item.subtitle,
                    color = Color.White.copy(alpha = 0.58f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
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
private val PanelSoft = Color(0xB0121725)
private val CardSoft = Color(0x80131A29)

private enum class HomeMenu(
    val label: String,
    val icon: String,
    val section: NexoraContentSection
) {
    Home("Home", "⌂", NexoraContentSection.Home),
    Movies("Movies", "◫", NexoraContentSection.Movies),
    Series("Series", "▤", NexoraContentSection.Series),
    Live("Live", "◉", NexoraContentSection.Live),
    Search("Search", "⌕", NexoraContentSection.Search),
    Settings("Settings", "⚙", NexoraContentSection.Settings)
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
                .padding(
                    start = 18.dp,
                    top = 18.dp,
                    end = 18.dp,
                    bottom = 18.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Sidebar(
                selectedMenu = selectedMenu,
                onMenuSelected = {
                    selectedMenu = it
                    selectedItemId = MockContentLibrary.firstItemFor(it.section)?.id
                }
            )

            MainStage(
                modifier = Modifier.weight(1f),
                selectedMenu = selectedMenu,
                rows = rows,
                selectedItem = selectedItem,
                onItemFocused = {
                    selectedItemId = it.id
                },
                onItemOpen = { item ->
                    when {
                        item.id == PROFILE_ACCESS_SETTING_ID -> {
                            navController.navigate(AppDestinations.PlaylistProfile.route) {
                                launchSingleTop = true
                            }
                        }

                        item.isPlayable -> {
                            navController.navigate(AppDestinations.Detail.createRoute(item.id)) {
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
            .width(198.dp)
            .fillMaxHeight()
            .background(
                Color(0xAA060810),
                RoundedCornerShape(28.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(horizontal = 14.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = "NEXORA TV",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.2.sp
            )

            Text(
                text = "PLAYER ECOSYSTEMS",
                color = NexoraVioletSoft,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        HomeMenu.values().forEach { menu ->
            MenuButton(
                icon = menu.icon,
                title = menu.label,
                selected = selectedMenu == menu,
                onSelected = {
                    onMenuSelected(menu)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.07f),
                            Color.White.copy(alpha = 0.035f)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color = NexoraViolet.copy(alpha = 0.26f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Alex",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "Premium profile",
                color = NexoraVioletSoft,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "TV-first layout",
                color = Color.White.copy(alpha = 0.58f),
                fontSize = 10.sp,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun MenuButton(
    icon: String,
    title: String,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Button(
        onClick = onSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .then(
                if (selected) {
                    Modifier.shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = NexoraViolet,
                        spotColor = NexoraViolet
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) {
                Color(0xCC171C30)
            } else {
                Color.White.copy(alpha = 0.045f)
            },
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 10.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(26.dp)
                    .height(26.dp)
                    .background(
                        color = if (selected) {
                            NexoraViolet.copy(alpha = 0.20f)
                        } else {
                            Color.White.copy(alpha = 0.06f)
                        },
                        shape = RoundedCornerShape(9.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    color = if (selected) {
                        NexoraBlue
                    } else {
                        Color.White.copy(alpha = 0.84f)
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (selected) {
                    FontWeight.Black
                } else {
                    FontWeight.Medium
                }
            )
        }
    }
}

@Composable
private fun MainStage(
    modifier: Modifier = Modifier,
    selectedMenu: HomeMenu,
    rows: List<NexoraContentRow>,
    selectedItem: NexoraContentItem?,
    onItemFocused: (NexoraContentItem) -> Unit,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        TopUtilityBar(selectedMenu)

        when (selectedMenu) {
            HomeMenu.Search -> {
                SearchStage(
                    rows = rows,
                    onItemFocused = onItemFocused,
                    onItemOpen = onItemOpen
                )
            }

            HomeMenu.Settings -> {
                SettingsStage(
                    rows = rows,
                    onItemOpen = onItemOpen
                )
            }

            else -> {
                LibraryStage(
                    selectedMenu = selectedMenu,
                    rows = rows,
                    selectedItem = selectedItem,
                    onItemFocused = onItemFocused,
                    onItemOpen = onItemOpen
                )
            }
        }
    }
}

@Composable
private fun TopUtilityBar(selectedMenu: HomeMenu) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0x66070A12),
                shape = RoundedCornerShape(22.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = selectedMenu.label,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Premium Android TV shell • remote-first navigation",
                color = Color.White.copy(alpha = 0.62f),
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UtilityPill("4K")
            UtilityPill("Dolby UI")
            UtilityPill("10:45 PM")
        }
    }
}

@Composable
private fun UtilityPill(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color.White.copy(alpha = 0.055f),
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.07f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 11.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.82f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LibraryStage(
    selectedMenu: HomeMenu,
    rows: List<NexoraContentRow>,
    selectedItem: NexoraContentItem?,
    onItemFocused: (NexoraContentItem) -> Unit,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    HeroPanel(
        selectedMenu = selectedMenu,
        selectedItem = selectedItem,
        onItemOpen = onItemOpen
    )

    rows.forEach { row ->
        ContentRow(
            row = row,
            selectedItemId = selectedItem?.id,
            onItemFocused = onItemFocused,
            onItemOpen = onItemOpen
        )
    }
}

@Composable
private fun HeroPanel(
    selectedMenu: HomeMenu,
    selectedItem: NexoraContentItem?,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    val item = selectedItem ?: return
    val accent = Color(item.accentColor)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(264.dp)
            .background(
                color = PanelDark,
                shape = RoundedCornerShape(30.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.10f),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.78f),
                            accent.copy(alpha = 0.24f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            accent.copy(alpha = 0.24f),
                            Color.Transparent
                        ),
                        center = Offset(760f, 92f),
                        radius = 360f
                    )
                )
        )

        Column(
            modifier = Modifier
                .padding(
                    start = 28.dp,
                    top = 24.dp,
                    end = 28.dp,
                    bottom = 22.dp
                )
                .width(390.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = "FEATURED ${selectedMenu.label.uppercase()}",
                color = NexoraVioletSoft,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.6.sp
            )

            Text(
                text = item.title,
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 45.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.subtitle,
                color = Color.White.copy(alpha = 0.70f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.description,
                color = Color.White.copy(alpha = 0.64f),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassActionButton(
                    text = if (item.isPlayable) "Play Now" else "Open",
                    primary = true,
                    onClick = {
                        onItemOpen(item)
                    }
                )

                GlassActionButton(
                    text = "More Info",
                    primary = false,
                    onClick = {
                        onItemOpen(item)
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp)
                .width(230.dp),
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            HeroInfoCard("Category", item.category)
            HeroInfoCard("Type", item.type.label)
            HeroInfoCard("Badge", item.badge)
        }
    }
}

@Composable
private fun HeroInfoCard(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.045f),
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.055f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 10.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = NexoraVioletSoft,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.7.sp,
            modifier = Modifier.width(56.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = value,
            color = Color.White.copy(alpha = 0.88f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun GlassActionButton(
    text: String,
    primary: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(if (primary) 142.dp else 126.dp)
            .height(46.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (primary) {
                NexoraViolet
            } else {
                Color.White.copy(alpha = 0.075f)
            },
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Black,
            fontSize = 13.sp
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
    Column(
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = row.title,
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = row.subtitle,
                color = Color.White.copy(alpha = 0.56f),
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            row.items.forEach { item ->
                LibraryCard(
                    item = item,
                    selected = selectedItemId == item.id,
                    onFocus = {
                        onItemFocused(item)
                    },
                    onOpen = {
                        onItemOpen(item)
                    }
                )
            }
        }
    }
}

@Composable
private fun LibraryCard(
    item: NexoraContentItem,
    selected: Boolean,
    onFocus: () -> Unit,
    onOpen: () -> Unit
) {
    val accent = Color(item.accentColor)

    Button(
        onClick = {
            onFocus()
            onOpen()
        },
        modifier = Modifier
            .width(206.dp)
            .height(128.dp)
            .then(
                if (selected) {
                    Modifier.shadow(
                        elevation = 13.dp,
                        shape = RoundedCornerShape(22.dp),
                        ambientColor = accent,
                        spotColor = accent
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CardSoft,
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            accent.copy(alpha = 0.24f),
                            Color.Black.copy(alpha = 0.08f),
                            Color.Black.copy(alpha = 0.54f)
                        )
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
                .border(
                    width = if (selected) 2.dp else 1.dp,
                    color = if (selected) {
                        accent.copy(alpha = 0.95f)
                    } else {
                        Color.White.copy(alpha = 0.08f)
                    },
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(14.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = item.badge,
                    color = if (item.badge == "LIVE") {
                        NexoraGreen
                    } else {
                        NexoraBlue
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 21.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = item.subtitle,
                color = Color.White.copy(alpha = 0.62f),
                fontSize = 11.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
private fun SearchStage(
    rows: List<NexoraContentRow>,
    onItemFocused: (NexoraContentItem) -> Unit,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = PanelDark,
                    shape = RoundedCornerShape(30.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.10f),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Search your library",
                    color = Color.White,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "Type search terms later. For now this is a polished search shell for Android TV remote flows.",
                    color = Color.White.copy(alpha = 0.66f),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UtilityPill("Recently viewed")
                    UtilityPill("Series")
                    UtilityPill("Live")
                    UtilityPill("Action")
                }
            }
        }

        rows.forEach { row ->
            ContentRow(
                row = row,
                selectedItemId = null,
                onItemFocused = onItemFocused,
                onItemOpen = onItemOpen
            )
        }
    }
}

@Composable
private fun SettingsStage(
    rows: List<NexoraContentRow>,
    onItemOpen: (NexoraContentItem) -> Unit
) {
    val settings = rows.firstOrNull()?.items.orEmpty()

    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsSummaryCard(
                title = "Profile",
                body = "Alex Premium • active device"
            )

            SettingsSummaryCard(
                title = "Playback",
                body = "Auto quality • UI shell"
            )

            SettingsSummaryCard(
                title = "Security",
                body = "No hidden APIs • legal boundary"
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            settings.forEach { item ->
                SettingsOptionCard(
                    item = item,
                    onOpen = {
                        onItemOpen(item)
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsSummaryCard(
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .width(280.dp)
            .background(
                color = PanelSoft,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = title,
            color = NexoraVioletSoft,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )

        Text(
            text = body,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 21.sp
        )
    }
}

@Composable
private fun SettingsOptionCard(
    item: NexoraContentItem,
    onOpen: () -> Unit
) {
    Button(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp),
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PanelSoft,
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = item.description,
                    color = Color.White.copy(alpha = 0.60f),
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = "›",
                color = NexoraVioletSoft,
                fontSize = 25.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

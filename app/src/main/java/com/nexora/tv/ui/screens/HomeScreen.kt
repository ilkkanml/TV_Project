package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private enum class HomeMenu(val label: String) {
    Home("Home"),
    Live("Live TV"),
    Movies("Movies"),
    Series("Series"),
    Settings("Settings")
}

private data class MenuPoster(
    val title: String,
    val subtitle: String,
    val accent: Color
)

@Composable
fun HomeScreen(navController: NavController) {
    var selectedMenu by remember { mutableStateOf(HomeMenu.Home) }
    var focusedAccent by remember { mutableStateOf(Color(0xFF00E5FF)) }
    val posters = menuPosters(selectedMenu)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundFor(focusedAccent))
            .padding(42.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
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
                MenuItem(
                    title = menu.label,
                    selected = selectedMenu == menu,
                    onSelect = { selectedMenu = menu }
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeaderPanel(selectedMenu, focusedAccent)

            Text(
                text = rowTitle(selectedMenu),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                posters.forEach { poster ->
                    PosterCard(
                        poster = poster,
                        onFocus = { focusedAccent = poster.accent }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                StatusTile("Loading", "Visible")
                StatusTile("Empty", "Visible")
                StatusTile("Error", "Visible")
            }
        }
    }
}

private fun backgroundFor(accent: Color): Color = when (accent) {
    Color(0xFF00E5FF) -> Color(0xFF061A22)
    Color(0xFF2962FF) -> Color(0xFF071226)
    Color(0xFF7C4DFF) -> Color(0xFF130D2A)
    Color(0xFF00BFA5) -> Color(0xFF06211D)
    Color(0xFFFF6D00) -> Color(0xFF241206)
    else -> Color.Black
}

private fun rowTitle(menu: HomeMenu): String = when (menu) {
    HomeMenu.Home -> "Featured Library"
    HomeMenu.Live -> "Live TV Categories"
    HomeMenu.Movies -> "Movies"
    HomeMenu.Series -> "Series"
    HomeMenu.Settings -> "Settings"
}

private fun menuPosters(menu: HomeMenu): List<MenuPoster> = when (menu) {
    HomeMenu.Home -> listOf(
        MenuPoster("Featured", "Main library highlight", Color(0xFF00E5FF)),
        MenuPoster("New Added", "Fresh mock content", Color(0xFF2962FF)),
        MenuPoster("Watch Later", "Saved placeholder", Color(0xFF7C4DFF))
    )
    HomeMenu.Live -> listOf(
        MenuPoster("News", "Live category placeholder", Color(0xFF00E5FF)),
        MenuPoster("Sports", "Licensed channel shell", Color(0xFFFF6D00)),
        MenuPoster("Cinema", "Linear TV row", Color(0xFF2962FF))
    )
    HomeMenu.Movies -> listOf(
        MenuPoster("Continue Movie", "Resume belongs here", Color(0xFF00E5FF)),
        MenuPoster("Orbit Fall", "Movie poster shell", Color(0xFF7C4DFF)),
        MenuPoster("Glass Tower", "Featured VOD", Color(0xFF2962FF))
    )
    HomeMenu.Series -> listOf(
        MenuPoster("Continue Series", "Episode 4 • 42 min left", Color(0xFF00E5FF)),
        MenuPoster("Midnight Grid", "Series poster shell", Color(0xFF7C4DFF)),
        MenuPoster("Deep Archive", "New season", Color(0xFF00BFA5))
    )
    HomeMenu.Settings -> listOf(
        MenuPoster("Account", "Device access shell", Color(0xFF00E5FF)),
        MenuPoster("Playback", "Preference placeholder", Color(0xFF2962FF)),
        MenuPoster("Display", "TV layout shell", Color(0xFF7C4DFF))
    )
}

@Composable
private fun MenuItem(
    title: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val active = focused || selected

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(50.dp)
            .background(
                if (active) Color(0x3329E7FF) else Color(0xFF141821),
                RoundedCornerShape(18.dp)
            )
            .border(
                1.dp,
                if (active) Color(0xFF00E5FF) else Color(0x22FFFFFF),
                RoundedCornerShape(18.dp)
            )
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .clickable { onSelect() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = title,
            color = if (active) Color.White else Color.LightGray,
            fontSize = 15.sp,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun HeaderPanel(menu: HomeMenu, accent: Color) {
    Column(
        modifier = Modifier
            .width(850.dp)
            .height(154.dp)
            .background(Color(0xFF111722), RoundedCornerShape(28.dp))
            .border(1.dp, accent, RoundedCornerShape(28.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = menu.label,
            color = accent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Nexora TV Home",
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Stable menu/category runtime test. Mock legal content only.",
            color = Color.LightGray,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun PosterCard(
    poster: MenuPoster,
    onFocus: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(width = 220.dp, height = 320.dp)
            .border(
                2.dp,
                if (focused) poster.accent else Color(0x22FFFFFF),
                RoundedCornerShape(24.dp)
            )
            .onFocusChanged {
                focused = it.isFocused
                if (it.isFocused) onFocus()
            }
            .focusable(),
        colors = CardDefaults.cardColors(
            containerColor = if (focused) poster.accent.copy(alpha = 0.32f) else Color(0xFF151A24)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = poster.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = poster.subtitle,
                    color = Color.LightGray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun StatusTile(title: String, body: String) {
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

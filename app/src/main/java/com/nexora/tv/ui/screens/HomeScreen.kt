package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

private data class StaticPosterModel(
    val title: String,
    val subtitle: String,
    val accent: Color
)

@Composable
fun HomeScreen(navController: NavController) {
    var selectedMenu by remember { mutableStateOf(HomeMenu.Home) }
    val posters = postersFor(selectedMenu)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06111D))
            .padding(42.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        StaticMenu(
            selectedMenu = selectedMenu,
            onMenuSelected = { selectedMenu = it }
        )
        StaticContent(
            selectedMenu = selectedMenu,
            posters = posters
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
    posters: List<StaticPosterModel>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        StaticHeader(selectedMenu)

        Text(
            text = rowTitle(selectedMenu),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            posters.forEach { poster ->
                StaticPoster(
                    title = poster.title,
                    subtitle = poster.subtitle,
                    accent = poster.accent
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            StaticStatusTile("Loading", "Visible")
            StaticStatusTile("Empty", "Visible")
            StaticStatusTile("Error", "Visible")
        }
    }
}

@Composable
private fun StaticHeader(selectedMenu: HomeMenu) {
    Column(
        modifier = Modifier
            .width(850.dp)
            .height(154.dp)
            .background(Color(0xFF111722), RoundedCornerShape(28.dp))
            .border(1.dp, Color(0xFF00E5FF), RoundedCornerShape(28.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = selectedMenu.label,
            color = Color(0xFF00E5FF),
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
            text = "Safe Material button menu test. No custom focus modifier.",
            color = Color.LightGray,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun StaticPoster(title: String, subtitle: String, accent: Color) {
    Box(
        modifier = Modifier
            .size(width = 220.dp, height = 320.dp)
            .background(Color(0xFF151A24), RoundedCornerShape(24.dp))
            .border(2.dp, accent, RoundedCornerShape(24.dp))
            .padding(18.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = Color.LightGray,
                fontSize = 13.sp
            )
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

private fun rowTitle(menu: HomeMenu): String = when (menu) {
    HomeMenu.Home -> "Featured Library"
    HomeMenu.Live -> "Live TV Categories"
    HomeMenu.Movies -> "Movies"
    HomeMenu.Series -> "Series"
    HomeMenu.Settings -> "Settings"
}

private fun postersFor(menu: HomeMenu): List<StaticPosterModel> = when (menu) {
    HomeMenu.Home -> listOf(
        StaticPosterModel("Featured", "Main library highlight", Color(0xFF00E5FF)),
        StaticPosterModel("New Added", "Fresh mock content", Color(0xFF2962FF)),
        StaticPosterModel("Watch Later", "Saved placeholder", Color(0xFF7C4DFF))
    )
    HomeMenu.Live -> listOf(
        StaticPosterModel("News", "Live category placeholder", Color(0xFF00E5FF)),
        StaticPosterModel("Sports", "Licensed channel shell", Color(0xFFFF6D00)),
        StaticPosterModel("Cinema", "Linear TV row", Color(0xFF2962FF))
    )
    HomeMenu.Movies -> listOf(
        StaticPosterModel("Continue Movie", "Resume belongs here", Color(0xFF00E5FF)),
        StaticPosterModel("Orbit Fall", "Movie poster shell", Color(0xFF7C4DFF)),
        StaticPosterModel("Glass Tower", "Featured VOD", Color(0xFF2962FF))
    )
    HomeMenu.Series -> listOf(
        StaticPosterModel("Continue Series", "Episode 4 • 42 min left", Color(0xFF00E5FF)),
        StaticPosterModel("Midnight Grid", "Series poster shell", Color(0xFF7C4DFF)),
        StaticPosterModel("Deep Archive", "New season", Color(0xFF00BFA5))
    )
    HomeMenu.Settings -> listOf(
        StaticPosterModel("Account", "Device access shell", Color(0xFF00E5FF)),
        StaticPosterModel("Playback", "Preference placeholder", Color(0xFF2962FF)),
        StaticPosterModel("Display", "TV layout shell", Color(0xFF7C4DFF))
    )
}

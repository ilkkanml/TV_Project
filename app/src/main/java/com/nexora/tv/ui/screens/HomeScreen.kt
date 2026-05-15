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
    var selectedPosterIndex by remember { mutableIntStateOf(0) }
    val posters = postersFor(selectedMenu)
    val selectedPoster = posters.getOrElse(selectedPosterIndex) { posters.first() }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundFor(selectedPoster.accent))
            .padding(42.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        StaticMenu(
            selectedMenu = selectedMenu,
            onMenuSelected = {
                selectedMenu = it
                selectedPosterIndex = 0
            }
        )
        StaticContent(
            selectedMenu = selectedMenu,
            posters = posters,
            selectedPosterIndex = selectedPosterIndex,
            onPosterSelected = { selectedPosterIndex = it }
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
    posters: List<StaticPosterModel>,
    selectedPosterIndex: Int,
    onPosterSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        StaticHeader(selectedMenu, posters[selectedPosterIndex])

        Text(
            text = rowTitle(selectedMenu),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .width(890.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            posters.forEachIndexed { index, poster ->
                PosterButton(
                    poster = poster,
                    selected = selectedPosterIndex == index,
                    onSelected = { onPosterSelected(index) }
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
private fun StaticHeader(selectedMenu: HomeMenu, selectedPoster: StaticPosterModel) {
    Column(
        modifier = Modifier
            .width(850.dp)
            .height(154.dp)
            .background(Color(0xFF111722), RoundedCornerShape(28.dp))
            .border(1.dp, selectedPoster.accent, RoundedCornerShape(28.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = selectedMenu.label,
            color = selectedPoster.accent,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = selectedPoster.title,
            color = Color.White,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = selectedPoster.subtitle,
            color = Color.LightGray,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun PosterButton(
    poster: StaticPosterModel,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Button(
        onClick = onSelected,
        modifier = Modifier
            .width(220.dp)
            .height(320.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) poster.accent.copy(alpha = 0.34f) else Color(0xFF151A24),
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    2.dp,
                    if (selected) poster.accent else Color(0x22FFFFFF),
                    RoundedCornerShape(24.dp)
                )
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

private fun backgroundFor(accent: Color): Color = when (accent) {
    Color(0xFF00E5FF) -> Color(0xFF061A22)
    Color(0xFF2962FF) -> Color(0xFF071226)
    Color(0xFF7C4DFF) -> Color(0xFF130D2A)
    Color(0xFF00BFA5) -> Color(0xFF06211D)
    Color(0xFFFF6D00) -> Color(0xFF241206)
    else -> Color(0xFF06111D)
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
        StaticPosterModel("Watch Later", "Saved placeholder", Color(0xFF7C4DFF)),
        StaticPosterModel("Top Rated", "Large TV poster layout", Color(0xFF00BFA5)),
        StaticPosterModel("Family Row", "Safe category preview", Color(0xFFFF6D00))
    )
    HomeMenu.Live -> listOf(
        StaticPosterModel("News", "Live category placeholder", Color(0xFF00E5FF)),
        StaticPosterModel("Sports", "Licensed channel shell", Color(0xFFFF6D00)),
        StaticPosterModel("Cinema", "Linear TV row", Color(0xFF2962FF)),
        StaticPosterModel("Documentary", "Channel category", Color(0xFF00BFA5)),
        StaticPosterModel("Kids", "Family category", Color(0xFF7C4DFF))
    )
    HomeMenu.Movies -> listOf(
        StaticPosterModel("Continue Movie", "Resume belongs here", Color(0xFF00E5FF)),
        StaticPosterModel("Orbit Fall", "Movie poster shell", Color(0xFF7C4DFF)),
        StaticPosterModel("Glass Tower", "Featured VOD", Color(0xFF2962FF)),
        StaticPosterModel("Silent Coast", "Watch later", Color(0xFF00BFA5)),
        StaticPosterModel("Afterglow", "Movie library shell", Color(0xFFFF6D00))
    )
    HomeMenu.Series -> listOf(
        StaticPosterModel("Continue Series", "Episode 4 • 42 min left", Color(0xFF00E5FF)),
        StaticPosterModel("Midnight Grid", "Series poster shell", Color(0xFF7C4DFF)),
        StaticPosterModel("Deep Archive", "New season", Color(0xFF00BFA5)),
        StaticPosterModel("Signal Room", "Episode row", Color(0xFF2962FF)),
        StaticPosterModel("Blue District", "Series library shell", Color(0xFFFF6D00))
    )
    HomeMenu.Settings -> listOf(
        StaticPosterModel("Account", "Device access shell", Color(0xFF00E5FF)),
        StaticPosterModel("Playback", "Preference placeholder", Color(0xFF2962FF)),
        StaticPosterModel("Display", "TV layout shell", Color(0xFF7C4DFF)),
        StaticPosterModel("Support", "Help placeholder", Color(0xFF00BFA5)),
        StaticPosterModel("About", "App information", Color(0xFFFF6D00))
    )
}

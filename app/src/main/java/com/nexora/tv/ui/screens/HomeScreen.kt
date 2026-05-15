package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.theme.NexoraColors

private enum class HomeSection(val label: String) {
    Home("Home"),
    Live("Live TV"),
    Movies("Movies"),
    Series("Series"),
    Settings("Settings")
}

private data class RuntimePoster(
    val title: String,
    val subtitle: String,
    val accent: Color
)

@Composable
fun HomeScreen(navController: NavController) {
    var selectedSection by remember { mutableStateOf(HomeSection.Home) }
    var focusedAccent by remember { mutableStateOf(NexoraColors.Cyan) }
    val posters = remember(selectedSection) { postersFor(selectedSection) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        focusedAccent.copy(alpha = 0.30f),
                        Color(0xFF07101D),
                        NexoraColors.Black
                    )
                )
            )
            .padding(horizontal = 42.dp, vertical = 34.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        NavigationShell(
            selectedSection = selectedSection,
            onSectionFocused = { selectedSection = it }
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            HeaderBlock(
                selectedSection = selectedSection,
                focusedAccent = focusedAccent,
                onOpenPlayer = {
                    navController.navigate(AppDestinations.Player.route) {
                        launchSingleTop = true
                    }
                }
            )

            Text(
                text = sectionRowTitle(selectedSection),
                color = NexoraColors.TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(start = 4.dp, end = 120.dp)
            ) {
                itemsIndexed(
                    items = posters,
                    key = { index, item -> "${selectedSection.label}-$index-${item.title}" }
                ) { _, poster ->
                    RuntimePosterCard(
                        poster = poster,
                        onFocused = { focusedAccent = poster.accent }
                    )
                }
            }

            StateTiles()
        }
    }
}

private fun sectionRowTitle(section: HomeSection): String = when (section) {
    HomeSection.Home -> "Featured Library"
    HomeSection.Live -> "Live TV Categories"
    HomeSection.Movies -> "Movies • Continue Watching"
    HomeSection.Series -> "Series • Continue Watching"
    HomeSection.Settings -> "Settings Preview"
}

private fun postersFor(section: HomeSection): List<RuntimePoster> = when (section) {
    HomeSection.Home -> listOf(
        RuntimePoster("Featured One", "Premium home highlight", NexoraColors.Cyan),
        RuntimePoster("Tonight Picks", "Curated mock content", NexoraColors.Blue),
        RuntimePoster("Recently Added", "Fresh content row", Color(0xFF7C4DFF)),
        RuntimePoster("Watch Later", "Saved mock library", Color(0xFF00BFA5)),
        RuntimePoster("Top Rated", "Large TV poster layout", Color(0xFFFF6D00))
    )

    HomeSection.Live -> listOf(
        RuntimePoster("News", "Live category placeholder", NexoraColors.Cyan),
        RuntimePoster("Sports", "Live event placeholder", Color(0xFFFFD600)),
        RuntimePoster("Cinema", "Linear channel row", NexoraColors.Blue),
        RuntimePoster("Documentary", "Licensed source ready", Color(0xFF64DD17)),
        RuntimePoster("Kids", "Family category", Color(0xFFFF4081))
    )

    HomeSection.Movies -> listOf(
        RuntimePoster("Continue Movie", "Resume mock progress", NexoraColors.Cyan),
        RuntimePoster("Orbit Fall", "Premium movie poster", Color(0xFF7C4DFF)),
        RuntimePoster("Glass Tower", "Featured VOD", NexoraColors.Blue),
        RuntimePoster("Silent Coast", "Watch later", Color(0xFF00BFA5)),
        RuntimePoster("Afterglow", "Movie library shell", Color(0xFFFF6D00))
    )

    HomeSection.Series -> listOf(
        RuntimePoster("Continue Series", "Episode 4 • 42 min left", NexoraColors.Cyan),
        RuntimePoster("Midnight Grid", "Series poster shell", Color(0xFF7C4DFF)),
        RuntimePoster("Deep Archive", "New season", NexoraColors.Blue),
        RuntimePoster("Signal Room", "Episode row", Color(0xFF00BFA5)),
        RuntimePoster("Blue District", "Series library shell", Color(0xFF536DFE))
    )

    HomeSection.Settings -> listOf(
        RuntimePoster("Account", "Device access placeholder", NexoraColors.Cyan),
        RuntimePoster("Playback", "Player preference shell", NexoraColors.Blue),
        RuntimePoster("Display", "TV layout setting", Color(0xFF7C4DFF)),
        RuntimePoster("Support", "Help placeholder", Color(0xFF00BFA5))
    )
}

@Composable
private fun NavigationShell(
    selectedSection: HomeSection,
    onSectionFocused: (HomeSection) -> Unit
) {
    Column(
        modifier = Modifier.width(178.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "NEXORA",
            color = NexoraColors.TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Premium TV",
            color = NexoraColors.TextSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        HomeSection.values().forEach { section ->
            NavItem(
                label = section.label,
                selected = selectedSection == section,
                onFocused = { onSectionFocused(section) }
            )
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    selected: Boolean,
    onFocused: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val borderColor = when {
        focused -> NexoraColors.Cyan
        selected -> NexoraColors.Blue.copy(alpha = 0.80f)
        else -> Color.White.copy(alpha = 0.08f)
    }
    val backgroundColor = when {
        focused -> NexoraColors.Cyan.copy(alpha = 0.20f)
        selected -> NexoraColors.SurfaceSoft
        else -> NexoraColors.Surface.copy(alpha = 0.72f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(backgroundColor, RoundedCornerShape(18.dp))
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .onFocusChanged {
                focused = it.isFocused
                if (it.isFocused) onFocused()
            }
            .focusable()
            .clickable { onFocused() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = label,
            color = if (selected || focused) NexoraColors.TextPrimary else NexoraColors.TextSecondary,
            fontSize = 15.sp,
            fontWeight = if (selected || focused) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun HeaderBlock(
    selectedSection: HomeSection,
    focusedAccent: Color,
    onOpenPlayer: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(164.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        NexoraColors.SurfaceSoft,
                        focusedAccent.copy(alpha = 0.24f),
                        NexoraColors.Black.copy(alpha = 0.18f)
                    )
                ),
                RoundedCornerShape(30.dp)
            )
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(30.dp))
            .padding(26.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = selectedSection.label,
                color = NexoraColors.Cyan,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Premium UI Runtime Test",
                color = NexoraColors.TextPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Navigation and poster focus are running with mock legal content.",
                color = NexoraColors.TextSecondary,
                fontSize = 15.sp
            )
        }

        Box(
            modifier = Modifier
                .width(172.dp)
                .height(54.dp)
                .background(NexoraColors.Cyan.copy(alpha = 0.16f), RoundedCornerShape(999.dp))
                .border(1.dp, NexoraColors.Cyan.copy(alpha = 0.82f), RoundedCornerShape(999.dp))
                .clickable { onOpenPlayer() }
                .focusable(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Open Player",
                color = NexoraColors.TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun RuntimePosterCard(
    poster: RuntimePoster,
    onFocused: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val borderColor = if (focused) NexoraColors.Cyan else Color.White.copy(alpha = 0.10f)
    val backgroundColor = if (focused) poster.accent.copy(alpha = 0.34f) else NexoraColors.Surface

    Card(
        modifier = Modifier
            .size(width = if (focused) 230.dp else 220.dp, height = if (focused) 330.dp else 320.dp)
            .border(2.dp, borderColor, RoundedCornerShape(24.dp))
            .onFocusChanged {
                focused = it.isFocused
                if (it.isFocused) onFocused()
            }
            .focusable(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            poster.accent.copy(alpha = 0.40f),
                            NexoraColors.SurfaceSoft,
                            NexoraColors.Black
                        )
                    )
                )
                .padding(18.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = poster.title,
                    color = NexoraColors.TextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = poster.subtitle,
                    color = NexoraColors.TextSecondary,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun StateTiles() {
    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        SafeStatusTile("Loading", "Visible")
        SafeStatusTile("Empty", "Visible")
        SafeStatusTile("Error", "Visible")
    }
}

@Composable
private fun SafeStatusTile(title: String, body: String) {
    Column(
        modifier = Modifier
            .width(210.dp)
            .height(74.dp)
            .background(NexoraColors.SurfaceSoft, RoundedCornerShape(20.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            color = NexoraColors.TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = body,
            color = NexoraColors.TextSecondary,
            fontSize = 12.sp
        )
    }
}

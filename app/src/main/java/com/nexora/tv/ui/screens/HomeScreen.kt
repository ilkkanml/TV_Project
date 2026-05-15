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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.ui.theme.NexoraColors

private enum class StableSection(val label: String) {
    Home("Home"),
    Live("Live TV"),
    Movies("Movies"),
    Series("Series"),
    Settings("Settings")
}

private data class StablePoster(
    val title: String,
    val subtitle: String,
    val accent: Color
)

@Composable
fun HomeScreen(navController: NavController) {
    var selectedSection by remember { mutableStateOf(StableSection.Home) }
    var focusedAccent by remember { mutableStateOf(NexoraColors.Cyan) }
    val posters = postersFor(selectedSection)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        focusedAccent.copy(alpha = 0.26f),
                        Color(0xFF07101D),
                        NexoraColors.Black
                    )
                )
            )
            .padding(42.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Column(
            modifier = Modifier.width(180.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
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

            StableSection.values().forEach { section ->
                StableNavItem(
                    label = section.label,
                    selected = selectedSection == section,
                    onFocus = { selectedSection = section }
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            StableHeader(
                selectedSection = selectedSection,
                focusedAccent = focusedAccent
            )

            Text(
                text = rowTitleFor(selectedSection),
                color = NexoraColors.TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                posters.forEach { poster ->
                    StablePosterCard(
                        poster = poster,
                        onFocus = { focusedAccent = poster.accent }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                StableStatusTile("Loading", "Visible")
                StableStatusTile("Empty", "Visible")
                StableStatusTile("Error", "Visible")
            }
        }
    }
}

private fun rowTitleFor(section: StableSection): String = when (section) {
    StableSection.Home -> "Featured Library"
    StableSection.Live -> "Live TV Categories"
    StableSection.Movies -> "Movies • Continue Watching"
    StableSection.Series -> "Series • Continue Watching"
    StableSection.Settings -> "Settings Preview"
}

private fun postersFor(section: StableSection): List<StablePoster> = when (section) {
    StableSection.Home -> listOf(
        StablePoster("Featured", "Premium home highlight", NexoraColors.Cyan),
        StablePoster("Tonight", "Curated mock content", NexoraColors.Blue),
        StablePoster("Added", "Fresh content row", Color(0xFF7C4DFF))
    )
    StableSection.Live -> listOf(
        StablePoster("News", "Live category placeholder", NexoraColors.Cyan),
        StablePoster("Sports", "Live event placeholder", Color(0xFFFFD600)),
        StablePoster("Cinema", "Linear channel row", NexoraColors.Blue)
    )
    StableSection.Movies -> listOf(
        StablePoster("Continue Movie", "Resume mock progress", NexoraColors.Cyan),
        StablePoster("Orbit Fall", "Premium movie poster", Color(0xFF7C4DFF)),
        StablePoster("Glass Tower", "Featured VOD", NexoraColors.Blue)
    )
    StableSection.Series -> listOf(
        StablePoster("Continue Series", "Episode 4 • 42 min left", NexoraColors.Cyan),
        StablePoster("Midnight Grid", "Series poster shell", Color(0xFF7C4DFF)),
        StablePoster("Deep Archive", "New season", NexoraColors.Blue)
    )
    StableSection.Settings -> listOf(
        StablePoster("Account", "Device access placeholder", NexoraColors.Cyan),
        StablePoster("Playback", "Preference shell", NexoraColors.Blue),
        StablePoster("Display", "TV layout setting", Color(0xFF7C4DFF))
    )
}

@Composable
private fun StableNavItem(
    label: String,
    selected: Boolean,
    onFocus: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val borderColor = if (focused || selected) NexoraColors.Cyan else Color.White.copy(alpha = 0.08f)
    val backgroundColor = if (focused || selected) NexoraColors.Cyan.copy(alpha = 0.18f) else NexoraColors.Surface.copy(alpha = 0.72f)

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(50.dp)
            .background(backgroundColor, RoundedCornerShape(18.dp))
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .onFocusChanged {
                focused = it.isFocused
                if (it.isFocused) onFocus()
            }
            .focusable()
            .clickable { onFocus() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = label,
            color = NexoraColors.TextPrimary,
            fontSize = 15.sp,
            fontWeight = if (focused || selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun StableHeader(
    selectedSection: StableSection,
    focusedAccent: Color
) {
    Column(
        modifier = Modifier
            .width(850.dp)
            .height(160.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        NexoraColors.SurfaceSoft,
                        focusedAccent.copy(alpha = 0.24f),
                        NexoraColors.Black.copy(alpha = 0.20f)
                    )
                ),
                RoundedCornerShape(30.dp)
            )
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(30.dp))
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = selectedSection.label,
            color = NexoraColors.Cyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Premium UI Stable Test",
            color = NexoraColors.TextPrimary,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Mock legal content only. Player button disabled during Home runtime stabilization.",
            color = NexoraColors.TextSecondary,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun StablePosterCard(
    poster: StablePoster,
    onFocus: () -> Unit
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
                if (it.isFocused) onFocus()
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
private fun StableStatusTile(title: String, body: String) {
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

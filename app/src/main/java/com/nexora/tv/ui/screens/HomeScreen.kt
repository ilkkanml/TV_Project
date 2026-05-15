package com.nexora.tv.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.theme.NexoraColors

private data class HomePoster(
    val title: String,
    val subtitle: String,
    val tag: String,
    val progress: Float,
    val tone: Color
)

private data class HomeRow(
    val title: String,
    val items: List<HomePoster>
)

@Composable
fun HomeScreen(navController: NavController) {
    val rows = rememberHomeRows()
    var focusedPoster by remember { mutableStateOf(rows.first().items.first()) }
    val backgroundTone by animateColorAsState(
        targetValue = focusedPoster.tone.copy(alpha = 0.28f),
        label = "homeBackgroundTone"
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        backgroundTone,
                        Color(0xFF07101D),
                        NexoraColors.Black
                    )
                )
            )
            .padding(horizontal = 36.dp, vertical = 28.dp),
        horizontalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        NavigationShell()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            HeroHeader(
                poster = focusedPoster,
                onPlay = {
                    navController.navigate(AppDestinations.Player.route) {
                        launchSingleTop = true
                    }
                }
            )

            rows.forEach { row ->
                CinematicRow(
                    row = row,
                    onPosterFocused = { focusedPoster = it },
                    onPosterClick = {
                        navController.navigate(AppDestinations.Player.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            StateFeedbackRow()
        }
    }
}

@Composable
private fun rememberHomeRows(): List<HomeRow> = remember {
    listOf(
        HomeRow(
            title = "Continue Watching",
            items = listOf(
                HomePoster("Midnight Grid", "Episode 4 • 42 min left", "Series", 0.62f, NexoraColors.Cyan),
                HomePoster("City Signal", "Live preview ready", "Live", 0.18f, NexoraColors.Blue),
                HomePoster("Neon Harbor", "Resume from 01:12:40", "Movie", 0.48f, Color(0xFF7C4DFF)),
                HomePoster("Signal Room", "Episode 2 • 21 min left", "Series", 0.36f, Color(0xFF00BFA5)),
                HomePoster("Afterglow", "Continue watching", "Movie", 0.74f, Color(0xFFFF6D00))
            )
        ),
        HomeRow(
            title = "Live TV Highlights",
            items = listOf(
                HomePoster("Nexora News", "Live • Stable signal", "Live", 1f, NexoraColors.Cyan),
                HomePoster("Cinema One", "Now playing", "Live", 1f, Color(0xFF448AFF)),
                HomePoster("Documentary Hub", "Tonight lineup", "Live", 1f, Color(0xFF64DD17)),
                HomePoster("Sports Desk", "Upcoming event", "Live", 1f, Color(0xFFFFD600)),
                HomePoster("Kids Space", "Family row", "Live", 1f, Color(0xFFFF4081))
            )
        ),
        HomeRow(
            title = "Movies & Series",
            items = listOf(
                HomePoster("Orbit Fall", "Featured movie", "VOD", 0f, Color(0xFF7C4DFF)),
                HomePoster("Deep Archive", "New season", "Series", 0f, NexoraColors.Blue),
                HomePoster("Glass Tower", "Premium pick", "VOD", 0f, Color(0xFF00B8D4)),
                HomePoster("Silent Coast", "Watch later", "VOD", 0f, Color(0xFF18FFFF)),
                HomePoster("Blue District", "Editor pick", "Series", 0f, Color(0xFF536DFE))
            )
        )
    )
}

@Composable
private fun NavigationShell() {
    Column(
        modifier = Modifier
            .width(168.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "NEXORA",
            color = NexoraColors.TextPrimary,
            fontSize = 25.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Premium TV",
            color = NexoraColors.TextSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavItem("Home", selected = true)
        NavItem("Live TV", selected = false)
        NavItem("Movies", selected = false)
        NavItem("Series", selected = false)
        NavItem("Settings", selected = false)
    }
}

@Composable
private fun NavItem(label: String, selected: Boolean) {
    var focused by remember { mutableStateOf(false) }
    val borderColor by animateColorAsState(
        targetValue = when {
            focused -> NexoraColors.Cyan
            selected -> NexoraColors.Blue.copy(alpha = 0.75f)
            else -> Color.White.copy(alpha = 0.08f)
        },
        label = "navBorder"
    )
    val backgroundColor by animateColorAsState(
        targetValue = when {
            focused -> NexoraColors.Cyan.copy(alpha = 0.18f)
            selected -> NexoraColors.SurfaceSoft.copy(alpha = 0.92f)
            else -> NexoraColors.Surface.copy(alpha = 0.58f)
        },
        label = "navBackground"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .onFocusChanged { focused = it.isFocused }
            .focusable()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = label,
            color = if (selected || focused) NexoraColors.TextPrimary else NexoraColors.TextSecondary,
            fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun HeroHeader(
    poster: HomePoster,
    onPlay: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        NexoraColors.SurfaceSoft.copy(alpha = 0.96f),
                        poster.tone.copy(alpha = 0.22f),
                        NexoraColors.Surface.copy(alpha = 0.25f)
                    )
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(30.dp))
            .padding(26.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = "Featured now",
                color = NexoraColors.Cyan,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = poster.title,
                color = NexoraColors.TextPrimary,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = poster.subtitle,
                color = NexoraColors.TextSecondary,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            modifier = Modifier
                .width(168.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(NexoraColors.Cyan.copy(alpha = 0.16f))
                .border(1.dp, NexoraColors.Cyan.copy(alpha = 0.82f), RoundedCornerShape(999.dp))
                .clickable { onPlay() }
                .padding(horizontal = 22.dp),
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
private fun CinematicRow(
    row: HomeRow,
    onPosterFocused: (HomePoster) -> Unit,
    onPosterClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = row.title,
            color = NexoraColors.TextPrimary,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
        ) {
            itemsIndexed(
                items = row.items,
                key = { index, item -> "${row.title}-$index-${item.title}" }
            ) { _, item ->
                PremiumPosterCard(
                    item = item,
                    onFocused = { onPosterFocused(item) },
                    onClick = onPosterClick
                )
            }
        }
    }
}

@Composable
private fun PremiumPosterCard(
    item: HomePoster,
    onFocused: () -> Unit,
    onClick: () -> Unit
) {
    var focused by remember { mutableStateOf(false) }
    val cardWidth by animateDpAsState(
        targetValue = if (focused) 212.dp else 204.dp,
        label = "posterWidth"
    )
    val cardHeight by animateDpAsState(
        targetValue = if (focused) 292.dp else 282.dp,
        label = "posterHeight"
    )
    val borderColor by animateColorAsState(
        targetValue = if (focused) NexoraColors.Cyan else Color.White.copy(alpha = 0.08f),
        label = "posterBorderColor"
    )
    val cardBackground by animateColorAsState(
        targetValue = if (focused) item.tone.copy(alpha = 0.32f) else NexoraColors.Surface,
        label = "posterBackground"
    )

    Card(
        modifier = Modifier
            .size(width = cardWidth, height = cardHeight)
            .clip(RoundedCornerShape(28.dp))
            .border(2.dp, borderColor, RoundedCornerShape(28.dp))
            .onFocusChanged {
                focused = it.isFocused
                if (it.isFocused) onFocused()
            }
            .focusable()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = cardBackground)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            item.tone.copy(alpha = 0.54f),
                            NexoraColors.SurfaceSoft,
                            NexoraColors.Black
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = item.tag.uppercase(),
                color = NexoraColors.TextPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Color.Black.copy(alpha = 0.34f))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            )

            Column(
                modifier = Modifier.align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.title,
                    color = NexoraColors.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.subtitle,
                    color = NexoraColors.TextSecondary,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (item.progress > 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(Color.White.copy(alpha = 0.16f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(item.progress)
                                .background(NexoraColors.Cyan)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StateFeedbackRow() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        StatusTile(
            modifier = Modifier.weight(1f),
            title = "Loading",
            body = "Skeleton-safe rows keep the TV layout steady."
        )
        StatusTile(
            modifier = Modifier.weight(1f),
            title = "Empty",
            body = "Quiet placeholders preserve premium spacing."
        )
        StatusTile(
            modifier = Modifier.weight(1f),
            title = "Error",
            body = "Readable feedback remains clear from couch distance."
        )
    }
}

@Composable
private fun StatusTile(
    modifier: Modifier = Modifier,
    title: String,
    body: String
) {
    Column(
        modifier = modifier
            .height(74.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(NexoraColors.Surface.copy(alpha = 0.72f))
            .border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(22.dp))
            .padding(horizontal = 18.dp, vertical = 12.dp),
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
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

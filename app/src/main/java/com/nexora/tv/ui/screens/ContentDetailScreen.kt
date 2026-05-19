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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.content.MockContentLibrary
import com.nexora.tv.data.content.NexoraContentItem
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraBlue = Color(0xFF4CC9FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xB0111624)

@Composable
fun ContentDetailScreen(
    navController: NavController,
    contentId: String?
) {
    val content = MockContentLibrary.findContent(contentId)

    if (content == null) {
        MissingContentDetail(navController)
        return
    }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 58.dp, vertical = 44.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailPoster(content)
            DetailInfo(
                navController = navController,
                content = content
            )
        }
    }
}

@Composable
private fun DetailPoster(content: NexoraContentItem) {
    val accent = Color(content.accentColor)

    Box(
        modifier = Modifier
            .width(390.dp)
            .height(560.dp)
            .shadow(
                elevation = 26.dp,
                shape = RoundedCornerShape(34.dp),
                ambientColor = accent,
                spotColor = accent
            )
            .background(
                color = PanelDark,
                shape = RoundedCornerShape(34.dp)
            )
            .border(
                width = 2.dp,
                color = accent.copy(alpha = 0.72f),
                shape = RoundedCornerShape(34.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            accent.copy(alpha = 0.34f),
                            Color.Black.copy(alpha = 0.20f),
                            Color.Black.copy(alpha = 0.84f)
                        )
                    ),
                    shape = RoundedCornerShape(34.dp)
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailPill(
                text = content.badge,
                color = accent
            )

            DetailPill(
                text = content.category,
                color = NexoraBlue
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(26.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = content.title,
                color = Color.White,
                fontSize = 33.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 36.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = content.subtitle,
                color = Color.White.copy(alpha = 0.68f),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun DetailInfo(
    navController: NavController,
    content: NexoraContentItem
) {
    val accent = Color(content.accentColor)

    Column(
        modifier = Modifier
            .width(730.dp)
            .background(
                color = PanelDark,
                shape = RoundedCornerShape(34.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.10f),
                shape = RoundedCornerShape(34.dp)
            )
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = content.type.label.uppercase(),
            color = NexoraVioletSoft,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        Text(
            text = content.title,
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 54.sp
        )

        Text(
            text = content.description,
            color = Color.White.copy(alpha = 0.72f),
            fontSize = 17.sp,
            lineHeight = 24.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailPill(
                text = content.category,
                color = accent
            )

            DetailPill(
                text = "Licensed",
                color = NexoraBlue
            )

            DetailPill(
                text = "Mock Metadata",
                color = NexoraViolet
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                title = "Runtime",
                body = "42 min"
            )

            MetricCard(
                title = "Quality",
                body = "4K shell"
            )

            MetricCard(
                title = "Audio",
                body = "Spatial UI"
            )
        }

        Box(
            modifier = Modifier
                .width(650.dp)
                .background(
                    color = PanelSoft,
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(18.dp)
        ) {
            Text(
                text = "No provider, backend token, payment flow, scraping, hidden API, or unauthorized stream source is included in this route.",
                color = Color.White.copy(alpha = 0.56f),
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate(AppDestinations.Player.route) {
                        launchSingleTop = true
                    }
                },
                enabled = content.isPlayable,
                modifier = Modifier
                    .width(170.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accent,
                    contentColor = Color.White,
                    disabledContainerColor = Color.White.copy(alpha = 0.08f),
                    disabledContentColor = Color.White.copy(alpha = 0.44f)
                )
            ) {
                Text(
                    text = if (content.isPlayable) {
                        "Play Mock"
                    } else {
                        "Not Playable"
                    },
                    fontWeight = FontWeight.Black
                )
            }

            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.08f),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Back",
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    navController.navigate(AppDestinations.Home.route) {
                        popUpTo(AppDestinations.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.08f),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Home",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .width(170.dp)
            .background(
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = title,
            color = NexoraVioletSoft,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = body,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun DetailPill(
    text: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .height(38.dp)
            .background(
                color = color.copy(alpha = 0.18f),
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.44f),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun MissingContentDetail(navController: NavController) {
    NexoraCinematicBackdrop {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(58.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Content not found",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "This placeholder protects the route if a mock content id is missing.",
                color = Color.White.copy(alpha = 0.68f),
                fontSize = 16.sp
            )

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NexoraViolet,
                        contentColor = Color.White
                    )
                ) {
                    Text("Back")
                }

                Button(
                    onClick = {
                        navController.navigate(AppDestinations.Home.route) {
                            popUpTo(AppDestinations.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.08f),
                        contentColor = Color.White
                    )
                ) {
                    Text("Home")
                }
            }
        }
    }
}

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
fun ContentDetailScreen(navController: NavController, contentId: String?) {
    val content = MockContentLibrary.findContent(contentId)

    if (content == null) {
        MissingContentDetail(navController)
        return
    }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 42.dp, vertical = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailPoster(content)
            DetailInfo(navController, content)
        }
    }
}

@Composable
private fun DetailPoster(content: NexoraContentItem) {
    val accent = Color(content.accentColor)

    Box(
        modifier = Modifier
            .width(360.dp)
            .height(540.dp)
            .shadow(24.dp, RoundedCornerShape(34.dp), ambientColor = accent, spotColor = accent)
            .background(PanelDark, RoundedCornerShape(34.dp))
            .border(2.dp, accent.copy(alpha = 0.72f), RoundedCornerShape(34.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            accent.copy(alpha = 0.34f),
                            Color.Black.copy(alpha = 0.18f),
                            Color.Black.copy(alpha = 0.86f)
                        )
                    ),
                    shape = RoundedCornerShape(34.dp)
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailPill(content.badge, accent)
            DetailPill(content.category, NexoraBlue)
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = content.title,
                color = Color.White,
                fontSize = 31.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 34.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = content.subtitle,
                color = Color.White.copy(alpha = 0.70f),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun DetailInfo(navController: NavController, content: NexoraContentItem) {
    val accent = Color(content.accentColor)

    Column(
        modifier = Modifier
            .width(770.dp)
            .background(PanelDark, RoundedCornerShape(34.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(34.dp))
            .padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
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
            fontSize = 48.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 52.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = content.description,
            color = Color.White.copy(alpha = 0.72f),
            fontSize = 16.sp,
            lineHeight = 23.sp,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DetailPill(content.category, accent)
            DetailPill("Licensed", NexoraBlue)
            DetailPill(content.badge, NexoraViolet)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            MetricCard("Runtime", "42 min")
            MetricCard("Quality", "4K shell")
            MetricCard("Audio", "TV ready")
        }

        Box(
            modifier = Modifier
                .width(690.dp)
                .background(PanelSoft, RoundedCornerShape(24.dp))
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "This screen is a licensed-content UI shell. Playback uses only user-provided legal streams or local mock metadata.",
                color = Color.White.copy(alpha = 0.58f),
                fontSize = 12.sp,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    navController.navigate(AppDestinations.Player.route) {
                        launchSingleTop = true
                    }
                },
                enabled = content.isPlayable,
                modifier = Modifier
                    .width(230.dp)
                    .height(70.dp)
                    .shadow(18.dp, RoundedCornerShape(22.dp), ambientColor = accent, spotColor = accent),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accent,
                    contentColor = Color.White,
                    disabledContainerColor = Color.White.copy(alpha = 0.08f),
                    disabledContentColor = Color.White.copy(alpha = 0.44f)
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (content.isPlayable) "PLAY" else "NOT PLAYABLE",
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        letterSpacing = 1.5.sp,
                        maxLines = 1
                    )
                    if (content.isPlayable) {
                        Text(
                            text = "Open player",
                            color = Color.White.copy(alpha = 0.78f),
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.width(128.dp).height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
            ) {
                Text("Back", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    navController.navigate(AppDestinations.Home.route) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.width(128.dp).height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
            ) {
                Text("Home", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, body: String) {
    Column(
        modifier = Modifier
            .width(170.dp)
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(title, color = NexoraVioletSoft, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Text(body, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
private fun DetailPill(text: String, color: Color) {
    Box(
        modifier = Modifier
            .height(38.dp)
            .background(color.copy(alpha = 0.18f), RoundedCornerShape(14.dp))
            .border(1.dp, color.copy(alpha = 0.44f), RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
            Text("Content not found", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
            Text("This placeholder protects the route if a mock content id is missing.", color = Color.White.copy(alpha = 0.68f), fontSize = 16.sp)

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
                ) {
                    Text("Back")
                }
                Button(
                    onClick = { navController.navigate(AppDestinations.Home.route) { launchSingleTop = true } },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                ) {
                    Text("Home")
                }
            }
        }
    }
}

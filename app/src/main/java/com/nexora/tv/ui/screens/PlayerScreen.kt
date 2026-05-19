package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private const val MOCK_PLAYER_STREAM_URL = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraBlue = Color(0xFF4CC9FF)
private val PanelDark = Color(0xCC090B12)

@Composable
fun PlayerScreen(navController: NavController) {
    NexoraCinematicBackdrop {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.30f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(42.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.46f),
                        shape = RoundedCornerShape(34.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(34.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x33151A2A),
                                    Color.Black.copy(alpha = 0.46f),
                                    Color.Black.copy(alpha = 0.82f)
                                )
                            ),
                            shape = RoundedCornerShape(34.dp)
                        )
                )

                TopOverlay(navController)
                CenterPlayerState()
                BottomOverlay(navController)
            }
        }
    }
}

@Composable
private fun TopOverlay(navController: NavController) {
    Row(
        modifier = Modifier
            .width(1120.dp)
            .padding(28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "NEXORA PLAYER",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp
            )

            Text(
                text = "Immersive playback shell",
                color = Color.White.copy(alpha = 0.58f),
                fontSize = 13.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlayerChip("4K")
            PlayerChip("HDR")
            PlayerChip("Mock Shell")
            PlayerNavButton("Back") {
                navController.popBackStack()
            }
            PlayerNavButton("Home") {
                navController.navigate(AppDestinations.Home.route) {
                    popUpTo(AppDestinations.Home.route) { inclusive = false }
                    launchSingleTop = true
                }
            }
        }
    }
}

@Composable
private fun PlayerChip(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = NexoraViolet.copy(alpha = 0.18f),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = NexoraViolet.copy(alpha = 0.42f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
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
private fun PlayerNavButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(86.dp)
            .height(40.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.08f),
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun CenterPlayerState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 82.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(112.dp)
                .height(112.dp)
                .background(
                    color = NexoraViolet.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(56.dp)
                )
                .border(
                    width = 2.dp,
                    color = NexoraBlue.copy(alpha = 0.80f),
                    shape = RoundedCornerShape(56.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "▶",
                color = Color.White,
                fontSize = 46.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Playback shell ready",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = "Safe internal route. No autoplay, no hidden provider, no unauthorized source.",
            color = Color.White.copy(alpha = 0.62f),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun BoxScope.BottomOverlay(navController: NavController) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(32.dp)
            .width(940.dp)
            .background(
                color = PanelDark,
                shape = RoundedCornerShape(26.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.10f),
                shape = RoundedCornerShape(26.dp)
            )
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(896.dp)
        ) {
            Text(
                text = "Player Overlay",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PlayerNavButton("Back") {
                    navController.popBackStack()
                }
                PlayerNavButton("Home") {
                    navController.navigate(AppDestinations.Home.route) {
                        popUpTo(AppDestinations.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OverlayMetric(
                title = "Title",
                value = "Orbit Fall"
            )

            OverlayMetric(
                title = "Time",
                value = "01:12 / 02:06"
            )

            OverlayMetric(
                title = "Audio",
                value = "Spatial"
            )
        }

        Text(
            text = "Current route is a premium visual shell only. Media3 playback integration can be layered later without changing the design language.",
            color = Color.White.copy(alpha = 0.68f),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )

        Text(
            text = "Mock test stream preserved for later QA: $MOCK_PLAYER_STREAM_URL",
            color = Color.White.copy(alpha = 0.38f),
            fontSize = 11.sp
        )
    }
}

@Composable
private fun OverlayMetric(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .background(
                color = Color.White.copy(alpha = 0.05f),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(18.dp)
            )
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            color = NexoraVioletSoft,
            fontSize = 11.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

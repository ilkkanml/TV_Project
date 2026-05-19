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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private const val MOCK_PLAYER_STREAM_URL = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"
private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)

@Composable
fun PlayerScreen() {
    NexoraCinematicBackdrop {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.34f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(42.dp)
                    .background(Color.Black.copy(alpha = 0.44f), RoundedCornerShape(34.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(34.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.42f),
                                    Color.Black.copy(alpha = 0.78f)
                                )
                            ),
                            RoundedCornerShape(34.dp)
                        )
                )

                TopOverlay()
                CenterPlayerState()
                BottomOverlay()
            }
        }
    }
}

@Composable
private fun TopOverlay() {
    Row(
        modifier = Modifier
            .width(1120.dp)
            .padding(28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "NEXORA PLAYER",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "Licensed-ready mock playback shell",
                color = Color.White.copy(alpha = 0.58f),
                fontSize = 13.sp
            )
        }

        Box(
            modifier = Modifier
                .background(NexoraViolet.copy(alpha = 0.20f), RoundedCornerShape(16.dp))
                .border(1.dp, NexoraViolet.copy(alpha = 0.56f), RoundedCornerShape(16.dp))
                .padding(horizontal = 18.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MOCK SHELL",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
private fun CenterPlayerState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 72.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(104.dp)
                .height(104.dp)
                .background(NexoraViolet.copy(alpha = 0.18f), RoundedCornerShape(52.dp))
                .border(2.dp, NexoraVioletSoft.copy(alpha = 0.76f), RoundedCornerShape(52.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "▶",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Playback not started",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Safe internal player route. No auto-play, no provider stream, no unauthorized source.",
            color = Color.White.copy(alpha = 0.62f),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun BottomOverlay() {
    Column(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(32.dp)
            .width(900.dp)
            .background(PanelDark, RoundedCornerShape(26.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(26.dp))
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Internal Alpha Player Shell",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "The route is ready for future controlled Media3 playback work. Current M15R scope is visual shell only.",
            color = Color.White.copy(alpha = 0.68f),
            fontSize = 14.sp
        )
        Text(
            text = "Mock test stream preserved for later QA: $MOCK_PLAYER_STREAM_URL",
            color = Color.White.copy(alpha = 0.38f),
            fontSize = 11.sp
        )
    }
}

package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val MOCK_PLAYER_STREAM_URL = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8"

@Composable
fun PlayerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(56.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(720.dp)
                .border(1.dp, Color(0xFF00E5FF), RoundedCornerShape(28.dp))
                .background(Color(0xFF06111D), RoundedCornerShape(28.dp))
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NEXORA PLAYER",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Black
            )

            Text(
                text = "Safe player shell route active",
                color = Color(0xFF00E5FF),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Playback is intentionally not started during initial render.",
                color = Color.LightGray,
                fontSize = 15.sp
            )

            Text(
                text = "Mock stream preserved for later controlled playback: $MOCK_PLAYER_STREAM_URL",
                color = Color(0xFF9EA8B8),
                fontSize = 12.sp
            )
        }
    }
}

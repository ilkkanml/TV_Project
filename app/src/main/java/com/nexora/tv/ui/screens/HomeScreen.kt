package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

private data class SafePoster(
    val title: String,
    val subtitle: String,
    val accent: Color
)

@Composable
fun HomeScreen(navController: NavController) {
    val posters = remember {
        listOf(
            SafePoster("Continue Watching", "Runtime safe HomeScreen", NexoraColors.Cyan),
            SafePoster("Live TV", "Mock category row", NexoraColors.Blue),
            SafePoster("Movies", "Premium poster shell", Color(0xFF7C4DFF)),
            SafePoster("Series", "Focus test card", Color(0xFF00BFA5))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF07101D),
                        NexoraColors.Black
                    )
                )
            )
            .padding(48.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Text(
            text = "NEXORA TV",
            color = NexoraColors.TextPrimary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = "M3 Safe Runtime Home Test",
            color = NexoraColors.TextSecondary,
            fontSize = 18.sp
        )

        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            posters.forEach { poster ->
                SafePosterCard(poster)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            SafeStatusTile("Loading", "Visible")
            SafeStatusTile("Empty", "Visible")
            SafeStatusTile("Error", "Visible")
        }
    }
}

@Composable
private fun SafePosterCard(poster: SafePoster) {
    var focused by remember { mutableStateOf(false) }
    val borderColor = if (focused) NexoraColors.Cyan else Color.White.copy(alpha = 0.10f)
    val backgroundColor = if (focused) poster.accent.copy(alpha = 0.32f) else NexoraColors.Surface

    Card(
        modifier = Modifier
            .size(width = if (focused) 230.dp else 220.dp, height = if (focused) 330.dp else 320.dp)
            .border(2.dp, borderColor, RoundedCornerShape(24.dp))
            .onFocusChanged { focused = it.isFocused }
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
                            poster.accent.copy(alpha = 0.38f),
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

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.content.MockContentLibrary
import com.nexora.tv.data.content.NexoraContentItem
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)

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
            horizontalArrangement = Arrangement.spacedBy(38.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DetailPoster(content)
            DetailInfo(navController, content)
        }
    }
}

@Composable
private fun DetailPoster(content: NexoraContentItem) {
    Box(
        modifier = Modifier
            .width(370.dp)
            .height(520.dp)
            .shadow(
                elevation = 26.dp,
                shape = RoundedCornerShape(34.dp),
                ambientColor = NexoraViolet,
                spotColor = NexoraViolet
            )
            .background(PanelDark, RoundedCornerShape(34.dp))
            .border(2.dp, NexoraViolet.copy(alpha = 0.72f), RoundedCornerShape(34.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            NexoraViolet.copy(alpha = 0.28f),
                            Color.Black.copy(alpha = 0.24f),
                            Color.Black.copy(alpha = 0.82f)
                        )
                    ),
                    RoundedCornerShape(34.dp)
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(26.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = content.badge,
                color = NexoraVioletSoft,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp
            )
            Text(
                text = content.title,
                color = Color.White,
                fontSize = 31.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 34.sp
            )
            Text(
                text = content.subtitle,
                color = Color.White.copy(alpha = 0.62f),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun DetailInfo(navController: NavController, content: NexoraContentItem) {
    Column(
        modifier = Modifier
            .width(720.dp)
            .background(PanelDark, RoundedCornerShape(34.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(34.dp))
            .padding(32.dp),
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
            lineHeight = 52.sp
        )
        Text(
            text = content.description,
            color = Color.White.copy(alpha = 0.72f),
            fontSize = 17.sp,
            lineHeight = 24.sp
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DetailPill(content.category)
            DetailPill("Licensed")
            DetailPill("Mock Metadata")
        }

        Text(
            text = "No provider, backend, token, payment, scraping, or unauthorized stream integration is included.",
            color = Color.White.copy(alpha = 0.52f),
            fontSize = 13.sp,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            Button(
                onClick = {
                    navController.navigate(AppDestinations.Player.route) {
                        launchSingleTop = true
                    }
                },
                enabled = content.isPlayable,
                modifier = Modifier
                    .width(150.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NexoraViolet,
                    contentColor = Color.White,
                    disabledContainerColor = Color.White.copy(alpha = 0.08f),
                    disabledContentColor = Color.White.copy(alpha = 0.44f)
                )
            ) {
                Text(if (content.isPlayable) "Play Mock" else "Not Playable", fontWeight = FontWeight.Black)
            }

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .width(112.dp)
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.08f),
                    contentColor = Color.White
                )
            ) {
                Text("Back", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DetailPill(text: String) {
    Box(
        modifier = Modifier
            .height(38.dp)
            .background(NexoraViolet.copy(alpha = 0.18f), RoundedCornerShape(14.dp))
            .border(1.dp, NexoraViolet.copy(alpha = 0.44f), RoundedCornerShape(14.dp))
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
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = NexoraViolet,
                    contentColor = Color.White
                )
            ) {
                Text("Back")
            }
        }
    }
}

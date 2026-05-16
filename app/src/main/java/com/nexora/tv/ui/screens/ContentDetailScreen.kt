package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.content.MockContentLibrary
import com.nexora.tv.data.content.NexoraContentItem
import com.nexora.tv.navigation.AppDestinations

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

    val accent = Color(content.accentColor)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06111D))
            .padding(horizontal = 56.dp, vertical = 44.dp),
        horizontalArrangement = Arrangement.spacedBy(34.dp)
    ) {
        Column(
            modifier = Modifier
                .width(360.dp)
                .height(520.dp)
                .background(accent.copy(alpha = 0.24f), RoundedCornerShape(30.dp))
                .border(2.dp, accent, RoundedCornerShape(30.dp))
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = content.badge,
                color = accent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = content.title,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = content.subtitle,
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }

        Column(
            modifier = Modifier.width(690.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = content.type.label,
                color = accent,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = content.title,
                color = Color.White,
                fontSize = 44.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = content.description,
                color = Color.LightGray,
                fontSize = 17.sp
            )
            Text(
                text = "Category: ${content.category}",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Mock/local metadata only. No provider, backend, token, payment, or illegal stream integration.",
                color = Color(0xFF9EA8B8),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Button(
                    onClick = {
                        navController.navigate(AppDestinations.Player.route) {
                            launchSingleTop = true
                        }
                    },
                    enabled = content.isPlayable,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accent,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF26303D),
                        disabledContentColor = Color.LightGray
                    )
                ) {
                    Text(if (content.isPlayable) "Play Mock" else "Not Playable")
                }

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF141821),
                        contentColor = Color.White
                    )
                ) {
                    Text("Back")
                }
            }
        }
    }
}

@Composable
private fun MissingContentDetail(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06111D))
            .padding(56.dp),
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
            color = Color.LightGray,
            fontSize = 16.sp
        )
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF123A46),
                contentColor = Color.White
            )
        ) {
            Text("Back")
        }
    }
}

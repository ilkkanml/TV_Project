package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private const val MOCK_NEXORA_DEVICE_ID = "NX-TV-8F2K-44M9"
private const val MOCK_ACTIVATION_KEY = "K7Q4-29XA"
private const val MOCK_ACTIVATION_WEBSITE = "nexoratv.com/activate"
private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)

@Composable
fun DeviceActivationScreen(navController: NavController) {
    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 44.dp, vertical = 34.dp),
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(390.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("NEXORA", color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.Black, letterSpacing = 2.5.sp, maxLines = 1)
                Text("Activate this TV", color = NexoraVioletSoft, fontSize = 22.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(
                    text = "Pair this internal alpha device, then continue to playlist setup.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                ActivationCodePanel("Nexora Device ID", MOCK_NEXORA_DEVICE_ID)
                ActivationCodePanel("Activation Key", MOCK_ACTIVATION_KEY)

                Button(
                    onClick = {
                        navController.navigate(AppDestinations.PlaylistProfile.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.width(260.dp).height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
                ) {
                    Text("Continue Setup", fontWeight = FontWeight.Black, fontSize = 13.sp)
                }
            }

            Column(
                modifier = Modifier
                    .width(610.dp)
                    .background(PanelDark, RoundedCornerShape(30.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                Text("Website Activation", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black, maxLines = 1)
                Text(MOCK_ACTIVATION_WEBSITE, color = NexoraVioletSoft, fontSize = 24.sp, fontWeight = FontWeight.Black, maxLines = 1)

                ActivationStep("1", "Open the activation website on your phone or computer.")
                ActivationStep("2", "Enter the Nexora Device ID shown on this TV.")
                ActivationStep("3", "Enter the Activation Key to pair this internal alpha device.")
                ActivationStep("4", "Continue to playlist/profile setup on this TV.")

                Box(
                    modifier = Modifier
                        .width(550.dp)
                        .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(20.dp))
                        .border(1.dp, NexoraViolet.copy(alpha = 0.36f), RoundedCornerShape(20.dp))
                        .padding(15.dp)
                ) {
                    Text(
                        text = "Demo identity only. No real device pairing is performed in this prototype.",
                        color = Color.White.copy(alpha = 0.72f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivationCodePanel(title: String, value: String) {
    Column(
        modifier = Modifier
            .width(390.dp)
            .background(PanelDark, RoundedCornerShape(22.dp))
            .border(1.dp, NexoraViolet.copy(alpha = 0.50f), RoundedCornerShape(22.dp))
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(text = title, color = NexoraVioletSoft, fontSize = 12.sp, fontWeight = FontWeight.Black)
        Text(text = value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 1.5.sp, maxLines = 1)
    }
}

@Composable
private fun ActivationStep(number: String, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(11.dp))
                .border(1.dp, NexoraViolet.copy(alpha = 0.62f), RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = number, color = NexoraVioletSoft, fontSize = 14.sp, fontWeight = FontWeight.Black)
        }
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.86f),
            fontSize = 14.sp,
            lineHeight = 19.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

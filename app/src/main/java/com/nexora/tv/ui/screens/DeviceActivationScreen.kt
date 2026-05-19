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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                .padding(horizontal = 58.dp, vertical = 46.dp),
            horizontalArrangement = Arrangement.spacedBy(34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(430.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "NEXORA",
                    color = Color.White,
                    fontSize = 43.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp
                )
                Text(
                    text = "Activate this TV",
                    color = NexoraVioletSoft,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Early access is free for a limited internal alpha period. Pair this screen on the website, then continue to playlist setup.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 15.sp,
                    lineHeight = 21.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                ActivationCodePanel("Nexora Device ID", MOCK_NEXORA_DEVICE_ID)
                ActivationCodePanel("Activation Key", MOCK_ACTIVATION_KEY)

                Button(
                    onClick = {
                        navController.navigate(AppDestinations.PlaylistProfile.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .width(286.dp)
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NexoraViolet,
                        contentColor = Color.White
                    )
                ) {
                    Text("Continue Setup", fontWeight = FontWeight.Black)
                }
            }

            Column(
                modifier = Modifier
                    .width(650.dp)
                    .background(PanelDark, RoundedCornerShape(34.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(34.dp))
                    .padding(28.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Website Activation",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = MOCK_ACTIVATION_WEBSITE,
                    color = NexoraVioletSoft,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )

                ActivationStep("1", "Open the activation website on your phone or computer.")
                ActivationStep("2", "Enter the Nexora Device ID shown on this TV.")
                ActivationStep("3", "Enter the Activation Key to pair this internal alpha device.")
                ActivationStep("4", "Continue to playlist/profile setup on this TV.")

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .width(590.dp)
                        .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(22.dp))
                        .border(1.dp, NexoraViolet.copy(alpha = 0.36f), RoundedCornerShape(22.dp))
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Privacy-safe mock identity: no MAC address, no hardware identifier collection, no real backend activation, no license enforcement.",
                        color = Color.White.copy(alpha = 0.72f),
                        fontSize = 13.sp,
                        lineHeight = 19.sp
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
            .width(430.dp)
            .background(PanelDark, RoundedCornerShape(24.dp))
            .border(1.dp, NexoraViolet.copy(alpha = 0.56f), RoundedCornerShape(24.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            color = NexoraVioletSoft,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
    }
}

@Composable
private fun ActivationStep(number: String, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .width(36.dp)
                .height(36.dp)
                .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(12.dp))
                .border(1.dp, NexoraViolet.copy(alpha = 0.62f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = NexoraVioletSoft,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black
            )
        }
        Text(
            text = text,
            color = Color.White.copy(alpha = 0.86f),
            fontSize = 16.sp,
            lineHeight = 22.sp
        )
    }
}

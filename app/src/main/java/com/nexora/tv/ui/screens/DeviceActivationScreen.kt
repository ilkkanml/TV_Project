package com.nexora.tv.ui.screens

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
import com.nexora.tv.ui.components.PremiumBackground
import com.nexora.tv.ui.components.PremiumButton
import com.nexora.tv.ui.components.PremiumPanel
import com.nexora.tv.ui.theme.NexoraColors

private const val MOCK_NEXORA_DEVICE_ID = "NX-TV-8F2K-44M9"
private const val MOCK_ACTIVATION_KEY = "K7Q4-29XA"
private const val MOCK_ACTIVATION_WEBSITE = "nexoratv.com/activate"

@Composable
fun DeviceActivationScreen(navController: NavController) {
    PremiumBackground(
        accent = NexoraColors.Cyan,
        contentAlignment = Alignment.Center
    ) {
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
                    color = NexoraColors.TextPrimary,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp
                )
                Text(
                    text = "Activate this TV",
                    color = NexoraColors.Cyan,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Use the website to pair this screen with a Nexora account when real activation is approved. This internal alpha keeps activation local and mock-only.",
                    color = NexoraColors.TextSecondary,
                    fontSize = 15.sp,
                    lineHeight = 21.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                ActivationCodePanel(
                    title = "Nexora Device ID",
                    value = MOCK_NEXORA_DEVICE_ID,
                    accent = NexoraColors.Cyan
                )

                ActivationCodePanel(
                    title = "Activation Key",
                    value = MOCK_ACTIVATION_KEY,
                    accent = Color(0xFF00BFA5)
                )

                PremiumButton(
                    text = "Continue Internal Alpha",
                    onClick = {
                        navController.navigate(AppDestinations.Home.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.width(270.dp),
                    accent = Color(0xFF00BFA5),
                    primary = true
                )
            }

            PremiumPanel(
                modifier = Modifier.width(650.dp),
                accent = NexoraColors.Cyan,
                cornerRadius = 32.dp
            ) {
                Text(
                    text = "Website Activation",
                    color = NexoraColors.TextPrimary,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = MOCK_ACTIVATION_WEBSITE,
                    color = NexoraColors.Cyan,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(22.dp))
                ActivationStep("1", "Open the activation website on your phone or computer.")
                ActivationStep("2", "Enter the Nexora Device ID shown on this TV.")
                ActivationStep("3", "Enter the Activation Key to pair this internal alpha device.")
                ActivationStep("4", "Return to this TV and continue to the app shell.")
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Privacy-safe mock identity: no MAC address, no hardware identifier collection, no real backend activation, no license enforcement.",
                    color = NexoraColors.TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}

@Composable
private fun ActivationCodePanel(
    title: String,
    value: String,
    accent: Color
) {
    PremiumPanel(
        modifier = Modifier.width(430.dp),
        accent = accent,
        cornerRadius = 24.dp
    ) {
        Text(
            text = title,
            color = accent,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            color = NexoraColors.TextPrimary,
            fontSize = 27.sp,
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
        modifier = Modifier.padding(vertical = 7.dp)
    ) {
        Text(
            text = number,
            color = NexoraColors.Cyan,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .width(34.dp)
                .border(1.dp, NexoraColors.Cyan.copy(alpha = 0.55f), RoundedCornerShape(12.dp))
                .padding(vertical = 5.dp),
        )
        Text(
            text = text,
            color = NexoraColors.TextPrimary,
            fontSize = 16.sp,
            lineHeight = 22.sp
        )
    }
}

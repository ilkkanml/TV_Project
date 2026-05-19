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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)
private val PanelSoft = Color(0xB0111624)

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp, vertical = 44.dp),
            horizontalArrangement = Arrangement.spacedBy(28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(470.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "NEXORA",
                    color = Color.White,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp
                )

                Text(
                    text = "PLAYER ECOSYSTEMS",
                    color = NexoraVioletSoft,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.5.sp
                )

                Text(
                    text = "A cinematic Android TV shell with a premium intro, large-banner home experience and polished account onboarding.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 16.sp,
                    lineHeight = 23.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                FeatureCard(
                    title = "Brand intro",
                    body = "Animated NEXORA startup with neon motion and sound design."
                )

                FeatureCard(
                    title = "TV-first UI",
                    body = "Remote-friendly spacing, large typography and premium glass panels."
                )

                FeatureCard(
                    title = "Safe legal shell",
                    body = "No hidden APIs, no scraped providers, no unauthorized sources."
                )
            }

            Column(
                modifier = Modifier
                    .width(560.dp)
                    .background(
                        color = PanelDark,
                        shape = RoundedCornerShape(34.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.10f),
                        shape = RoundedCornerShape(34.dp)
                    )
                    .padding(30.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Sign in",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "Use your account to continue to device activation and playlist profile setup.",
                    color = Color.White.copy(alpha = 0.64f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                PremiumField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = "Username"
                )

                PremiumField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = "Password",
                    isPassword = true
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(154.dp)
                            .background(
                                color = PanelSoft,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.07f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "Internal Alpha",
                            color = NexoraVioletSoft,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .background(
                                color = PanelSoft,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.07f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "No real auth backend",
                            color = Color.White.copy(alpha = 0.78f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Button(
                    onClick = {
                        navController.navigate(AppDestinations.Activation.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .width(220.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NexoraViolet,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .width(430.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.06f),
                        Color.White.copy(alpha = 0.03f)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            color = NexoraVioletSoft,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
        )

        Text(
            text = body,
            color = Color.White.copy(alpha = 0.70f),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun PremiumField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(label)
        },
        modifier = Modifier.width(500.dp),
        singleLine = true,
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            androidx.compose.ui.text.input.VisualTransformation.None
        },
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NexoraViolet,
            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
            focusedLabelColor = NexoraVioletSoft,
            unfocusedLabelColor = Color.White.copy(alpha = 0.50f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = NexoraVioletSoft,
            focusedContainerColor = Color.White.copy(alpha = 0.03f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.02f)
        )
    )
}
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations

private const val MOCK_DEVICE_ID = "NEXORA-DEMO-DEVICE-001"
private const val MOCK_VALID_PASSWORD = "demo123"

private enum class MockActivationState {
    Empty,
    Loading,
    Active,
    Inactive,
    Expired,
    Error
}

private data class MockActivationResult(
    val state: MockActivationState,
    val title: String,
    val message: String,
    val expiresAt: String = "Not active"
)

@Composable
fun DeviceActivationScreen(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var result by remember {
        mutableStateOf(
            MockActivationResult(
                state = MockActivationState.Empty,
                title = "Waiting for activation",
                message = "Enter the mock activation password to continue."
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF06111D))
            .padding(horizontal = 56.dp, vertical = 44.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(430.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "NEXORA",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Device Activation",
                color = Color(0xFF00E5FF),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Safe placeholder flow. No production backend, payment, IPTV provider, token, or DRM logic.",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoPanel(
                title = "Device Identity",
                body = MOCK_DEVICE_ID,
                accent = Color(0xFF00E5FF)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Activation Password") },
                modifier = Modifier.width(430.dp),
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        result = MockActivationResult(
                            state = MockActivationState.Loading,
                            title = "Checking activation",
                            message = "Mock validation is running locally."
                        )
                        result = validateMockActivation(password)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF123A46),
                        contentColor = Color.White
                    )
                ) {
                    Text("Activate")
                }

                if (result.state == MockActivationState.Active) {
                    Button(
                        onClick = {
                            navController.navigate(AppDestinations.Home.route) {
                                launchSingleTop = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00BFA5),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Continue")
                    }
                }
            }
        }

        Column(
            modifier = Modifier.width(620.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusCard(result)

            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                SmallStateCard("Loading", "Visible placeholder", Color(0xFF2962FF))
                SmallStateCard("Empty", "No activation yet", Color(0xFF7C4DFF))
                SmallStateCard("Error", "Invalid password path", Color(0xFFFF6D00))
            }

            InfoPanel(
                title = "Mock Passwords",
                body = "demo123 = active • inactive = inactive • expired = expired • blank/other = error",
                accent = Color(0xFF7C4DFF)
            )
        }
    }
}

private fun validateMockActivation(password: String): MockActivationResult {
    val normalized = password.trim()

    return when {
        normalized.isBlank() -> MockActivationResult(
            state = MockActivationState.Error,
            title = "Activation password required",
            message = "Enter a password before activating."
        )

        normalized == MOCK_VALID_PASSWORD -> MockActivationResult(
            state = MockActivationState.Active,
            title = "Device active",
            message = "Mock auth accepted. Device can continue to Home.",
            expiresAt = "2099-12-31"
        )

        normalized.equals("inactive", ignoreCase = true) -> MockActivationResult(
            state = MockActivationState.Inactive,
            title = "Device inactive",
            message = "Mock response shows an inactive device placeholder.",
            expiresAt = "Inactive"
        )

        normalized.equals("expired", ignoreCase = true) -> MockActivationResult(
            state = MockActivationState.Expired,
            title = "Subscription expired",
            message = "Mock response shows expiration handling placeholder.",
            expiresAt = "2026-01-01"
        )

        else -> MockActivationResult(
            state = MockActivationState.Error,
            title = "Invalid activation password",
            message = "Mock auth rejected this password."
        )
    }
}

@Composable
private fun StatusCard(result: MockActivationResult) {
    val accent = when (result.state) {
        MockActivationState.Empty -> Color(0xFF7C4DFF)
        MockActivationState.Loading -> Color(0xFF2962FF)
        MockActivationState.Active -> Color(0xFF00BFA5)
        MockActivationState.Inactive -> Color(0xFFFF6D00)
        MockActivationState.Expired -> Color(0xFFFFD600)
        MockActivationState.Error -> Color(0xFFFF6D00)
    }

    Column(
        modifier = Modifier
            .width(620.dp)
            .height(220.dp)
            .background(Color(0xFF111722), RoundedCornerShape(28.dp))
            .border(1.dp, accent, RoundedCornerShape(28.dp))
            .padding(26.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = result.title,
            color = accent,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = result.message,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Device state: ${result.state.name}",
            color = Color.LightGray,
            fontSize = 14.sp
        )
        Text(
            text = "Subscription expires: ${result.expiresAt}",
            color = Color.LightGray,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun InfoPanel(title: String, body: String, accent: Color) {
    Column(
        modifier = Modifier
            .width(430.dp)
            .background(Color(0xFF111722), RoundedCornerShape(22.dp))
            .border(1.dp, accent, RoundedCornerShape(22.dp))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = title,
            color = accent,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = body,
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

@Composable
private fun SmallStateCard(title: String, body: String, accent: Color) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .height(94.dp)
            .background(Color(0xFF111722), RoundedCornerShape(20.dp))
            .border(1.dp, accent, RoundedCornerShape(20.dp))
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                color = accent,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = body,
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}

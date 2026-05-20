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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val PanelDark = Color(0xCC090B12)

@Composable
fun LanguageSelectionScreen(navController: NavController) {
    val context = LocalContext.current

    NexoraCinematicBackdrop {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(44.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .width(760.dp)
                    .background(PanelDark, RoundedCornerShape(36.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(36.dp))
                    .padding(34.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "NEXORA TV",
                    color = Color.White,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.5.sp
                )

                Text(
                    text = "Choose your language",
                    color = NexoraVioletSoft,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = "Select the language for the full app experience. You can change this later from Settings.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(560.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                    LanguageButton(
                        title = "Türkçe",
                        subtitle = "Uygulamayı Türkçe kullan",
                        onClick = {
                            AppLanguageStore.setLanguage(context, AppLanguageStore.Language.TR)
                            navController.navigate(AppDestinations.Activation.route) {
                                popUpTo(AppDestinations.Language.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )

                    LanguageButton(
                        title = "English",
                        subtitle = "Use the app in English",
                        onClick = {
                            AppLanguageStore.setLanguage(context, AppLanguageStore.Language.EN)
                            navController.navigate(AppDestinations.Activation.route) {
                                popUpTo(AppDestinations.Language.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .width(620.dp)
                        .background(Color.White.copy(alpha = 0.055f), RoundedCornerShape(22.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(22.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "This appears once on first launch. Language can be changed later in Settings.",
                        color = Color.White.copy(alpha = 0.62f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun LanguageButton(title: String, subtitle: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(260.dp)
            .height(116.dp)
            .shadow(14.dp, RoundedCornerShape(26.dp), ambientColor = NexoraViolet, spotColor = NexoraViolet),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.Black)
            Text(subtitle, fontSize = 12.sp, color = Color.White.copy(alpha = 0.78f), textAlign = TextAlign.Center)
        }
    }
}

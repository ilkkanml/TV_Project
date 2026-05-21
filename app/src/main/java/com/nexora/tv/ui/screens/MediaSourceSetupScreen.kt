package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop

private val setupViolet = Color(0xFF7C3AED)
private val setupVioletSoft = Color(0xFF9F67FF)
private val setupPanel = Color(0xCC090B12)

internal enum class SourceMode {
    Portal,
    ListUrl,
    LocalFile,
    Single
}

@Composable
fun MediaSourceSetupScreen(navController: NavController) {
    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 36.dp, vertical = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(560.dp)
                    .background(setupPanel, RoundedCornerShape(30.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp))
                    .padding(26.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("NEXORA", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black, letterSpacing = 2.3.sp)
                Text(AppLanguageStore.t("Frontend Test Build", "Frontend Test Sürümü"), color = setupVioletSoft, fontSize = 24.sp, fontWeight = FontWeight.Black)
                Text(
                    text = AppLanguageStore.t(
                        "This setup screen is simplified for the first APK test. Continue to Home and test navigation, cards, detail pages, and player screen.",
                        "İlk APK testi için bu kurulum ekranı sadeleştirildi. Home ekranına geçip navigasyon, kartlar, detay sayfaları ve player ekranını test et."
                    ),
                    color = Color.White.copy(alpha = 0.72f),
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { navController.navigate(AppDestinations.Home.route) { launchSingleTop = true } },
                        modifier = Modifier.width(170.dp).height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = setupViolet, contentColor = Color.White)
                    ) {
                        Text(AppLanguageStore.t("Open Home", "Home Aç"), fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.width(120.dp).height(54.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
                    ) {
                        Text(AppLanguageStore.t("Back", "Geri"), fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .width(480.dp)
                    .background(setupPanel, RoundedCornerShape(30.dp))
                    .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp))
                    .padding(26.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(AppLanguageStore.t("Test Focus", "Test Odağı"), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
                Text(AppLanguageStore.t("Splash, language, activation, profiles, home menu, detail pages, and player navigation.", "Splash, dil, aktivasyon, profiller, home menü, detay sayfaları ve player navigasyonu."), color = Color.White.copy(alpha = 0.70f), fontSize = 13.sp, lineHeight = 20.sp)
            }
        }
    }
}

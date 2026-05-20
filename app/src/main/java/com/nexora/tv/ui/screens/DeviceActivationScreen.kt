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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop
import com.nexora.tv.ui.components.NexoraWordmark
import com.nexora.tv.ui.components.PlayerEcosystemWordmark

private const val MOCK_NEXORA_DEVICE_ID = "NX-TV-8F2K-44M9"
private const val MOCK_ACTIVATION_KEY = "K7Q4-29XA"
private const val ACTIVATION_SCREEN_MARKER = "EA0-ACT-FIX-6"
private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraGreen = Color(0xFF39FF88)
private val PanelDark = Color(0xCC090B12)

@Composable
fun DeviceActivationScreen(navController: NavController) {
    val title = AppLanguageStore.t("Activate this TV", "Bu TV'yi Etkinleştir")
    val nextTitle = AppLanguageStore.t("Next: User Profiles", "Sonraki: Kullanıcı Profilleri")
    val continueText = AppLanguageStore.t("CONTINUE", "DEVAM ET")
    val continueSub = AppLanguageStore.t("Open user profiles", "Kullanıcı profillerini aç")

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
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NexoraWordmark(fontSize = 38.sp, letterSpacing = 2.5.sp)
                PlayerEcosystemWordmark(fontSize = 11.sp, letterSpacing = 1.2.sp)

                Text(title, color = NexoraVioletSoft, fontSize = 22.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(ACTIVATION_SCREEN_MARKER, color = Color.White.copy(alpha = 0.42f), fontSize = 11.sp, fontWeight = FontWeight.Black, maxLines = 1)
                Text(
                    text = AppLanguageStore.t(
                        "Early access device accepted. Next step: choose or create a user profile.",
                        "Erken erişim cihazı kabul edildi. Sonraki adım: kullanıcı profili seç veya oluştur."
                    ),
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
                        navController.navigate(AppDestinations.Profiles.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .width(340.dp)
                        .height(70.dp)
                        .shadow(16.dp, RoundedCornerShape(24.dp), ambientColor = NexoraViolet, spotColor = NexoraViolet),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text(continueText, fontWeight = FontWeight.Black, fontSize = 16.sp, letterSpacing = 1.1.sp, maxLines = 1)
                        Text(continueSub, fontWeight = FontWeight.Medium, fontSize = 11.sp, color = Color.White.copy(alpha = 0.78f), maxLines = 1)
                    }
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
                Text(AppLanguageStore.t("Device Ready", "Cihaz Hazır"), color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black, maxLines = 1)
                PlayerEcosystemWordmark(fontSize = 12.sp, letterSpacing = 1.2.sp)
                EarlyAccessLine()
                Text(nextTitle, color = NexoraVioletSoft, fontSize = 24.sp, fontWeight = FontWeight.Black, maxLines = 1)

                ActivationStep("1", AppLanguageStore.t("Device screen is accepted for early access.", "Cihaz erken erişim için kabul edildi."))
                ActivationStep("2", AppLanguageStore.t("Open user profiles.", "Kullanıcı profillerini aç."))
                ActivationStep("3", AppLanguageStore.t("Create a profile and add your own media access.", "Profil oluştur ve kendi medya erişimini ekle."))
                ActivationStep("4", AppLanguageStore.t("Load library data, then open Home or play a stream.", "Kütüphaneyi yükle, sonra Home'u aç veya oynat."))

                Box(
                    modifier = Modifier
                        .width(550.dp)
                        .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(20.dp))
                        .border(1.dp, NexoraViolet.copy(alpha = 0.36f), RoundedCornerShape(20.dp))
                        .padding(15.dp)
                ) {
                    Text(
                        text = AppLanguageStore.t(
                            "Nexora TV is a media player shell. It does not sell, provide, host, restream, or promote any media service.",
                            "Nexora TV bir medya oynatıcı arayüzüdür. Yayın satmaz, sağlamaz, barındırmaz, yeniden yayınlamaz veya medya servisi sunmaz."
                        ),
                        color = Color.White.copy(alpha = 0.72f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun EarlyAccessLine() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = AppLanguageStore.t("Early Access", "Erken erişim"),
            color = NexoraGreen,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = AppLanguageStore.t(" mode is active", " modu aktif"),
            color = Color.White.copy(alpha = 0.70f),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
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

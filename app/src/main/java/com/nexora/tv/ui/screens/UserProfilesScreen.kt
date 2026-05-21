package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.profile.MediaProfile
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.NexoraCinematicBackdrop
import com.nexora.tv.ui.components.NexoraWordmark
import com.nexora.tv.ui.components.PlayerEcosystemWordmark

private val NexoraViolet = Color(0xFF7C3AED)
private val NexoraVioletSoft = Color(0xFF9F67FF)
private val NexoraGreen = Color(0xFF39FF88)
private val PanelDark = Color(0xCC090B12)

@Composable
fun UserProfilesScreen(navController: NavController) {
    val context = LocalContext.current
    MediaProfileStore.init(context)

    val profiles = MediaProfileStore.profiles
    val selected = MediaProfileStore.selectedProfile
    var previewProfileId by remember(profiles.size, selected?.id) {
        mutableStateOf(selected?.id ?: profiles.firstOrNull()?.id)
    }
    val previewProfile = profiles.firstOrNull { it.id == previewProfileId }

    NexoraCinematicBackdrop {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 34.dp, vertical = 22.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(370.dp).height(635.dp).background(PanelDark, RoundedCornerShape(32.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(32.dp)).padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NexoraWordmark(fontSize = 27.sp, letterSpacing = 2.sp)
                PlayerEcosystemWordmark(fontSize = 9.sp, letterSpacing = 1.sp)
                Text(AppLanguageStore.t("User Profiles", "Kullanıcı Profilleri"), color = NexoraVioletSoft, fontSize = 21.sp, fontWeight = FontWeight.Black)
                Text(AppLanguageStore.t("Focus a user to preview details. Press OK to open Home.", "Detayları görmek için kullanıcıya gel. Home açmak için OK tuşuna bas."), color = Color.White.copy(alpha = 0.62f), fontSize = 12.sp, lineHeight = 17.sp)

                Button(
                    onClick = {
                        MediaProfileStore.startAdd()
                        navController.navigate(AppDestinations.PlaylistProfile.route) { launchSingleTop = true }
                    },
                    modifier = Modifier.width(334.dp).height(58.dp).shadow(12.dp, RoundedCornerShape(22.dp), ambientColor = NexoraViolet, spotColor = NexoraViolet),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
                ) { Text(AppLanguageStore.t("ADD USER", "KULLANICI EKLE"), fontSize = 15.sp, fontWeight = FontWeight.Black) }

                Column(
                    modifier = Modifier.width(334.dp).height(430.dp).verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    if (profiles.isEmpty()) {
                        EmptyUserHint()
                    } else {
                        profiles.forEach { profile ->
                            ProfileListButton(
                                profile = profile,
                                highlighted = previewProfile?.id == profile.id,
                                selected = selected?.id == profile.id,
                                onFocused = { previewProfileId = profile.id },
                                onClick = {
                                    MediaProfileStore.select(profile, context)
                                    navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                                }
                            )
                        }
                    }
                }
            }

            ProfileInfoPanel(
                navController = navController,
                profile = previewProfile,
                onSelect = { profile ->
                    MediaProfileStore.select(profile, context)
                    navController.navigate(AppDestinations.Home.route) { launchSingleTop = true }
                }
            )
        }
    }
}

@Composable
private fun ProfileInfoPanel(navController: NavController, profile: MediaProfile?, onSelect: (MediaProfile) -> Unit) {
    Column(
        modifier = Modifier.width(760.dp).height(635.dp).background(PanelDark, RoundedCornerShape(32.dp)).border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(32.dp)).padding(26.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (profile == null) {
            Text(AppLanguageStore.t("No user selected", "Kullanıcı seçilmedi"), color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Black)
            Text(AppLanguageStore.t("Create a user profile to connect a personal media source and view account/library status here.", "Kişisel medya kaynağını bağlamak ve hesap/kütüphane durumunu görmek için kullanıcı profili oluştur."), color = Color.White.copy(alpha = 0.68f), fontSize = 15.sp, lineHeight = 22.sp)
            Box(modifier = Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(NexoraViolet.copy(alpha = 0.18f), Color.White.copy(alpha = 0.04f))), RoundedCornerShape(26.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(26.dp)).padding(20.dp)) {
                Text(AppLanguageStore.t("Profiles keep each user's media setup separate and make the player easier to manage on a shared TV.", "Profiller, her kullanıcının medya kurulumunu ayrı tutar ve ortak TV kullanımını daha düzenli hale getirir."), color = Color.White.copy(alpha = 0.72f), fontSize = 14.sp, lineHeight = 21.sp)
            }
            return@Column
        }

        Text(profile.profileName, color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(profile.status, color = NexoraGreen, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            InfoCard(AppLanguageStore.t("Source", "Kaynak"), profile.sourceType)
            InfoCard("Live", profile.live.size.toString())
            InfoCard(AppLanguageStore.t("Movies", "Filmler"), profile.movies.size.toString())
            InfoCard(AppLanguageStore.t("Series", "Diziler"), profile.series.size.toString())
        }

        InfoLine(AppLanguageStore.t("Server", "Sunucu"), profile.serverAddress.ifBlank { AppLanguageStore.t("Not saved", "Kaydedilmedi") })
        InfoLine(AppLanguageStore.t("User name", "Kullanıcı adı"), profile.accountName.ifBlank { AppLanguageStore.t("Not saved", "Kaydedilmedi") })
        InfoLine(AppLanguageStore.t("Password", "Şifre"), if (profile.accessKey.isBlank()) AppLanguageStore.t("Not saved", "Kaydedilmedi") else "••••••••")
        InfoLine(AppLanguageStore.t("Preview profile", "Önizlenen profil"), profile.profileName)

        Text(AppLanguageStore.t("Account status", "Hesap durumu"), color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Black)
        InfoLine(AppLanguageStore.t("Media account expiry", "Yayın hesabı son geçerlilik"), profileValue(profile.mediaAccountExpiry))
        InfoLine(AppLanguageStore.t("Media account status", "Yayın hesabı durumu"), profileValue(profile.mediaAccountStatus))
        InfoLine(AppLanguageStore.t("App validity", "Uygulama geçerliliği"), AppLanguageStore.t(profile.appValidity, "Erken erişim aktif"))
        InfoLine(AppLanguageStore.t("Active connections", "Aktif bağlantı"), profileValue(profile.activeConnections))
        InfoLine(AppLanguageStore.t("Max connections", "Maksimum bağlantı"), profileValue(profile.maxConnections))
        InfoLine(AppLanguageStore.t("Trial account", "Deneme hesabı"), profileValue(profile.trialStatus))

        Column(modifier = Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp)).padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(AppLanguageStore.t("Profile policy", "Profil politikası"), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
            Text(AppLanguageStore.t("• This profile connects only to media access entered by the user.", "• Bu profil yalnızca kullanıcının girdiği medya erişimine bağlanır."), color = Color.White.copy(alpha = 0.72f), fontSize = 13.sp)
            Text(AppLanguageStore.t("• We do not use MAC as the device identity. It can expose hardware details and is not reliable across devices.", "• MAC adresini cihaz kimliği olarak kullanmıyoruz. Donanım bilgisi açığa çıkarabilir ve cihazlar arasında güvenilir değildir."), color = Color.White.copy(alpha = 0.72f), fontSize = 13.sp)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = {
                    MediaProfileStore.startEdit(profile)
                    navController.navigate(AppDestinations.PlaylistProfile.route) { launchSingleTop = true }
                },
                modifier = Modifier.width(150.dp).height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NexoraViolet, contentColor = Color.White)
            ) { Text(AppLanguageStore.t("Edit", "Düzenle"), fontWeight = FontWeight.Black) }

            Button(
                onClick = { onSelect(profile) },
                modifier = Modifier.width(170.dp).height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f), contentColor = Color.White)
            ) { Text(AppLanguageStore.t("Open Home", "Ana Sayfayı Aç"), fontWeight = FontWeight.Black) }
        }
    }
}

@Composable
private fun ProfileListButton(profile: MediaProfile, highlighted: Boolean, selected: Boolean, onFocused: () -> Unit, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.width(334.dp).height(72.dp).onFocusChanged { if (it.isFocused) onFocused() },
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                selected -> NexoraViolet.copy(alpha = 0.92f)
                highlighted -> NexoraViolet.copy(alpha = 0.42f)
                else -> Color.White.copy(alpha = 0.07f)
            },
            contentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            Text(profile.profileName, fontSize = 16.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(profile.status, fontSize = 11.sp, color = Color.White.copy(alpha = 0.72f), maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun EmptyUserHint() {
    Column(modifier = Modifier.width(334.dp).height(130.dp).background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(24.dp)).padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text(AppLanguageStore.t("No users yet", "Henüz kullanıcı yok"), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.t("Use Add User to create the first profile.", "İlk profili oluşturmak için Kullanıcı Ekle'yi kullan."), color = Color.White.copy(alpha = 0.62f), fontSize = 12.sp)
    }
}

private fun profileValue(value: String): String {
    return when (value.trim()) {
        "Not available" -> AppLanguageStore.t("Not available", "Mevcut değil")
        "Unknown" -> AppLanguageStore.t("Unknown", "Bilinmiyor")
        "Yes" -> AppLanguageStore.t("Yes", "Evet")
        "No" -> AppLanguageStore.t("No", "Hayır")
        else -> value
    }
}

@Composable
private fun InfoCard(title: String, value: String) {
    Column(modifier = Modifier.width(160.dp).background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp)).border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, color = NexoraVioletSoft, fontSize = 11.sp, fontWeight = FontWeight.Black)
        Text(value, color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun InfoLine(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().height(44.dp).background(Color.White.copy(alpha = 0.04f), RoundedCornerShape(16.dp)).padding(horizontal = 14.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = NexoraVioletSoft, fontSize = 12.sp, fontWeight = FontWeight.Black)
        Text(value, color = Color.White.copy(alpha = 0.84f), fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

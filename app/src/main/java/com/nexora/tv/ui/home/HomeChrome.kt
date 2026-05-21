package com.nexora.tv.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexora.tv.data.app.AppLanguageStore

private val Purple = Color(0xFF7C3AED)
private val PurpleSoft = Color(0xFF9F67FF)
private val Blue = Color(0xFF4CC9FF)

@Composable
internal fun HomeSidebar(selectedMenu: HomeMenu, onMenuSelected: (HomeMenu) -> Unit) {
    val requesters = remember { HomeMenu.values().associateWith { FocusRequester() } }
    LaunchedEffect(selectedMenu) { requesters[selectedMenu]?.requestFocus() }

    Column(
        modifier = Modifier
            .width(188.dp)
            .fillMaxHeight()
            .background(Color(0xAA060810), RoundedCornerShape(26.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(26.dp))
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Text("NEXORA TV", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black, letterSpacing = 1.1.sp)
        Text("PLAYER ECOSYSTEM", color = PurpleSoft, fontSize = 8.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(Modifier.height(4.dp))
        HomeMenu.values().forEach { menu ->
            HomeMenuButton(menu.icon, AppLanguageStore.ui(menu.key), selectedMenu == menu, requesters.getValue(menu)) {
                onMenuSelected(menu)
            }
        }
        Spacer(Modifier.weight(1f))
        Text(
            AppLanguageStore.t("Focus changes section • OK opens cards", "Focus bölümü değiştirir • OK kartı açar"),
            color = Color.White.copy(alpha = 0.48f),
            fontSize = 9.sp,
            lineHeight = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.04f), RoundedCornerShape(16.dp))
                .padding(10.dp)
        )
    }
}

@Composable
private fun HomeMenuButton(icon: String, title: String, selected: Boolean, requester: FocusRequester, onSelected: () -> Unit) {
    Button(
        onClick = onSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .focusRequester(requester)
            .onFocusChanged { if (it.isFocused) onSelected() }
            .then(if (selected) Modifier.shadow(9.dp, RoundedCornerShape(15.dp), ambientColor = Purple, spotColor = Purple) else Modifier),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xCC171C30) else Color.White.copy(alpha = 0.045f),
            contentColor = Color.White
        )
    ) {
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.Start), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .background(if (selected) Purple.copy(alpha = 0.20f) else Color.White.copy(alpha = 0.06f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, color = if (selected) Blue else Color.White.copy(alpha = 0.84f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text(title, fontSize = 13.sp, fontWeight = if (selected) FontWeight.Black else FontWeight.Medium)
        }
    }
}

@Composable
internal fun HomeTopBar(selectedMenu: HomeMenu) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color(0x66070A12), RoundedCornerShape(20.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(AppLanguageStore.ui(selectedMenu.key), color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                AppLanguageStore.t("Licensed Android TV shell • remote-first navigation", "Lisanslı Android TV shell • kumanda öncelikli navigasyon"),
                color = Color.White.copy(alpha = 0.60f),
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        UtilityPill("4K")
        UtilityPill("Dolby")
        UtilityPill("10:45")
    }
}

@Composable
internal fun HomeBackHintPopup() {
    Box(Modifier.fillMaxSize().padding(top = 24.dp, end = 24.dp), contentAlignment = Alignment.TopEnd) {
        Column(
            Modifier
                .width(330.dp)
                .background(Color(0xEE0A0D16), RoundedCornerShape(24.dp))
                .border(1.dp, Purple.copy(alpha = 0.45f), RoundedCornerShape(24.dp))
                .shadow(18.dp, RoundedCornerShape(24.dp), ambientColor = Purple, spotColor = Purple)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(AppLanguageStore.ui("Go back?"), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
            Text(AppLanguageStore.ui("Press Back again to return to user profiles."), color = Color.White.copy(alpha = 0.70f), fontSize = 12.sp, lineHeight = 17.sp)
        }
    }
}

@Composable
private fun UtilityPill(text: String) {
    Box(
        Modifier
            .background(Color.White.copy(alpha = 0.055f), RoundedCornerShape(13.dp))
            .border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(13.dp))
            .padding(horizontal = 10.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White.copy(alpha = 0.82f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

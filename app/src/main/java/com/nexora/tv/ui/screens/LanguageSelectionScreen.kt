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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
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
private val FocusBlue = Color(0xFF4CC9FF)
private val PanelDark = Color(0xCC090B12)
private val ButtonIdle = Color(0xAA121624)

@Composable
fun LanguageSelectionScreen(navController: NavController) {
    val context = LocalContext.current
    val turkishFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        turkishFocusRequester.requestFocus()
    }

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
                    text = "Dil seç / Choose language",
                    color = NexoraVioletSoft,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Uygulama dilini seç. Daha sonra Settings / Ayarlar ekranından değiştirebilirsin.",
                    color = Color.White.copy(alpha = 0.68f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(560.dp)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    LanguageButton(
                        title = "Türkçe",
                        requester = turkishFocusRequester,
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
                        requester = null,
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
                        .width(560.dp)
                        .background(Color.White.copy(alpha = 0.055f), RoundedCornerShape(18.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(18.dp))
                        .padding(13.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Kumandadan OK tuşu ile devam et.",
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
private fun LanguageButton(title: String, requester: FocusRequester?, onClick: () -> Unit) {
    var focused by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)

    Button(
        onClick = onClick,
        modifier = Modifier
            .width(180.dp)
            .height(54.dp)
            .then(if (requester != null) Modifier.focusRequester(requester) else Modifier)
            .onFocusChanged { focused = it.isFocused }
            .shadow(
                elevation = if (focused) 10.dp else 0.dp,
                shape = shape,
                ambientColor = FocusBlue,
                spotColor = FocusBlue
            )
            .border(
                width = if (focused) 2.dp else 1.dp,
                color = if (focused) FocusBlue else Color.White.copy(alpha = 0.12f),
                shape = shape
            ),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (focused) NexoraViolet else ButtonIdle,
            contentColor = Color.White
        )
    ) {
        Text(title, fontSize = 17.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center)
    }
}

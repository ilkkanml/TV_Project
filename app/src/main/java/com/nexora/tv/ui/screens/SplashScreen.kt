package com.nexora.tv.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.components.PremiumBackground
import com.nexora.tv.ui.theme.NexoraColors
import kotlinx.coroutines.delay

private const val SPLASH_ENTRY_DELAY_MS = 1800L

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(SPLASH_ENTRY_DELAY_MS)
        navController.navigate(AppDestinations.Activation.route) {
            popUpTo(AppDestinations.Splash.route) {
                inclusive = true
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }
    }

    PremiumBackground(
        accent = NexoraColors.Cyan,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "NEXORA",
                color = NexoraColors.TextPrimary,
                fontSize = 58.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 5.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Cinematic TV starts here",
                color = NexoraColors.Cyan,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Preparing secure device activation",
                color = NexoraColors.TextSecondary,
                fontSize = 13.sp
            )
        }
    }
}

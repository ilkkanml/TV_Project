package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.theme.NexoraColors
import kotlinx.coroutines.delay

private const val SPLASH_ENTRY_DELAY_MS = 1800L

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(SPLASH_ENTRY_DELAY_MS)
        navController.navigate(AppDestinations.Login.route) {
            popUpTo(AppDestinations.Splash.route) {
                inclusive = true
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NexoraColors.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "NEXORA",
            color = NexoraColors.Cyan,
            fontSize = 44.sp
        )
    }
}

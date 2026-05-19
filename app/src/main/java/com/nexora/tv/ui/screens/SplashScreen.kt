package com.nexora.tv.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.intro.NexoraBrandIntroSplash

@Composable
fun SplashScreen(navController: NavController) {
    NexoraBrandIntroSplash(
        onFinished = {
            navController.navigate(AppDestinations.Activation.route) {
                popUpTo(AppDestinations.Splash.route) {
                    inclusive = true
                    saveState = false
                }
                launchSingleTop = true
                restoreState = false
            }
        }
    )
}

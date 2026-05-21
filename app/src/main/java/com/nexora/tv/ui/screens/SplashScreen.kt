package com.nexora.tv.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.nexora.tv.data.app.AppLanguageStore
import com.nexora.tv.data.profile.MediaProfileStore
import com.nexora.tv.navigation.AppDestinations
import com.nexora.tv.ui.intro.NexoraBrandIntroSplash

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    AppLanguageStore.init(context)
    MediaProfileStore.init(context)

    NexoraBrandIntroSplash(
        onFinished = {
            val target = when {
                !AppLanguageStore.hasChosenLanguage -> AppDestinations.Language.route
                MediaProfileStore.profiles.isEmpty() -> AppDestinations.Activation.route
                MediaProfileStore.hasSavedSelection -> AppDestinations.Home.route
                else -> AppDestinations.Profiles.route
            }

            navController.navigate(target) {
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

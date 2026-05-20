package com.nexora.tv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexora.tv.ui.screens.ContentDetailScreen
import com.nexora.tv.ui.screens.DeviceActivationScreen
import com.nexora.tv.ui.screens.HomeScreen
import com.nexora.tv.ui.screens.LanguageSelectionScreen
import com.nexora.tv.ui.screens.LoginScreen
import com.nexora.tv.ui.screens.MediaSourceSetupScreen
import com.nexora.tv.ui.screens.PlayerScreen
import com.nexora.tv.ui.screens.SplashScreen
import com.nexora.tv.ui.screens.UserProfilesScreen

@Composable
fun NexoraNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppDestinations.Splash.route) {
        composable(AppDestinations.Splash.route) { SplashScreen(navController) }
        composable(AppDestinations.Language.route) { LanguageSelectionScreen(navController) }
        composable(AppDestinations.Login.route) { LoginScreen(navController) }
        composable(AppDestinations.Activation.route) { DeviceActivationScreen(navController) }
        composable(AppDestinations.Profiles.route) { UserProfilesScreen(navController) }
        composable(AppDestinations.Home.route) { HomeScreen(navController) }
        composable(AppDestinations.Detail.route) { backStackEntry ->
            ContentDetailScreen(navController = navController, contentId = backStackEntry.arguments?.getString("contentId"))
        }
        composable(AppDestinations.Player.route) { PlayerScreen(navController) }
        composable(AppDestinations.PlaylistProfile.route) { MediaSourceSetupScreen(navController) }
    }
}

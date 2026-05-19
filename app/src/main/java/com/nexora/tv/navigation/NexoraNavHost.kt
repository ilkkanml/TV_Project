package com.nexora.tv.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nexora.tv.ui.screens.ContentDetailScreen
import com.nexora.tv.ui.screens.DeviceActivationScreen
import com.nexora.tv.ui.screens.HomeScreen
import com.nexora.tv.ui.screens.LoginScreen
import com.nexora.tv.ui.screens.PlayerScreen
import com.nexora.tv.ui.screens.PlaylistProfileScreen
import com.nexora.tv.ui.screens.SplashScreen

@Composable
fun NexoraNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.Splash.route
    ) {

        composable(AppDestinations.Splash.route) {
            SplashScreen(navController)
        }

        composable(AppDestinations.Login.route) {
            LoginScreen(navController)
        }

        composable(AppDestinations.Activation.route) {
            DeviceActivationScreen(navController)
        }

        composable(AppDestinations.Home.route) {
            HomeScreen(navController)
        }

        composable(AppDestinations.Detail.route) { backStackEntry ->
            ContentDetailScreen(
                navController = navController,
                contentId = backStackEntry.arguments?.getString("contentId")
            )
        }

        composable(AppDestinations.Player.route) {
            PlayerScreen()
        }

        composable(AppDestinations.PlaylistProfile.route) {
            PlaylistProfileScreen(navController)
        }
    }
}

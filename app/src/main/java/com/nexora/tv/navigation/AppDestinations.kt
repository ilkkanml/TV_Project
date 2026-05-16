package com.nexora.tv.navigation

sealed class AppDestinations(val route: String) {
    data object Splash : AppDestinations("splash")
    data object Login : AppDestinations("login")
    data object Activation : AppDestinations("activation")
    data object Home : AppDestinations("home")
    data object Player : AppDestinations("player")
}

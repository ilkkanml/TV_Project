package com.nexora.tv.navigation

sealed class AppDestinations(val route: String) {
    data object Splash : AppDestinations("splash")
    data object Language : AppDestinations("language")
    data object Activation : AppDestinations("activation")
    data object Profiles : AppDestinations("profiles")
    data object Home : AppDestinations("home")
    data object Detail : AppDestinations("detail/{contentId}") {
        fun createRoute(contentId: String): String = "detail/$contentId"
    }
    data object Player : AppDestinations("player")
    data object PlaylistProfile : AppDestinations("playlist-profile")
}

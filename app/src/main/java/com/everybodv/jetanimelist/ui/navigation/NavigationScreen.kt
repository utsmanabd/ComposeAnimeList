package com.everybodv.jetanimelist.ui.navigation

sealed class NavigationScreen(val route: String) {
    object Home : NavigationScreen("home")
    object WatchList : NavigationScreen("watchlist")
    object Profile : NavigationScreen("profile")
    object Detail : NavigationScreen("home/{animeId}") {
        fun createRoute(animeId: Long) = "home/$animeId"
    }
}
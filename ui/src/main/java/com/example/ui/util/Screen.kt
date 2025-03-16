package com.example.ui.util

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Login : Screen("login_screen")
    data object Home : Screen("home_screen")
    data object Search : Screen("search_screen")
    data object Notifications : Screen("notifications_screen")
    data object Profile : Screen("profile_screen")
    data object ResetPassword : Screen("reset_password")

}
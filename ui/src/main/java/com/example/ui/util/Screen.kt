package com.example.ui.util

sealed class Screen(val route: String) {
    data object Login : Screen("login_screen")
    data object Splash : Screen("login_screen")
    data object Profile : Screen("profile_screen")
    data object ResetPassword : Screen("reset_password")

}
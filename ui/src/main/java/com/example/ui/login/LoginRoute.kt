package com.example.ui.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.ui.util.Screen

fun NavController.navigateToLogin(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(Screen.Login.route, builder)
}

fun NavGraphBuilder.loginRoute(navController: NavHostController){
    composable(Screen.Login.route) { LoginScreen(navController) }
}
package com.example.ui.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ui.util.Screen

fun NavGraphBuilder.splashRoute(navController: NavHostController) {
    composable(Screen.Splash.route) { SplashScreen(navController) }
}
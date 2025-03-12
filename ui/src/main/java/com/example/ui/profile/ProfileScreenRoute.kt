package com.example.ui.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.ui.util.Screen

fun NavController.navigateToProfile(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(Screen.Profile.route, builder)
}

fun NavGraphBuilder.profileRoute(navController: NavHostController){
    composable(Screen.Profile.route) { ProfileScreen(navController) }
}
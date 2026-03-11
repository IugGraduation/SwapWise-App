package com.sam.ui.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sam.ui.util.Screen

fun NavController.navigateToProfile() {
    navigate(Screen.Profile.route) {
        popUpTo(graph.id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.profileRoute(navController: NavHostController){
    composable(Screen.Profile.route) { ProfileScreen(navController) }
}
package com.example.ui.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ui.util.Screen


fun NavController.navigateToNotifications() {
    navigate(Screen.Notifications.route) {
        popUpTo(graph.id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.notificationsRoute(navController: NavHostController){
    composable(Screen.Notifications.route) { NotificationsScreen(navController) }
}
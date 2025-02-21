package com.example.ui.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable

private const val ROUTE = "notifications"

fun NavController.navigateToNotifications(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(ROUTE, builder)
}

fun NavGraphBuilder.notificationsRoute(navController: NavHostController){
    composable(ROUTE) { NotificationsScreen(navController) }
}
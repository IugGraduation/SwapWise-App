package com.example.ui.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val ROUTE = "notifications"

fun NavController.navigateToNotifications(){
    navigate(ROUTE)
}

fun NavGraphBuilder.notificationsRoute(navController: NavHostController){
    composable(ROUTE) { NotificationsScreen(navController) }
}
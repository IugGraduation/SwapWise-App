package com.example.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ui.util.Screen

fun NavController.navigateToHome() {
    navigate(Screen.Home.route) {
        popUpTo(graph.id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.homeRoute(navController: NavHostController){
    composable(Screen.Home.route) { HomeScreen(navController) }
}
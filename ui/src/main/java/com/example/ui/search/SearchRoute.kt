package com.example.ui.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.ui.util.Screen


fun NavController.navigateToSearch() {
    navigate(Screen.Search.route) {
        popUpTo(graph.id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.searchRoute(navController: NavHostController){
    composable(route = Screen.Search.route) { SearchScreen(navController) }
}


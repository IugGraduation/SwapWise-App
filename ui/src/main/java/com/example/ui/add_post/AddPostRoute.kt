package com.example.ui.add_post


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val ROUTE = "add_post"

fun NavController.navigateToAddPost() {
    navigate(ROUTE)
}

fun NavGraphBuilder.addPostRoute(navController: NavHostController) {
    composable(
        route = ROUTE,
    ) { AddPostScreen(navController) }
}


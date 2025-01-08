package com.example.ui.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val ROUTE = "profile"

fun NavController.navigateToProfile(){
    navigate(ROUTE)
}

fun NavGraphBuilder.profileRoute(navController: NavHostController){
    composable(ROUTE) { ProfileScreen(navController) }
}
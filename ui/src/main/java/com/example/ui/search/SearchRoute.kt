package com.example.ui.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val ROUTE = "search"

fun NavController.navigateToSearch(){
    navigate(ROUTE)
}

fun NavGraphBuilder.searchRoute(navController: NavHostController){
    composable(ROUTE) { SearchScreen(navController) }
}
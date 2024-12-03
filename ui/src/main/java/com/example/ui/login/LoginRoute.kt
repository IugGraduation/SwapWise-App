package com.example.ui.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val ROUTE = "login"

fun NavController.navigateToLogin(){
    navigate(ROUTE)
}

fun NavGraphBuilder.loginRoute(navController: NavHostController){
    composable(ROUTE) { LoginScreen(navController) }
}
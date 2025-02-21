package com.example.ui.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable


private const val ROUTE = "signup"

fun NavController.navigateToSignup(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(ROUTE, builder)
}

fun NavGraphBuilder.signupRoute(navController: NavHostController){
    composable(ROUTE) { SignupScreen(navController) }
}
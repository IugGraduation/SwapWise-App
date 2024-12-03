package com.example.ui.otp

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

private const val ROUTE = "otp"

fun NavController.navigateToOtp(){
    navigate(ROUTE)
}

fun NavGraphBuilder.otpRoute(navController: NavHostController){
    composable(ROUTE) { OtpScreen(navController) }
}
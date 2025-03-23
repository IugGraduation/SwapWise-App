package com.example.ui.reset_password

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.ui.util.Screen

fun NavController.navigateToResetPassword(builder: NavOptionsBuilder.() -> Unit = {}){
    navigate(Screen.ResetPassword.route)
}

fun NavGraphBuilder.resetPasswordRout(navController: NavHostController){
    composable(Screen.ResetPassword.route) { ResetPasswordScreen(navController) }
}
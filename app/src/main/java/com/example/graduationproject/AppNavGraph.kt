package com.example.graduationproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.ui.login.loginRoute
import com.example.ui.otp.otpRoute
import com.example.ui.signup.signupRoute


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "signup") {
        signupRoute(navController)
        loginRoute(navController)
        otpRoute(navController)
    }
}

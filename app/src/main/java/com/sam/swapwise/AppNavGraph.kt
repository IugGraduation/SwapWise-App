package com.sam.swapwise

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sam.ui.add_post.addPostRoute
import com.sam.ui.edit_post.editPostRoute
import com.sam.ui.home.homeRoute
import com.sam.ui.login.loginRoute
import com.sam.ui.notifications.notificationsRoute
import com.sam.ui.otp.otpRoute
import com.sam.ui.post_details.postDetailsRoute
import com.sam.ui.profile.profileRoute
import com.sam.ui.reset_password.resetPasswordRout
import com.sam.ui.search.searchRoute
import com.sam.ui.see_all_topics.seeAllTopicsRoute
import com.sam.ui.signup.signupRoute
import com.sam.ui.splash.splashRoute
import com.sam.ui.util.Screen


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        splashRoute(navController)
        signupRoute(navController)
        loginRoute(navController)
        otpRoute(navController)
        homeRoute(navController)
        seeAllTopicsRoute(navController)
        postDetailsRoute(navController)
        addPostRoute(navController)
        searchRoute(navController)
        notificationsRoute(navController)
        editPostRoute(navController)
        profileRoute(navController)
        resetPasswordRout(navController)

    }
}

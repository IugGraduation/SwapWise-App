package com.example.graduationproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.ui.add_offer.addOfferRoute
import com.example.ui.add_post.addPostRoute
import com.example.ui.login.loginRoute
import com.example.ui.confirm_number.otpRoute
import com.example.ui.edit_offer.editOfferRoute
import com.example.ui.edit_post.editPostRoute
import com.example.ui.home.homeRoute
import com.example.ui.notifications.notificationsRoute
import com.example.ui.offer_details.offerDetailsRoute
import com.example.ui.post_details.postDetailsRoute
import com.example.ui.profile.profileRoute
import com.example.ui.search.searchRoute
import com.example.ui.signup.signupRoute
import com.example.ui.see_all_topics.seeAllTopicsRoute


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "signup") {
        signupRoute(navController)
        loginRoute(navController)
        otpRoute(navController)
        homeRoute(navController)
        seeAllTopicsRoute(navController)
        postDetailsRoute(navController)
        addOfferRoute(navController)
        offerDetailsRoute(navController)
        editOfferRoute(navController)
        addPostRoute(navController)
        searchRoute(navController)
        notificationsRoute(navController)
        editPostRoute(navController)
        profileRoute(navController)
    }
}

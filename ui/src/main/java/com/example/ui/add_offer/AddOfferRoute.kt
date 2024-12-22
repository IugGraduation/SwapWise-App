package com.example.ui.add_offer


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "add_offer"

fun NavController.navigateToAddOffer(postId: String) {
    navigate("$ROUTE/$postId")
}

fun NavGraphBuilder.addOfferRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${AddOfferArgs.POST_ID_ARG}}",
        arguments = listOf(
            navArgument(AddOfferArgs.POST_ID_ARG) { NavType.StringType },
        )
    ) { AddOfferScreen(navController) }
}


class AddOfferArgs(savedStateHandle: SavedStateHandle) {
    val postId: String = checkNotNull(savedStateHandle[POST_ID_ARG])

    companion object {
        const val POST_ID_ARG = "post_id"
    }
}
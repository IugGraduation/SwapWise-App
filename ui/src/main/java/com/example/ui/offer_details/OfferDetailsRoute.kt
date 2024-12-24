package com.example.ui.offer_details


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "offer_details"

fun NavController.navigateToOfferDetails(uuid: String) {
    navigate("$ROUTE/$uuid")
}

fun NavGraphBuilder.offerDetailsRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${OfferDetailsArgs.UUID_ARG}}",
        arguments = listOf(
            navArgument(OfferDetailsArgs.UUID_ARG) { NavType.StringType },
        )
    ) { OfferDetailsScreen(navController) }
}


class OfferDetailsArgs(savedStateHandle: SavedStateHandle) {
    val uuid: String = checkNotNull(savedStateHandle[UUID_ARG])

    companion object {
        const val UUID_ARG = "uuid"
    }
}
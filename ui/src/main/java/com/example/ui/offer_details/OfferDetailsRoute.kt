package com.example.ui.offer_details


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "offer_details"

fun NavController.navigateToOfferDetails(
    offerId: String,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$ROUTE/$offerId", builder)
}

fun NavGraphBuilder.offerDetailsRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${OfferDetailsArgs.OFFER_ID_ARG}}",
        arguments = listOf(
            navArgument(OfferDetailsArgs.OFFER_ID_ARG) { NavType.StringType },
        )
    ) { OfferDetailsScreen(navController) }
}


class OfferDetailsArgs(savedStateHandle: SavedStateHandle) {
    val offerId: String = checkNotNull(savedStateHandle[OFFER_ID_ARG])

    companion object {
        const val OFFER_ID_ARG = "offer_id"
    }
}
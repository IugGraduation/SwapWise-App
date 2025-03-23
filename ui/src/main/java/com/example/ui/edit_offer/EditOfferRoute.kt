package com.example.ui.edit_offer


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "edit_offer"

fun NavController.navigateToEditOffer(offerId: String, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate("$ROUTE/$offerId", builder)
}

fun NavGraphBuilder.editOfferRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${EditOfferArgs.Offer_ID_ARG}}",
        arguments = listOf(
            navArgument(EditOfferArgs.Offer_ID_ARG) { NavType.StringType },
        )
    ) { EditOfferScreen(navController) }
}


class EditOfferArgs(savedStateHandle: SavedStateHandle) {
    val offerId: String = checkNotNull(savedStateHandle[Offer_ID_ARG])

    companion object {
        const val Offer_ID_ARG = "offer_id"
    }
}
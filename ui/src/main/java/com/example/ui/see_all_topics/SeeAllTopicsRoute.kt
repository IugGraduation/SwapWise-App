package com.example.ui.see_all_topics


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "topic_see_all"

fun NavController.navigateToTopicSeeAll(url: String) {
    navigate("$ROUTE/$url")
}

fun NavGraphBuilder.seeAllTopicsRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${SeeAllTopicsArgs.URL}}",
        arguments = listOf(
            navArgument(SeeAllTopicsArgs.URL) { NavType.StringType },
        )
    ) { SeeAllTopicsScreen(navController) }
}


class SeeAllTopicsArgs(savedStateHandle: SavedStateHandle) {
    val url: String = checkNotNull(savedStateHandle[URL])

    companion object {
        const val URL = "url"
    }
}
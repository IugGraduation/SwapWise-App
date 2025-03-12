package com.example.ui.see_all_topics


import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

private const val ROUTE = "topic_see_all"

fun NavController.navigateToSeeAllTopics(
    url: String,
    title: String,
    categoryId: String = "",
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate("$ROUTE/$url/$title/$categoryId", builder)
}

fun NavGraphBuilder.seeAllTopicsRoute(navController: NavHostController) {
    composable(
        route = "$ROUTE/{${SeeAllTopicsArgs.URL}}/{${SeeAllTopicsArgs.TITLE}}/{${SeeAllTopicsArgs.CATEGORY_ID}}",
        arguments = listOf(
            navArgument(SeeAllTopicsArgs.URL) { NavType.StringType },
            navArgument(SeeAllTopicsArgs.TITLE) { NavType.StringType },
            navArgument(SeeAllTopicsArgs.CATEGORY_ID) { NavType.StringType },
        )
    ) { SeeAllTopicsScreen(navController) }
}


class SeeAllTopicsArgs(savedStateHandle: SavedStateHandle) {
    val url: String = checkNotNull(savedStateHandle[URL])
    val title: String = checkNotNull(savedStateHandle[TITLE])
    val categoryId: String = checkNotNull(savedStateHandle[CATEGORY_ID])

    companion object {
        const val URL = "url"
        const val TITLE = "title"
        const val CATEGORY_ID = "categoryId"
    }
}